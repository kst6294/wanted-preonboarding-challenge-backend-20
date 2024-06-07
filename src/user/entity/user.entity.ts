import { Order } from 'src/order/entity/order.entity';
import { Product } from 'src/product/entity/product.entity';
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from 'typeorm';

@Entity('user')
export class User {
  @PrimaryGeneratedColumn('increment', { type: 'bigint' })
  userId: number;

  @Column({ type: 'varchar' })
  name: string;

  @Column({ type: 'varchar' })
  email: string;

  @Column({ type: 'varchar' })
  password: string;

  @OneToMany(() => Product, (product) => product.userId)
  product: Product[];

  @OneToMany(() => Order, (order) => order.userId)
  order: Order[];
}
