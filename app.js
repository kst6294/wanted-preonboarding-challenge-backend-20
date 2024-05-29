import express, { json, urlencoded } from "express";
import cookieParser from "cookie-parser";
import logger from "morgan";
import dotenv from "dotenv";

import tokenRouter from "./routes/token.js";
import productsRouter from "./routes/products.js";
import ordersRouter from "./routes/orders.js";
import usersRouter from "./routes/users.js";
import dbErrorHandler from "./middlewares/dbErrorHandler.js";

var app = express();
dotenv.config();

app.use(logger("dev"));
app.use(json());
app.use(urlencoded({ extended: false }));
app.use(cookieParser());

app.use("/token", tokenRouter);
app.use("/products", productsRouter);
app.use("/orders", ordersRouter);
app.use("/users", usersRouter);

app.use(dbErrorHandler);

export default app;
