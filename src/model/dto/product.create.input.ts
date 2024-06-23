import { BadRequestException } from '@nestjs/common';
import { IsNotEmpty, IsNumber, IsOptional, Max, Min } from 'class-validator';
import { Product } from '../product.entity';
import { User } from '../user.entity';


export default class ProductInputDto {

  id?: number;

  @IsNotEmpty()
  name: string;

  @IsOptional()
  amount?: number = 1;

  @IsNumber()
  @Min(1)
  @Max(10000)
  price: number;


  validateInput() {
    if (this.amount < 1) {
      throw new BadRequestException("수량은 1이상이여야 합니다.");
    }

    if (this.amount > 1000) {
      throw new BadRequestException("수량은 1000 이하여야 합니다.");
    }

    const specialCharacters = /[!@#$%^&*(),.?":{}|<>]/;
    if (specialCharacters.test(this.name)) {
      throw new BadRequestException("특수문자는 허용하지 않습니다.");
    }
  }

  isNewProduct() {
    return !!this.id;
  }

  createNewProduct(user: User) {
    return new Product()
      .bUser(user)
      .bAmount(this.amount)
      .bPrice(this.price);
  }
}