import { ProductSummaryResponseDto } from '../../product/dto/product-summary.dto';
import { IUser } from '../interface/user.interface';

export class ReservedProductResponseDto
  implements IUser.IReservedProductResponse
{
  /**
   * 예약된 상품 목록
   */
  reservedProduct: ProductSummaryResponseDto[];

  static of(
    reservedProduct: ProductSummaryResponseDto[],
  ): ReservedProductResponseDto {
    return {
      reservedProduct,
    };
  }
}
