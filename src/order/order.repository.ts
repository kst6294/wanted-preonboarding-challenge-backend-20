import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Order } from './entity/order.entity';
import { QueryRunner, Repository } from 'typeorm';

@Injectable()
export class OrderRepository {
  constructor(
    @InjectRepository(Order) private orderRepository: Repository<Order>,
  ) {}

  // 제품 예약
  async createOrder(
    queryRunner: QueryRunner,
    userId: number,
    productId: number,
  ): Promise<Order> {
    const newOrder = new Order();
    newOrder.productId = productId;
    newOrder.userId = userId;
    return await queryRunner.manager.save(newOrder);
  }

  // 주문 확인
  async findOrder(productId: number): Promise<Order> {
    return await this.orderRepository
      .createQueryBuilder('order')
      .select(['userId'])
      .getRawOne();
  }
}
