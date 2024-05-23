import { Router, Request, Response, NextFunction } from "express";
import IController from "../interfaces/IController";
import ProductService from "../services/product.service";

class ProductController implements IController {
  path = "/products";
  router = Router();
  service = new ProductService();

  constructor() {
    this.initRoutes();
  }

  initRoutes() {
    this.router.get(this.path, this.getProducts);
    this.router.get(this.path + "/:productID", this.getProduct);
  }

  getProducts = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { userID } = req.cookies;
      const { isBought, isReserved } = req.query;

      const dto = {
        isBought: isBought && JSON.parse(String(isBought)),
        isReserved: isReserved && JSON.parse(String(isReserved)),
        userID: Number(userID),
      };
      const { meta, data } = await this.service.getProducts(dto);

      res.json({
        meta,
        data,
      });
    } catch (error) {
      next(error);
    }
  };

  getProduct = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { userID } = req.cookies;
      const { productID } = req.params;

      const dto = {
        productID: Number(productID),
        userID: Number(userID),
      };
      const data = await this.service.getProduct(dto);

      res.json({
        data,
      });
    } catch (error) {
      next(error);
    }
  };
}

module.exports = new ProductController();
