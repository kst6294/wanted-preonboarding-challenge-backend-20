import { Order } from 'src/order/entity/order.entity';
import { User } from 'src/user/entity/user.entity';
import {
  Column,
  CreateDateColumn,
  Entity,
  JoinColumn,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';
import { ProductStatus } from '../enum/productStatus.enum';

@Entity('product')
export class Product {
  @PrimaryGeneratedColumn('increment', { type: 'bigint' })
  productId: number;

  @Column({ type: 'varchar' })
  productName: string;

  @Column({ type: 'bigint' })
  productPrice: number;

  @Column({
    type: 'enum',
    enum: ProductStatus,
    default: ProductStatus.SALE,
  })
  productStatus: ProductStatus;

  @CreateDateColumn({ type: 'timestamp' })
  productCreatedAt: Date;

  @UpdateDateColumn({ type: 'timestamp' })
  productUpdateAt: Date;

  // 판매자 userId
  @ManyToOne(() => User, (user) => user.product)
  @JoinColumn({ name: 'userId' })
  userId: number;

  @OneToMany(() => Order, (order) => order.productId)
  order: Order[];
}
