import { Router, Request, Response, NextFunction } from "express";
import IController from "../interfaces/IController";
import UserService from "../services/user.service";

class UserController implements IController {
  path = "/users";
  router = Router();
  service = new UserService();

  constructor() {
    this.initRoutes();
  }

  initRoutes() {
    this.router.post(this.path + "/sign-up", this.signUp);
    this.router.post(this.path + "/log-in", this.logIn);
  }

  signUp = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const dto = req.body;
      await this.service.signUp(dto);

      res.status(201).end();
    } catch (error) {
      next(error);
    }
  };

  logIn = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const dto = req.body;
      const userID = await this.service.logIn(dto);

      res.cookie("userID", userID);
      res.status(201).end();
    } catch (error) {
      next(error);
    }
  };
}

module.exports = new UserController();
