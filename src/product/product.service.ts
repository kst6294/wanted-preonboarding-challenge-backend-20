import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { ProductRepository } from './product.repository';
import { Product } from './entity/product.entity';
import { ProductDto } from './dto/product.dto';

@Injectable()
export class ProductService {
  constructor(private readonly productRepository: ProductRepository) {}

  // 제품 목록 조회
  async findAllProducts(): Promise<Product[]> {
    return await this.productRepository.findAllProducts();
  }

  // 제품 상세 조회
  async findProductDetail(productId: number): Promise<Product> {
    const product = await this.productRepository.findProductDetail(productId);
    if (!product) {
      throw new NotFoundException('존재하지 않는 상품입니다.');
    }
    return product;
  }

  // 제품 올리기
  async createProduct(
    userId: number,
    productDto: ProductDto,
  ): Promise<Product> {
    const product = await this.productRepository.createProduct(
      userId,
      productDto,
    );
    if (!product) {
      return null;
    }
    return product;
  }
}
