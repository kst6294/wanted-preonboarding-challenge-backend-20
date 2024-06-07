import {
  BadRequestException,
  ConflictException,
  Injectable,
  InternalServerErrorException,
  UnauthorizedException,
} from '@nestjs/common';
import { OrderRepository } from './order.repository';
import { Order } from './entity/order.entity';
import { ProductRepository } from 'src/product/product.repository';
import { DataSource } from 'typeorm';
import { Product } from 'src/product/entity/product.entity';
import { ProductStatus } from 'src/product/enum/productStatus.enum';

@Injectable()
export class OrderService {
  constructor(
    private readonly orderRepository: OrderRepository,
    private readonly productReposiotory: ProductRepository,
    private readonly dataSource: DataSource,
  ) {}

  // 제품 예약
  async createOrder(userId: number, productId: number): Promise<Order> {
    const product = await this.productReposiotory.findProductDetail(productId);
    if (product.userId === userId) {
      throw new BadRequestException('자신의 올린 제품을 예약할 수 없습니다.');
    }
    if (product.productStatus === ProductStatus.COMPLETED) {
      throw new ConflictException('판매가 완료된 제품입니다.');
    }
    const order = await this.orderRepository.findOrder(productId);
    if (order) {
      throw new ConflictException('예약이 있는 제품입니다.');
    }

    // DB 트랜잭션 start
    const queryRunner = this.dataSource.createQueryRunner();
    await queryRunner.connect();
    await queryRunner.startTransaction();

    try {
      const order = await this.orderRepository.createOrder(
        queryRunner,
        userId,
        productId,
      );
      await this.productReposiotory.reserveProduct(queryRunner, productId);

      await queryRunner.commitTransaction();
      return order;
    } catch (error) {
      await queryRunner.rollbackTransaction();
      throw new InternalServerErrorException('제품 예약 실패', error.message);
    } finally {
      await queryRunner.release();
    }
  }

  // 구매한 제품 목록 조회
  async findPurchasedProducts(userId: number): Promise<Product[]> {
    return await this.productReposiotory.findPurchasedProducts(userId);
  }

  // 예약중인 제품 목록
  async findReservedProducts(userId: number): Promise<Record<string, any>> {
    const reservedProductsBySeller =
      await this.productReposiotory.findReservedProductsBySeller(userId);
    const reservedProductsByBuyer =
      await this.productReposiotory.findReservedProductsByBuyer(userId);
    return { reservedProductsBySeller, reservedProductsByBuyer };
  }

  // 제품 판매 승인
  async approveProductSale(userId: number, productId: number): Promise<void> {
    const product = await this.productReposiotory.findProductDetail(productId);
    if (product.userId !== userId) {
      throw new UnauthorizedException('제품 판매에 권한이 없습니다.');
    }
    await this.productReposiotory.approveProductSale(productId);
  }
}
