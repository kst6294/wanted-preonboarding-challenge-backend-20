import { ResultSetHeader } from "mysql2";
import { faker } from "@faker-js/faker";
import { Status } from "../../src/interfaces/IProduct.dto";
import database from "../../src/database";
import InsertUsers from "../insert-users";
import { getRandomStatus, makeIDIterator, makeIDs } from "../utils";

export default class InsertProducts {
  static size = 100;

  static makeValues() {
    const userIDs = makeIDs(InsertUsers.size);
    const getUserID = makeIDIterator(userIDs);

    const productIDs = makeIDs(this.size);
    return productIDs.map((productID) => [
      productID,
      getUserID(),
      faker.commerce.product(),
      faker.commerce.price({ dec: 0, min: 1_000, max: 10_000 }),
      getRandomStatus(Status),
    ]);
  }

  static async run() {
    const pool = database.pool;
    const query = `
      INSERT INTO
        products
        (
          id,
          seller_id,
          name,
          price,
          status
        )
      VALUES
        ?;
    `;

    const values = this.makeValues();
    const [result] = await pool.query<ResultSetHeader>(query, [values]);
    return result;
  }
}
