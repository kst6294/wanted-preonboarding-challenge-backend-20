import { ResultSetHeader } from "mysql2";
import { faker } from "@faker-js/faker";
import { ProductStatus } from "../../src/interfaces/IProduct.dto";
import database from "../../src/database";

interface Param {
  amount: number;
  userID: number;
}

export default class InsertProduct {
  static makeValues({ amount, userID }: Param) {
    return [
      userID,
      faker.commerce.product(),
      faker.commerce.price({ dec: 0, min: 1_000, max: 10_000 }),
      amount,
      ProductStatus.판매중,
    ];
  }

  static async run({ amount, userID }: Param) {
    const pool = database.pool;
    const query = `
      INSERT INTO
        products
        (
          seller_id,
          name,
          price,
          amount,
          status
        )
      VALUES
        (?, ?, ?, ?, ?);
    `;

    const values = this.makeValues({ amount, userID });
    const [result] = await pool.query<ResultSetHeader>(query, values);
    return result;
  }
}
