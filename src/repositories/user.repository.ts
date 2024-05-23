import { ResultSetHeader, RowDataPacket } from "mysql2";
import IUser from "../interfaces/IUser.dto";
import database from "../database";

export default class UserRepository {
  database = database;

  async selectUser(name: string) {
    const pool = this.database.pool;
    const query = `
      SELECT
        id AS userID,
        name,
        password
      FROM
        users
      WHERE
        name = ?;
    `;

    const values = [name];
    const [result] = await pool.query<RowDataPacket[]>(query, values);
    return result;
  }

  async insertUser(dao: IUser) {
    const pool = this.database.pool;
    const query = `
      INSERT INTO
        users
        (
          name,
          password
        )
      VALUES
        (?, ?);
    `;

    const values = [dao.name, dao.password];
    await pool.query<ResultSetHeader>(query, values);
  }
}
