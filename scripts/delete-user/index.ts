import { ResultSetHeader } from "mysql2";
import database from "../../src/database";

interface Param {
  userID: number;
}

export default class DeleteUser {
  static async run({ userID }: Param) {
    const pool = database.pool;
    const query = `
      DELETE FROM
        users
      WHERE
        id = ?;
    `;

    const values = [userID];
    const [result] = await pool.query<ResultSetHeader>(query, values);
    return result;
  }
}
