import { $Enums } from '@prisma/client';
import { IProduct } from '../interface/product.interface';
import { IsNotEmpty, IsNumber } from 'class-validator';
import { Type } from 'class-transformer';

export class ProductDetailResquestDto {
  /**
   * 제품 인덱스
   * @example 1
   */
  @IsNotEmpty()
  @IsNumber()
  @Type(() => Number)
  productIdx: IProduct['idx'];
}

export class ProductDetailResponseDto
  implements IProduct.IProductDetailResopnse
{
  /**
   * 제품 인덱스
   * @example 1
   */
  idx: number;

  /**
   * 판매자 인덱스
   * @example 1
   */
  sellerIdx: number;

  /**
   * 제품명
   * @example '아이폰13'
   */
  name: string;

  /**
   * 가격
   * @example 1000000
   */
  price: number;

  /**
   * 상태
   * @example AVAILABLE | RESERVED | SOLD_OUT
   */
  status: $Enums.ProductStatus;

  /**
   * 생성일
   * @example '2021-09-01T00:00:00.000Z'
   */
  createdAt: Date;

  /**
   * 수정일
   * @example '2021-09-01T00:00:00.000Z'
   */
  updatedAt: Date;

  static of(
    product: IProduct.IProductDetailResopnse,
  ): ProductDetailResponseDto {
    return {
      idx: product.idx,
      sellerIdx: product.sellerIdx,
      name: product.name,
      price: product.price,
      status: product.status,
      createdAt: product.createdAt,
      updatedAt: product.updatedAt,
    };
  }
}
