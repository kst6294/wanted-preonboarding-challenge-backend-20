import { applyDecorators } from '@nestjs/common';
import { ApiOkResponse } from '@nestjs/swagger';
import {
  ReferenceObject,
  SchemaObject,
} from '@nestjs/swagger/dist/interfaces/open-api-spec.interface';

export const ApiCommonResponse = (
  obj: SchemaObject & Partial<ReferenceObject>,
  message: string = '',
) => {
  return applyDecorators(
    ApiOkResponse({
      schema: {
        properties: {
          statusCode: {
            type: 'number',
            example: 200,
          },
          message: {
            type: 'string',
            example: message,
          },
          data: {
            ...obj,
          },
        },
      },
    }),
  );
};
