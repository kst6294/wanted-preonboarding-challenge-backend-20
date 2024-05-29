import { IsNotEmpty, IsNumber, IsString } from 'class-validator';
import { IProduct } from '../interface/product.interface';
import { Type } from 'class-transformer';

export class ProductRegisterRequestDto implements IProduct.IRegisterRequest {
  /**
   * 제품 이름
   * @example 노트북
   */
  @IsString()
  @IsNotEmpty()
  name: string;

  /**
   * 제품 가격
   * @example 500000
   */
  @IsNumber()
  @IsNotEmpty()
  @Type(() => Number)
  price: number;
}

export class ProductRegisterResponseDto implements IProduct.IRegisterResponse {
  /**
   * 생성된 제품 인덱스
   * @example 1
   */
  idx: number;

  static of(input: IProduct.IRegisterResponse): ProductRegisterResponseDto {
    return {
      idx: input.idx,
    };
  }
}
