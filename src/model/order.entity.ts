import { Column, Entity, JoinColumn, ManyToOne } from 'typeorm';
import { BaseEntityWithId } from './base.entity.withid';
import { ORDER_STATUS } from './enums/order.status.enum';
import { User } from './user.entity';

@Entity()
export class Order extends BaseEntityWithId {

  @Column({ name: "user_id" })
  userId: number;

  @ManyToOne(() => User, (user: User) => user.orderList)
  @JoinColumn({ name: "user_id" })
  user: User;

  @Column({ name: "product_id" })
  productId: number;
  @Column()
  amount: number;
  @Column()
  status: ORDER_STATUS;

  bUser(user: User) {
    this.user = user;
    return this;
  }

  bProductId(productId: number) {
    this.productId = productId;
    return this;
  }

  bAmount(amount: number) {
    this.amount = amount;
    return this;
  }

  bStatus(status: ORDER_STATUS) {
    this.status = status;
    return this;
  }

  static create(productId: number, user: User) {
    return new Order()
      .bAmount(1)
      .bProductId(productId)
      .bUser(user)
      .bStatus(ORDER_STATUS.Wait);
  }

}