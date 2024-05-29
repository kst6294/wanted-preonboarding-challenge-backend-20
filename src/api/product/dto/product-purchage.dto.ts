import { IsNotEmpty, IsNumber } from 'class-validator';
import { IProduct } from '../interface/product.interface';

export class ProductPurchaseRequestDto implements IProduct.IPurchaseRequest {
  /**
   * 구매 할 제품 인덱스
   * @example 1
   */
  @IsNotEmpty()
  @IsNumber()
  productIdx: number;
}

export class ProductPurchaseResponseDto implements IProduct.IPurchaseResponse {
  /**
   * 거래 번호
   * @example 1
   */
  transactionIdx: number;

  static of(input: IProduct.IPurchaseResponse): ProductPurchaseResponseDto {
    return {
      transactionIdx: input.transactionIdx,
    };
  }
}
