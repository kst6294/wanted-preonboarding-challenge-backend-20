import { ResultSetHeader } from "mysql2";
import { faker } from "@faker-js/faker";
import database from "../../src/database";
import { makeIDs } from "../utils";

export default class InsertUsers {
  static size = 10;

  static makeValues() {
    const userIDs = makeIDs(this.size);
    return userIDs.map((userID) => [
      userID,
      faker.internet.userName(),
      faker.internet.password(),
    ]);
  }

  static async run() {
    const pool = database.pool;
    const query = `
      INSERT INTO
        users
        (
          id,
          name,
          password
        )
      VALUES
        ?;
    `;

    const values = this.makeValues();
    const [result] = await pool.query<ResultSetHeader>(query, [values]);
    return result;
  }
}
