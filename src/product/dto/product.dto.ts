import { IsNotEmpty, IsNumber, IsString } from 'class-validator';

export class ProductDto {
  @IsNotEmpty()
  @IsString()
  readonly productName: string;

  @IsNotEmpty()
  @IsNumber()
  readonly productPrice: number;
}
