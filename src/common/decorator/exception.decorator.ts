import { applyDecorators } from '@nestjs/common';
import { ApiResponse } from '@nestjs/swagger';

export const Exception = (status: number, description: string) => {
  return applyDecorators(
    ApiResponse({
      status,
      description,
    }),
  );
};
