import { IsNotEmpty, IsNumber } from 'class-validator';
import { IProduct } from '../interface/product.interface';
import { Type } from 'class-transformer';

export class ProductApproveRequestDto
  implements IProduct.IProductApproveRequest
{
  /**
   * 승인할 제품 인덱스
   * @example 1
   */
  @IsNotEmpty()
  @IsNumber()
  @Type(() => Number)
  idx: number;
}
