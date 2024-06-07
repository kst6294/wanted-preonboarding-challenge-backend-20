import { Test, TestingModule } from '@nestjs/testing';
import { ProductService } from './product.service';
import { Product } from './entity/product.entity';
import { ProductRepository } from './product.repository';
import { ProductDto } from './dto/product.dto';
import { ProductStatus } from './enum/productStatus.enum';
import { MockOrderRepository } from 'src/order/order.service.spec';
import { NotFoundException, UnauthorizedException } from '@nestjs/common';

export class MockProductRepository {
  readonly mockProduct: Product = {
    productId: 1,
    productName: '아이패드',
    productPrice: 100000,
    productStatus: ProductStatus.SALE,
    productCreatedAt: new Date(),
    productUpdateAt: new Date(),
    userId: 1,
    order: [],
  };
  findAllProducts = jest.fn();
  findProductDetail = jest.fn();
  createProduct = jest.fn();
  reserveProduct = jest.fn();
  approveProductSale = jest.fn();
  findPurchasedProducts = jest.fn();
  findReservedProductsBySeller = jest.fn();
  findReservedProductsByBuyer = jest.fn();
  findTransactionHistory = jest.fn();
}

describe('ProductService', () => {
  let service: ProductService;
  let productRepository: ProductRepository;

  const mockProduct = new MockProductRepository().mockProduct;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        { provide: ProductRepository, useClass: MockProductRepository },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
    productRepository = module.get<ProductRepository>(ProductRepository);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('findAllProducts', () => {
    it('모든 제품의 목록을 return', async () => {
      (productRepository.findAllProducts as jest.Mock).mockResolvedValue([
        { mockProduct },
      ]);

      const result = await service.findAllProducts();

      expect(result).toEqual([{ mockProduct }]);
    });
  });

  describe('findProductDetail', () => {
    it('productId에 맞는 제품의 상세 정보를 return', async () => {
      const productId = 1;

      (productRepository.findProductDetail as jest.Mock).mockResolvedValue(
        mockProduct,
      );

      const result = await service.findProductDetail(productId);

      expect(result).toEqual(mockProduct);
    });

    it('존재하지 않는 제품 ID로 요청할 경우 NotFoundException을 throw', async () => {
      const productId = 2;

      (productRepository.findProductDetail as jest.Mock).mockResolvedValue(
        null,
      );

      await expect(service.findProductDetail(productId)).rejects.toThrow(
        NotFoundException,
      );
    });
  });

  describe('createProduct', () => {
    it('제품 생성 후 제품 정보 return', async () => {
      const userId = 1;
      const productDto: ProductDto = {
        productName: '아이패드',
        productPrice: 100000,
      };
      (productRepository.createProduct as jest.Mock).mockResolvedValue(
        mockProduct,
      );

      const result = await service.createProduct(userId, productDto);
      expect(result).toEqual(mockProduct);
      expect(productRepository.createProduct).toHaveBeenCalledWith(
        userId,
        productDto,
      );
    });
  });

  describe('approveProductSale', () => {
    it('제품 판매 승인이 정상적으로 이루어져야 한다', async () => {
      const userId = 1;
      const productId = 1;

      (productRepository.findProductDetail as jest.Mock).mockResolvedValue(
        mockProduct,
      );

      await service.approveProductSale(userId, productId);

      expect(productRepository.approveProductSale).toHaveBeenCalledWith(
        productId,
      );
    });

    it('권한 없는 사용자가 제품 판매 승인을 시도할 경우 UnauthorizedException을 throw', async () => {
      const userId = 2;
      const productId = 1;

      (productRepository.findProductDetail as jest.Mock).mockResolvedValue(
        mockProduct,
      );

      await expect(
        service.approveProductSale(userId, productId),
      ).rejects.toThrow(UnauthorizedException);
    });
  });

  describe('findTransactionHistory', () => {
    it('사용자와 판매자의 거래 내역을 return', async () => {
      const userId = 1;
      const sellerId = 2;
      const mockTransactionHistory = [mockProduct];

      (productRepository.findTransactionHistory as jest.Mock).mockResolvedValue(
        mockTransactionHistory,
      );

      const result = await service.findTransactionHistory(userId, sellerId);

      expect(result).toEqual(mockTransactionHistory);
      expect(productRepository.findTransactionHistory).toHaveBeenCalledWith(
        userId,
        sellerId,
      );
    });
  });
});
