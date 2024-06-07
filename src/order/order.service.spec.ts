import { Test, TestingModule } from '@nestjs/testing';
import { OrderService } from './order.service';
import { Order } from './entity/order.entity';
import { OrderRepository } from './order.repository';
import { ProductRepository } from 'src/product/product.repository';
import { MockProductRepository } from 'src/product/product.service.spec';
import { DataSource } from 'typeorm';
import {
  BadRequestException,
  ConflictException,
  InternalServerErrorException,
} from '@nestjs/common';
import { ProductStatus } from 'src/product/enum/productStatus.enum';

export class MockOrderRepository {
  readonly mockOrder: Order = {
    orderId: 1,
    productId: 1,
    userId: 2,
  };
  createOrder = jest.fn();
  findOrder = jest.fn();
}

class MockDataSource {
  createQueryRunner = jest.fn(() => ({
    connect: jest.fn(),
    startTransaction: jest.fn(),
    commitTransaction: jest.fn(),
    rollbackTransaction: jest.fn(),
    release: jest.fn(),
  }));
}

describe('OrderService', () => {
  let service: OrderService;
  let orderRepository: OrderRepository;
  let productRepository: ProductRepository;
  let dataSource: DataSource;

  const mockOrder = new MockOrderRepository().mockOrder;
  const mockProduct = new MockProductRepository().mockProduct;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        OrderService,
        { provide: ProductRepository, useClass: MockProductRepository },
        { provide: OrderRepository, useClass: MockOrderRepository },
        { provide: DataSource, useClass: MockDataSource },
      ],
    }).compile();

    service = module.get<OrderService>(OrderService);
    orderRepository = module.get<OrderRepository>(OrderRepository);
    productRepository = module.get<ProductRepository>(ProductRepository);
    dataSource = module.get<DataSource>(DataSource);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('createOrder', () => {
    it('자신의 올린 제품을 예약할 수 없음을 확인', async () => {
      jest
        .spyOn(productRepository, 'findProductDetail')
        .mockResolvedValue({ ...mockProduct, userId: 2 });

      await expect(service.createOrder(2, 1)).rejects.toThrow(
        BadRequestException,
      );
    });

    it('판매가 완료된 제품을 예약할 수 없음을 확인', async () => {
      jest.spyOn(productRepository, 'findProductDetail').mockResolvedValue({
        ...mockProduct,
        productStatus: ProductStatus.COMPLETED,
      });

      await expect(service.createOrder(2, 1)).rejects.toThrow(
        ConflictException,
      );
    });

    it('이미 예약이 있는 제품을 예약할 수 없음을 확인', async () => {
      jest
        .spyOn(productRepository, 'findProductDetail')
        .mockResolvedValue(mockProduct);
      jest.spyOn(orderRepository, 'findOrder').mockResolvedValue(mockOrder);

      await expect(service.createOrder(2, 1)).rejects.toThrow(
        ConflictException,
      );
    });
  });

  it('제품 예약을 성공적으로 처리해야 한다', async () => {
    jest
      .spyOn(productRepository, 'findProductDetail')
      .mockResolvedValue(mockProduct);
    jest.spyOn(orderRepository, 'findOrder').mockResolvedValue(null);
    jest.spyOn(orderRepository, 'createOrder').mockResolvedValue(mockOrder);
    jest
      .spyOn(productRepository, 'reserveProduct')
      .mockResolvedValue(undefined);
    const queryRunner = dataSource.createQueryRunner();
    jest.spyOn(queryRunner, 'commitTransaction').mockResolvedValue(undefined);

    const result = await service.createOrder(2, 1);
    expect(result).toEqual(mockOrder);
  });

  it('트랜잭션 실패 시 롤백해야 한다', async () => {
    jest
      .spyOn(productRepository, 'findProductDetail')
      .mockResolvedValue(mockProduct);
    jest.spyOn(orderRepository, 'findOrder').mockResolvedValue(null);
    jest.spyOn(orderRepository, 'createOrder').mockImplementation(() => {
      throw new Error('Error');
    });
    const queryRunner = dataSource.createQueryRunner();
    jest.spyOn(queryRunner, 'rollbackTransaction').mockResolvedValue(undefined);

    await expect(service.createOrder(2, 1)).rejects.toThrow(
      InternalServerErrorException,
    );
  });
});
