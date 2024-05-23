import HttpError from "../errors/HttpError";
import ITransaction from "../interfaces/ITransaction.dto";
import TransactionRepository from "../repositories/transaction.repository";
import database from "../database";

export default class TransactionService {
  repository = new TransactionRepository();

  async postTransaction(dto: ITransaction) {
    const [row] = await this.repository.selectTransaction(dto);

    if (row) {
      const message = "이미 거래 처리되었습니다.";
      throw new HttpError(409, message);
    }

    await this.repository.insertTransaction(dto);
  }

  async putTransaction(dto: ITransaction & { buyerID: number }) {
    const pool = database.pool;
    const conn = await pool.getConnection();

    try {
      await conn.beginTransaction();

      const [row] = await this.repository.selectProduct(conn, dto);

      if (!row) {
        const message = `요청하신 상품 ID (${dto.productID}) 의 판매자가 아닙니다.`;
        throw new HttpError(403, message);
      }

      if (row.productStatus === dto.status) {
        const message = "이미 거래 완료 처리되었습니다.";
        throw new HttpError(409, message);
      }

      const results = await Promise.allSettled([
        this.repository.updateProudct(conn, dto),
        this.repository.updateTransaction(conn, dto),
      ]);

      results.forEach((result) => {
        if (result.status === "rejected") {
          throw new Error(result.reason);
        }
      });

      await conn.commit();
    } catch (error) {
      await conn.rollback();

      throw error;
    } finally {
      await conn.release();
    }
  }
}
