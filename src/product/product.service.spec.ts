import { Test, TestingModule } from '@nestjs/testing';
import { ProductService } from './product.service';
import { Product } from './entity/product.entity';
import { ProductRepository } from './product.repository';
import { ProductDto } from './dto/product.dto';
import { InternalServerErrorException } from '@nestjs/common';

export class MockProductRepository {
  readonly mockProduct: Product = {
    productId: 1,
    productName: '아이패드',
    productPrice: 100000,
    productStatus: '판매중',
    productCreatedAt: new Date(),
    productUpdateAt: new Date(),
    userId: 1,
  };
  findAllProducts = jest.fn();
  findProductDetail = jest.fn();
  createProduct = jest.fn();
}

describe('ProductService', () => {
  let service: ProductService;
  let repository: ProductRepository;

  const mockProduct = new MockProductRepository().mockProduct;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        { provide: ProductRepository, useClass: MockProductRepository },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
    repository = module.get<ProductRepository>(ProductRepository);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('findAllProducts', () => {
    it('모든 제품의 목록을 return', async () => {
      (repository.findAllProducts as jest.Mock).mockResolvedValue([
        { mockProduct },
      ]);

      const result = await service.findAllProducts();

      expect(result).toEqual([{ mockProduct }]);
    });
  });

  describe('findProductDetail', () => {
    it('productId에 맞는 제품의 상세 정보를 return', async () => {
      const productId = 1;

      (repository.findProductDetail as jest.Mock).mockResolvedValue(
        mockProduct,
      );

      const result = await service.findProductDetail(productId);

      expect(result).toEqual(mockProduct);
    });
  });

  describe('createProduct', () => {
    it('제품 생성 후 제품 정보 return', async () => {
      const userId = 1;
      const productDto: ProductDto = {
        productName: '아이패드',
        productPrice: 100000,
      };
      (repository.createProduct as jest.Mock).mockResolvedValue(mockProduct);

      const result = await service.createProduct(userId, productDto);
      expect(result).toEqual(mockProduct);
      expect(repository.createProduct).toHaveBeenCalledWith(userId, productDto);
    });
  });
});
