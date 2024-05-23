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

      res.json({
        message: "회원가입 되었습니다.",
      });
    } catch (error) {
      next(error);
    }
  };

  logIn = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const dto = req.body;
      const userID = await this.service.logIn(dto);

      res.cookie("userID", userID);
      res.json({
        message: "로그인 되었습니다.",
      });
    } catch (error) {
      next(error);
    }
  };
}

module.exports = new UserController();
