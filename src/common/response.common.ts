import { HttpStatus } from '@nestjs/common';

export class ResponseEntity<T> {
  private constructor(
    private readonly statusCode: HttpStatus,
    private readonly message: string,
    private readonly data: T,
  ) {
    this.statusCode = statusCode;
    this.message = message;
    this.data = data;
  }

  static SUCCESS(message: string = ''): ResponseEntity<string> {
    return new ResponseEntity<string>(HttpStatus.OK, message, '');
  }

  static SUCCESS_WITH<T>(data: T, message: string = ''): ResponseEntity<T> {
    return new ResponseEntity<T>(HttpStatus.OK, message, data);
  }

  static ERROR(): ResponseEntity<string> {
    return new ResponseEntity<string>(
      HttpStatus.INTERNAL_SERVER_ERROR,
      '서버에서 오류가 발생하였습니다',
      '',
    );
  }

  static ERROR_WITH<T>(
    httpStatus: HttpStatus,
    message: string,
    data: any = {},
  ): ResponseEntity<T> {
    return new ResponseEntity<T>(httpStatus, message, data);
  }
}
