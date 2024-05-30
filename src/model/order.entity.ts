import { Column } from 'typeorm';
import { BaseEntityWithId } from './base.entity.withid';
import { ORDER_STATUS } from './enums/order.status.enum';


export class Order extends BaseEntityWithId {

  @Column()
  userId: number;
  @Column()
  productId: number;
  @Column()
  amount: number;
  @Column()
  status: ORDER_STATUS;
}