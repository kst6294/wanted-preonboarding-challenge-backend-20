import { Test, TestingModule } from '@nestjs/testing';
import { ProductController } from './product.controller';
import { ProductService } from './product.service';
import { MockProductRepository } from './product.service.spec';
import { Request, Response } from 'express';
import { HttpStatus } from '@nestjs/common';
import { ProductDto } from './dto/product.dto';

class MockProductService {
  findAllProducts = jest.fn();
  findProductDetail = jest.fn();
  createProduct = jest.fn();
}

describe('ProductController', () => {
  let controller: ProductController;
  let service: ProductService;

  const mockProduct = new MockProductRepository().mockProduct;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProductController],
      providers: [{ provide: ProductService, useClass: MockProductService }],
    }).compile();

    controller = module.get<ProductController>(ProductController);
    service = module.get<ProductService>(ProductService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  describe('getAllProducts', () => {
    it('모든 제품의 목록을 return', async () => {
      const res: Partial<Response> = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
      };
      (service.findAllProducts as jest.Mock).mockResolvedValue([
        { mockProduct },
      ]);

      await controller.getAllProducts(res as Response);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith([{ mockProduct }]);
    });
  });

  describe('getProduct', () => {
    it('productId에 해당하는 제품을 상세 조회 후 return', async () => {
      const res: Partial<Response> = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
      };
      const productId = 1;
      (service.findProductDetail as jest.Mock).mockResolvedValue(mockProduct);

      await controller.getProduct(productId, res as Response);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith(mockProduct);
    });
  });

  describe('createProduct', () => {
    it('should create product and return success message', async () => {
      const req: Partial<Request> = {
        body: { productDto: ProductDto },
      };
      const res: Partial<Response> = {
        locals: {
          user: {
            userId: 1,
          },
        },
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
      };
      (service.createProduct as jest.Mock).mockResolvedValue(true);

      await controller.createProduct(req.body, res as Response);
      expect(res.status).toHaveBeenCalledWith(HttpStatus.OK);
      expect(res.json).toHaveBeenCalledWith({ message: '상품 등록 완료' });
    });
  });
});
