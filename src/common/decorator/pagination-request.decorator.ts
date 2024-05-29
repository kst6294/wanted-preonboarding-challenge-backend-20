import { applyDecorators } from '@nestjs/common';
import { ApiQuery } from '@nestjs/swagger';

export const ApiPagenationRequest = () => {
  return applyDecorators(
    ApiQuery({
      name: 'page',
      type: Number,
      required: false,
      example: 1,
      description: '요청할 페이지 번호',
    }),
    ApiQuery({
      name: 'limit',
      type: Number,
      required: false,
      example: 10,
      description: '요청할 페이지당 데이터 수',
    }),
  );
};
