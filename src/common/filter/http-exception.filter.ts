import {
  ArgumentsHost,
  Catch,
  ExceptionFilter,
  HttpException,
} from '@nestjs/common';
import { Response } from 'express';
import { ResponseEntity } from '../response.common';

@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
  catch(e: HttpException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const res: Response = ctx.getResponse();

    const statusCode = e.getStatus();
    let errorMessage = (e.getResponse() as Error).message;

    res
      .status(statusCode)
      .send(ResponseEntity.ERROR_WITH(statusCode, errorMessage));
  }
}
