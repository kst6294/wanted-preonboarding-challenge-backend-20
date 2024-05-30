import { HttpException, HttpStatus } from '@nestjs/common';

class WantedCustomException extends HttpException {
  constructor() {
    super('Forbidden', HttpStatus.FORBIDDEN);
  }
}

export default WantedCustomException;
