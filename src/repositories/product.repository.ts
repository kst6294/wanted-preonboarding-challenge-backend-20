import { RowDataPacket } from "mysql2";
import { IProductItem, IProductList } from "../interfaces/IProduct.dto";
import database from "../database";

export default class ProductRepository {
  database = database;

  async selectProducts(dao: IProductList) {
    const pool = this.database.pool;
    let query = `
      SELECT
        p.id AS productID,
        p.seller_id AS sellerID,
        p.name,
        p.price,
        p.amount,
        p.status AS productStatus
      FROM
        products AS p
    `;

    let conditions = [];
    let values = [];

    if (dao.userID) {
      query += `
        LEFT JOIN
          transactions AS t
          ON p.id = t.product_id
      `;

      if (dao.isBought) {
        conditions.push("p.status = ?");
        values.push("완료");

        conditions.push("t.buyer_id = ?");
        values.push(dao.userID);
      }

      if (dao.isReserved) {
        conditions.push("p.status = ?");
        values.push("예약중");

        conditions.push("(p.seller_id = ? OR t.buyer_id = ?)");
        values.push(...[dao.userID, dao.userID]);
      }
    }

    if (conditions.length) {
      query += `
        WHERE
        ${conditions.join(" AND ")}
      `;
    }

    query += "ORDER BY p.id;";

    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async selectAuthorizedProduct(dao: IProductItem) {
    const pool = this.database.pool;
    const query = `
      SELECT
        p.id AS productID,
        p.seller_id AS sellerID,
        t.buyer_id AS buyerID,
        p.name,
        p.price AS productPrice,
        t.price AS transactionPrice,
        p.amount,
        p.status AS productStatus,
        t.status AS transactionStatus
      FROM
        products AS p
      LEFT JOIN
        transactions AS t
        ON p.id = t.product_id
      WHERE
        p.id = ?
        AND (p.seller_id = ? OR t.buyer_id = ?);
    `;

    const values = [dao.productID, dao.userID, dao.userID];
    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async selectProduct(dao: IProductItem) {
    const pool = this.database.pool;
    const query = `
      SELECT
        id AS productID,
        seller_id AS sellerID,
        name,
        price AS productPrice,
        amount,
        status AS productStatus
      FROM
        products
      WHERE
        id = ?;
    `;

    const values = [dao.productID];
    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }
}
