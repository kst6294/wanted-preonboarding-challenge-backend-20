import { Test, TestingModule } from '@nestjs/testing';
import { OrderController } from './order.controller';
import { MockOrderRepository } from './order.service.spec';
import { OrderService } from './order.service';
import { Response } from 'express';
import { HttpStatus } from '@nestjs/common';
import { MockProductRepository } from 'src/product/product.service.spec';

class MockOrderService {
  createOrder = jest.fn();
  findPurchasedProducts = jest.fn();
  findReservedProducts = jest.fn();
  approveProductSale = jest.fn();
}

describe('OrderController', () => {
  let controller: OrderController;
  let service: OrderService;

  const mockOrder = new MockOrderRepository().mockOrder;
  const mockProduct = new MockProductRepository().mockProduct;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [OrderController],
      providers: [{ provide: OrderService, useClass: MockOrderService }],
    }).compile();

    controller = module.get<OrderController>(OrderController);
    service = module.get<OrderService>(OrderService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  describe('createOrder', () => {
    it('제품 예약을 성공적으로 처리', async () => {
      const userId = 1;
      const productId = 1;
      const res: Partial<Response> = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
        locals: {
          user: {
            userId: userId,
          },
        },
      };
      await controller.createOrder(productId, res as Response);
      expect(service.createOrder).toHaveBeenCalledWith(userId, productId);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith({ message: '제품 예약 완료' });
    });
  });

  describe('getPurchasedProducts', () => {
    it('구매한 제품 목록을 성공적으로 가져와야 한다', async () => {
      const userId = 1;
      const res: Partial<Response> = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
        locals: {
          user: {
            userId: userId,
          },
        },
      };
      const purchasedProducts = [{ mockProduct }];
      (service.findPurchasedProducts as jest.Mock).mockResolvedValue(
        purchasedProducts,
      );

      await controller.getPurchasedProducts(res as Response);
      expect(service.findPurchasedProducts).toHaveBeenCalledWith(userId);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(purchasedProducts);
    });
  });

  describe('getReservedProducts', () => {
    it('예약중인 제품 목록을 성공적으로 가져와야 한다', async () => {
      const userId = 1;
      const res: Partial<Response> = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
        locals: {
          user: {
            userId: userId,
          },
        },
      };
      const reservedProducts = {
        reservedProductsBySeller: [],
        reservedProductsByBuyer: [],
      };
      (service.findReservedProducts as jest.Mock).mockResolvedValue(
        reservedProducts,
      );

      await controller.getReservedProducts(res as Response);
      expect(service.findReservedProducts).toHaveBeenCalledWith(userId);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(reservedProducts);
    });
  });

  describe('approveProductSale', () => {
    it('제품 판매 승인을 성공적으로 처리해야 한다', async () => {
      const userId = 1;
      const productId = 1;
      const res: Partial<Response> = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
        locals: {
          user: {
            userId: userId,
          },
        },
      };
      await controller.approveProductSale(productId, res as Response);
      expect(service.approveProductSale).toHaveBeenCalledWith(
        userId,
        productId,
      );
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith({ message: '판매 승인 완료' });
    });
  });
});
