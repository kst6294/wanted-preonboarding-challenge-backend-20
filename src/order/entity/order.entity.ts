import { Product } from 'src/product/entity/product.entity';
import { User } from 'src/user/entity/user.entity';
import {
  Column,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
} from 'typeorm';

@Entity('order')
export class Order {
  @PrimaryGeneratedColumn('increment', { type: 'bigint' })
  orderId: number;

  // 구매자 userId
  @ManyToOne(() => User, (user) => user.order)
  @JoinColumn({ name: 'userId' })
  userId: number;

  // 제품 번호
  @ManyToOne(() => Product, (product) => product.order)
  @JoinColumn({ name: 'productId' })
  productId: number;
}
