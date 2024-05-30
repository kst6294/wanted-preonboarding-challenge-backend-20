import { applyDecorators } from '@nestjs/common';
import { ApiExtraModels, ApiOkResponse, getSchemaPath } from '@nestjs/swagger';

export const OkResponse = <T>(schema: new (...args: any[]) => T) => {
  return applyDecorators(
    ApiExtraModels(schema),
    ApiOkResponse({
      schema: {
        properties: {
          statusCode: {
            type: 'number',
            example: 200,
          },
          message: {
            type: 'string',
            example: 'message',
          },
          data: {
            $ref: getSchemaPath(schema),
          },
        },
      },
    }),
  );
};
