import {
  ConflictException,
  Inject,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import { ProductServiceInterface } from './interfaces/product.service.interface';
import { ProductRepositoryInterface } from './interfaces/product.repository.interface';
import { CreateProductDto } from './dto/create-product.dto';
import { ProductStatus } from '@prisma/client';

@Injectable()
export class ProductsService implements ProductServiceInterface {
  constructor(
    @Inject('PRODUCT_REPOSITORY_INTERFACE')
    private readonly productRepository: ProductRepositoryInterface,
  ) {}

  async checkSoldOut(productId: number) {
    const product = await this.productRepository.findById(productId);

    if (!product) {
      throw new NotFoundException('상품이 존재하지 않습니다.');
    }

    if (product.status == ProductStatus.RESERVED) {
      throw new ConflictException('현재 예약중인 상품입니다.');
    }

    if (product.status == ProductStatus.SOLD) {
      throw new ConflictException('이미 판매된 상품입니다.');
    }

    return product;
  }

  async findAll() {
    return await this.productRepository.findAll();
  }

  async create(
    createProductDto: CreateProductDto,
    userId: number,
  ): Promise<void> {
    return await this.productRepository.create(createProductDto, userId);
  }
}
