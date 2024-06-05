import { User } from 'src/user/entity/user.entity';
import {
  Column,
  CreateDateColumn,
  Entity,
  JoinColumn,
  ManyToOne,
  PrimaryGeneratedColumn,
  UpdateDateColumn,
} from 'typeorm';

@Entity('product')
export class Product {
  @PrimaryGeneratedColumn('increment', { type: 'bigint' })
  productId: number;

  @Column({ type: 'varchar' })
  productName: string;

  @Column({ type: 'bigint' })
  productPrice: number;

  @Column({ type: 'varchar', default: '판매중' })
  productStatus: string;

  @CreateDateColumn({ type: 'timestamp' })
  productCreatedAt: Date;

  @UpdateDateColumn({ type: 'timestamp' })
  productUpdateAt: Date;

  @ManyToOne(() => User, (user) => user.product)
  @JoinColumn({ name: 'userId' })
  userId: number;
}
