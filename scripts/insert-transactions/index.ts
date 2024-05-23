import { ResultSetHeader } from "mysql2";
import { Status } from "../../src/interfaces/ITransaction.dto";
import database from "../../src/database";
import InsertProducts from "../insert-products";
import InsertUsers from "../insert-users";
import {
  getRandomStatus,
  makeIDIterator,
  makeIDs,
  makeRandomIDMaker,
} from "../utils";

export default class InsertTransactions {
  static size = 1000;

  static makeValues() {
    const userIDs = makeIDs(InsertUsers.size);
    const getSellerID = makeIDIterator(userIDs);
    const getBuyerID = makeRandomIDMaker(InsertUsers.size);

    const productIDs = makeIDs(InsertProducts.size);
    const getProductID = makeIDIterator(productIDs);

    const transactionIDs = makeIDs(this.size);
    return transactionIDs.map((transactionID) => {
      const sellerID = getSellerID();
      let buyerID = getBuyerID();

      while (sellerID === buyerID) buyerID = getBuyerID();

      return [transactionID, getProductID(), buyerID, getRandomStatus(Status)];
    });
  }

  static async run() {
    const pool = database.pool;
    const query = `
      INSERT INTO
        transactions
        (
          id,
          product_id,
          buyer_id,
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
