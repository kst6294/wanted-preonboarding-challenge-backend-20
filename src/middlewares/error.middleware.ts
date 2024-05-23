import { Request, Response, NextFunction } from "express";

export default function errorMiddleware(
  error: Error,
  req: Request,
  res: Response,
  next: NextFunction
) {
  let log = [error, req.cookies && `with {"userID":${req.cookies.userID}}`];
  console.warn(log.join(" "));

  const status = 500;
  const message = error.message || "서버 내부에서 에러가 발생했습니다.";

  res.status(status).json({
    message,
  });
}
