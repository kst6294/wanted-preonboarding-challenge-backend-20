import { ProductSummaryResponseDto } from '../../product/dto/product-summary.dto';
import { IProduct } from '../../product/interface/product.interface';
import { IUser } from '../interface/user.interface';

export class SoldProductListResponseDto implements IUser.ISoldProductResponse {
  /**
   * 내가 판매 완료 된 상품 목록
   */
  soldProduct: ProductSummaryResponseDto[];

  static of(input: IProduct.ISummaryResponse[]): SoldProductListResponseDto {
    return {
      soldProduct: input,
    };
  }
}
