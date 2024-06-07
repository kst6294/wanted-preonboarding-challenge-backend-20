import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
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
      throw new InternalServerErrorException('제품 올리기 실패');
    }
    return product;
  }

  // 제품 판매 승인
  async approveProductSale(userId: number, productId: number): Promise<void> {
    const product = await this.findProductDetail(productId);
    if (product.userId !== userId) {
      throw new UnauthorizedException('제품 판매 승인 권한이 없습니다.');
    }
    await this.productRepository.approveProductSale(productId);
  }

  // 거래내역
  async findTransactionHistory(
    userId: number,
    sellerId: number,
  ): Promise<Product[]> {
    return await this.productRepository.findTransactionHistory(
      userId,
      sellerId,
    );
  }
}
