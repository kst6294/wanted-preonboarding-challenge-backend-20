import mysql, { Pool, PoolOptions } from "mysql2/promise";

class Database {
  options: PoolOptions = {
    host: "localhost",
    user: "root",
    password: "root",
    database: "pre-task",
  };

  readonly pool: Pool;

  constructor() {
    this.pool = mysql.createPool(this.options);
  }

  connect() {
    this.pool
      .query("SELECT 1;")
      .then(() => console.log("Connected on 3306"))
      .catch((error) => console.error(error));
  }
}

export default new Database();
