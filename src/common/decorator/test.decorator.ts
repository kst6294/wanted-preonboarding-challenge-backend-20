import { Type, applyDecorators } from '@nestjs/common';
import {
  ApiExtraModels,
  ApiOkResponse,
  ApiResponseOptions,
  getSchemaPath,
} from '@nestjs/swagger';
import { ResponseEntity } from '../response.common';

export const ApiBadRequestExceptionResponse = <TModel extends Type<any>>(
  model: TModel,
  options?: ApiResponseOptions,
) => {
  return applyDecorators(
    ApiExtraModels(ResponseEntity, model),
    ApiOkResponse({
      ...options,
      schema: {
        allOf: [
          { $ref: getSchemaPath(ResponseEntity) },
          {
            properties: {
              data: { type: 'array', items: { $ref: getSchemaPath(model) } },
            },
          },
        ],
      },
    }),
  );
};
