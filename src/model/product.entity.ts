import { Column, Entity, JoinColumn, ManyToOne } from 'typeorm';
import { BaseEntityWithId } from './base.entity.withid';
import { PRODUCT_STATUS } from './enums/product.status.enum';
import { User } from './user.entity';
import { ProductUpdateInput } from './dto/product.update.input';
import ProductInputDto from './dto/product.create.input';
import { NotAcceptableException } from '@nestjs/common';

@Entity()
export class Product extends BaseEntityWithId {

  @Column()
  user_id: number;

  @ManyToOne(() => User, (user: User) => user.productList)
  @JoinColumn({ name: "user_id" })
  user: User;

  @Column()
  name: string;

  @Column()
  amount: number;

  @Column()
  price: number;

  @Column()
  status: PRODUCT_STATUS;

  bUser(user: User) {
    this.user = user;
    return this;
  }

  bName(name: string) {
    this.name = name;
    return this;
  }

  bAmount(amount: number) {
    this.amount = amount;
    return this;
  }

  bPrice(price: number) {
    this.price = price;
    return this;
  }

  bStatus(status: PRODUCT_STATUS) {
    this.status = status;
    return this;
  }

  purchase() {
    if (this.amount < 1) {
      throw new NotAcceptableException("product의 수량이 1 미만입니다.");
    }

    this.amount -= 1;
    this.status = PRODUCT_STATUS.Reserved;
    return this;
  }

  update(input: ProductUpdateInput) {
    const { name, status, price } = input;
    this.name = name;
    this.status = status;
    this.price = price;
    return this;
  }

  static create(input: ProductInputDto, user: User) { // user Static Context로 뽑아내기.
    return new Product()
      .bUser(user)
      .bName(input.name)
      .bAmount(input.amount)
      .bPrice(input.price)
      .bStatus(PRODUCT_STATUS.Available);
  }


}



