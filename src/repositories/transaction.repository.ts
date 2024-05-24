import { PoolConnection, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import { ProductStatus } from "../interfaces/IProduct.dto";
import ITransaction from "../interfaces/ITransaction.dto";
import database from "../database";

export default class TransactionRepository {
  database = database;

  async selectTransactionCount(dao: ITransaction) {
    const pool = this.database.pool;
    const query = `
      SELECT
        COUNT(*) AS count
      FROM
        transactions
      WHERE
        product_id = ?
        AND status = ?;
    `;

    const values = [dao.productID, dao.status];
    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async selectTransactionStatus(dao: ITransaction) {
    const pool = this.database.pool;
    let query = `
      SELECT
        t.status
      FROM
        transactions AS t
    `;

    let values = [dao.productID];

    if (dao.sellerID) {
      query += `
        INNER JOIN
          products AS p
          ON t.product_id = p.id
        WHERE
          t.product_id = ?
          AND seller_id = ?;
      `;
      values.push(dao.sellerID);
    } else {
      query += `
        WHERE
          t.product_id = ?
          AND t.buyer_id = ?;
      `;
      values.push(dao.buyerID);
    }

    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async insertTransaction(
    conn: PoolConnection,
    dao: ITransaction & { price: number }
  ) {
    const query = `
      INSERT INTO
        transactions
        (
          product_id,
          buyer_id,
          price,
          status
        )
      VALUES
        (?, ?, ?, ?);
    `;

    const values = [dao.productID, dao.buyerID, dao.price, dao.status];
    await conn.query<ResultSetHeader>(query, values);
  }

  async updateTransactionStatus(conn: PoolConnection, dao: ITransaction) {
    const query = `
      UPDATE
        transactions
      SET
        status = ?
      WHERE
        product_id = ?
        AND buyer_id = ?;
    `;

    const values = [dao.status, dao.productID, dao.buyerID];
    const [result] = await conn.query<ResultSetHeader>(query, values);
    return result;
  }

  async selectProduct(dao: ITransaction) {
    const pool = this.database.pool;
    const query = `
      SELECT        
        price,
        amount,
        status
      FROM
        products
      WHERE
        id = ?;
    `;

    const values = [dao.productID];
    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async updateProductStatus(
    conn: PoolConnection,
    dao: ITransaction & { productStatus: ProductStatus }
  ) {
    const query = `
      UPDATE
        products
      SET
        status = ?
      WHERE
        id = ?;
    `;

    const values = [dao.productStatus, dao.productID];
    const [result] = await conn.query<ResultSetHeader>(query, values);
    return result;
  }
}
