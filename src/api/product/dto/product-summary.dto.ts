import { ProductStatus } from '@prisma/client';
import { IProduct } from '../interface/product.interface';

export class ProductSummaryResponseDto implements IProduct.ISummaryResponse {
  /**
   * 제품 인덱스
   * @example 1
   */
  idx: number;

  /**
   * 제품 이름
   * @example '커피'
   */
  name: string;

  /**
   * 제품 가격
   * @example 3000
   */
  price: number;

  /**
   * 제품 상태
   * @example AVAILABLE | RESERVED
   */
  status: ProductStatus;

  /**
   * 제품 생성일
   * @example 2021-07-01T00:00:00.000Z
   */
  createdAt: Date;

  static of(input: IProduct.ISummaryResponse[]): ProductSummaryResponseDto[] {
    return input.map((product) => ({
      idx: product.idx,
      name: product.name,
      price: product.price,
      status: product.status,
      createdAt: product.createdAt,
    }));
  }
}
