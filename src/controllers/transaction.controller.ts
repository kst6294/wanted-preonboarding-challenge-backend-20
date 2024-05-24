import { Router, Request, Response, NextFunction } from "express";
import IController from "../interfaces/IController";
import { TransactionStatus } from "../interfaces/ITransaction.dto";
import TransactionService from "../services/transaction.service";

class TransactionController implements IController {
  path = "/transactions";
  router = Router();
  service = new TransactionService();

  constructor() {
    this.initRoutes();
  }

  initRoutes() {
    this.router.post(
      this.path + "/:productID/request",
      this.requestTransaction
    );
    this.router.put(this.path + "/:productID/approve", this.approveTransaction);
    this.router.put(this.path + "/:productID/confirm", this.confirmTransaction);
  }

  requestTransaction = async (
    req: Request,
    res: Response,
    next: NextFunction
  ) => {
    try {
      const { userID } = req.cookies;
      const { productID } = req.params;
      const { price } = req.body;

      const dto = {
        productID: Number(productID),
        buyerID: Number(userID),
        price,
        status: TransactionStatus.구매요청,
      };
      await this.service.requestTransaction(dto);

      res.status(201).end();
    } catch (error) {
      next(error);
    }
  };

  approveTransaction = async (
    req: Request,
    res: Response,
    next: NextFunction
  ) => {
    try {
      const { userID } = req.cookies;
      const { productID } = req.params;
      const { buyerID } = req.body;

      const dto = {
        productID: Number(productID),
        sellerID: Number(userID),
        buyerID,
        status: TransactionStatus.판매승인,
      };
      await this.service.approveTransaction(dto);

      res.end();
    } catch (error) {
      next(error);
    }
  };

  confirmTransaction = async (
    req: Request,
    res: Response,
    next: NextFunction
  ) => {
    try {
      const { userID } = req.cookies;
      const { productID } = req.params;

      const dto = {
        productID: Number(productID),
        buyerID: Number(userID),
        status: TransactionStatus.구매확정,
      };
      await this.service.confirmTransaction(dto);

      res.end();
    } catch (error) {
      next(error);
    }
  };
}

module.exports = new TransactionController();
