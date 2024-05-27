import express, { json, urlencoded } from "express";
import cookieParser from "cookie-parser";
import logger from "morgan";
import dotenv from "dotenv";

import tokenRouter from "./routes/token.js";
import productsRouter from "./routes/products.js";
import userRouter from "./routes/user.js";
import dbErrorHandler from "./middlewares/dbErrorHandler.js";

var app = express();
dotenv.config();

app.use(logger("dev"));
app.use(json());
app.use(urlencoded({ extended: false }));
app.use(cookieParser());

app.use("/", tokenRouter);
app.use("/products", productsRouter);
app.use("/user", userRouter);

app.use(dbErrorHandler);

export default app;
