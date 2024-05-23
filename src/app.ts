import path from "node:path";
import fs from "node:fs";
import express, { Application } from "express";
import cookieParser from "cookie-parser";
import {
  errorMiddleware,
  httpErrorMiddleware,
  logMiddleware,
} from "./middlewares";

export default class App {
  app: Application = express();

  constructor() {
    this.initPreMiddlewares();
    this.initControllers();
    this.initPostMiddlewares();
  }

  listen() {
    this.app.listen(3001, () => console.log("Listening on 3001"));
  }

  initPreMiddlewares() {
    this.app.use(cookieParser());
    this.app.use(express.json());
    this.app.use(logMiddleware);
  }

  initControllers() {
    const files = fs.readdirSync(path.join(__dirname, "/controllers"));

    files.forEach((file) => {
      const controller = require(path.join(__dirname, "/controllers", file));
      this.app.use("/api", controller.router);
    });
  }

  initPostMiddlewares() {
    this.app.use(httpErrorMiddleware);
    this.app.use(errorMiddleware);
  }
}
