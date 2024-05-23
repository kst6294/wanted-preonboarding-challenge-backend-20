import { Request, Response, NextFunction } from "express";
import HttpError from "../errors/HttpError";

export default function httpErrorMiddleware(
  error: HttpError,
  req: Request,
  res: Response,
  next: NextFunction
) {
  if (!(error instanceof HttpError)) return next(error);

  let log = [error, req.cookies && `with {"userID":${req.cookies.userID}}`];
  console.log(log.join(" "));

  const status = error.status;
  const message = error.message;

  res.status(status).json({
    message,
  });
}
