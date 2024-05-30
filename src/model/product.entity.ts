import { Column, Entity } from 'typeorm';
import { BaseEntityWithId } from './base.entity.withid';
import { PRODUCT_STATUS } from './enums/product.status.enum';

@Entity()
export class Product extends BaseEntityWithId {

  @Column()
  ownerId: number;

  @Column()
  name: string;

  @Column()
  amount: number;

  @Column()
  price: number;

  @Column()
  status: PRODUCT_STATUS;
}



