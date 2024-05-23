import { PoolConnection, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import ITransaction from "../interfaces/ITransaction.dto";
import database from "../database";

export default class TransactionRepository {
  database = database;

  async selectTransaction(dao: ITransaction) {
    const pool = this.database.pool;
    const query = `
      SELECT
        *
      FROM
        transactions
      WHERE
        product_id = ?
        AND buyer_id = ?;
    `;

    const values = [dao.productID, dao.userID];
    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async insertTransaction(dao: ITransaction) {
    const pool = this.database.pool;
    const query = `
      INSERT INTO
        transactions
        (
          product_id,
          buyer_id,
          status
        )
      VALUES
        (?, ?, ?);
    `;

    const values = [dao.productID, dao.userID, dao.status];
    await pool.query<ResultSetHeader>(query, values);
  }

  async updateTransaction(
    conn: PoolConnection,
    dao: ITransaction & { buyerID: number }
  ) {
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

  async selectProduct(conn: PoolConnection, dao: ITransaction) {
    const query = `
      SELECT
        status AS productStatus
      FROM
        products
      WHERE
        id = ?
        AND seller_id = ?;
    `;

    const values = [dao.productID, dao.userID];
    const [result] = await conn.query<RowDataPacket[]>(query, values);
    return result;
  }

  async updateProudct(conn: PoolConnection, dao: ITransaction) {
    const query = `
      UPDATE
        products
      SET
        status = "완료"
      WHERE
        id = ?
        AND seller_id = ?;
    `;

    const values = [dao.productID, dao.userID];
    const [result] = await conn.query<ResultSetHeader>(query, values);
    return result;
  }
}
