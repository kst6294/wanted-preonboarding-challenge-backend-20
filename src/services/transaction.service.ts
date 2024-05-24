import HttpError from "../errors/HttpError";
import { ProductStatus } from "../interfaces/IProduct.dto";
import ITransaction, {
  TransactionStatus,
} from "../interfaces/ITransaction.dto";
import TransactionRepository from "../repositories/transaction.repository";
import database from "../database";
import { PoolConnection } from "mysql2/promise";

export default class TransactionService {
  repository = new TransactionRepository();

  private async updateProductStatus(conn: PoolConnection, dto: ITransaction) {
    const [[transaction], [product]] = await Promise.all([
      this.repository.selectTransactionCount(dto),
      this.repository.selectProduct(dto),
    ]);

    if (
      dto.status === TransactionStatus.구매요청 &&
      transaction.count > 0 &&
      transaction.count < product.amount
    ) {
      var status = ProductStatus.판매중;
    } else if (transaction.count >= product.amount) {
      var status = ProductStatus.예약중;
    } else if (
      dto.status === TransactionStatus.구매확정 &&
      transaction.count === product.amount
    ) {
      var status = ProductStatus.완료;
    } else {
      const message = "상품 상태를 변경할 수 없습니다.";
      throw new HttpError(500, message);
    }

    const dao = { ...dto, productStatus: status };
    await this.repository.updateProductStatus(conn, dao);
  }

  async requestTransaction(dto: ITransaction) {
    const pool = database.pool;

    const [[transaction], conn] = await Promise.all([
      this.repository.selectTransaction(dto),
      pool.getConnection(),
    ]);

    if (transaction) {
      const message = `이미 ${TransactionStatus.구매요청} 처리되었습니다.`;
      throw new HttpError(409, message);
    }

    try {
      await conn.beginTransaction();

      await this.repository.insertTransaction(conn, dto);
      await this.updateProductStatus(conn, dto);

      await conn.commit();
    } catch (error) {
      await conn.rollback();

      throw error;
    } finally {
      await conn.release();
    }
  }

  async approveTransaction(dto: ITransaction & { buyerID: number }) {
    const pool = database.pool;

    const [[product], conn] = await Promise.all([
      this.repository.selectProductBySellerID(dto),
      pool.getConnection(),
    ]);

    if (!product) {
      const message = `요청하신 상품 ID (${dto.productID}) 의 판매자가 아닙니다.`;
      throw new HttpError(403, message);
    }

    if (product.status === dto.status) {
      const message = `이미 ${TransactionStatus.판매승인} 처리되었습니다.`;
      throw new HttpError(409, message);
    }

    try {
      await conn.beginTransaction();

      await this.repository.updateTransactionStatus(conn, dto);
      await this.updateProductStatus(conn, dto);

      await conn.commit();
    } catch (error) {
      await conn.rollback();

      throw error;
    } finally {
      await conn.release();
    }
  }

  async confirmTransaction(dto: ITransaction) {
    const pool = database.pool;

    const [[product], conn] = await Promise.all([
      this.repository.selectProductBySellerID(dto),
      pool.getConnection(),
    ]);

    if (product.status === dto.status) {
      const message = `이미 ${TransactionStatus.구매확정} 처리되었습니다.`;
      throw new HttpError(409, message);
    }

    try {
      await conn.beginTransaction();

      await this.repository.updateTransactionStatus(conn, dto);
      await this.updateProductStatus(conn, dto);

      await conn.commit();
    } catch (error) {
      await conn.rollback();

      throw error;
    } finally {
      await conn.release();
    }
  }
}
