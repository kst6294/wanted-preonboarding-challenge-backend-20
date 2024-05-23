import { Router, Request, Response, NextFunction } from "express";
import IController from "../interfaces/IController";
import TransactionService from "../services/transaction.service";

class TransactionController implements IController {
  path = "/transactions";
  router = Router();
  service = new TransactionService();

  constructor() {
    this.initRoutes();
  }

  initRoutes() {
    this.router.post(this.path + "/:productID", this.postTransaction);
    this.router.put(this.path + "/:productID", this.putTransaction);
  }

  postTransaction = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { userID } = req.cookies;
      const { productID } = req.params;
      const { status } = req.body;

      const dto = {
        productID: Number(productID),
        userID: Number(userID),
        status,
      };
      await this.service.postTransaction(dto);

      res.status(201).end();
    } catch (error) {
      next(error);
    }
  };

  putTransaction = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { userID } = req.cookies;
      const { productID } = req.params;
      const { buyerID, status } = req.body;

      const dto = {
        productID: Number(productID),
        userID: Number(userID),
        buyerID,
        status,
      };
      await this.service.putTransaction(dto);

      res.end();
    } catch (error) {
      next(error);
    }
  };
}

module.exports = new TransactionController();
