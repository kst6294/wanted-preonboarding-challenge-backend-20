import { ProductDetailResponseDto } from './product-detail.dto';
import { TransactionHistoryReseponseDto } from '../../transaction/dto/transaction-history.dto';
import { IProduct } from '../interface/product.interface';

export class ProductDetailWithTransactionResponseDto extends ProductDetailResponseDto {
  /**
   * 거래 내역
   */
  transactionHistory: TransactionHistoryReseponseDto;

  static of(
    input: IProduct.IProductDetailWithTransactionResponse,
  ): ProductDetailWithTransactionResponseDto {
    return {
      ...ProductDetailResponseDto.of(input),
      transactionHistory: input.transactionHistory,
    };
  }
}
