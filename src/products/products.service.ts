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
import { ProductQuantityRepositoryInterface } from './interfaces/product.quantity.interface';
import { PrismaService } from 'prisma/prisma.service';

@Injectable()
export class ProductsService implements ProductServiceInterface {
  constructor(
    private readonly prisma: PrismaService,
    @Inject('PRODUCT_REPOSITORY_INTERFACE')
    private readonly productRepository: ProductRepositoryInterface,
    @Inject('PRODUCT_QUANTITY_REPOSITORY_INTERFACE')
    private readonly productQuantityRepository: ProductQuantityRepositoryInterface,
  ) {}

  async findOne(productId: number) {
    const product = await this.productRepository.findById(productId);

    if (!product) {
      throw new NotFoundException('상품이 존재하지 않습니다.');
    }

    return product;
  }

  async checkSoldOut(productId: number) {
    const product = await this.productRepository.findById(productId);

    if (!product) {
      throw new NotFoundException('상품이 존재하지 않습니다.');
    }

    const productQuantity = await this.productQuantityRepository.findById(
      product.id,
    );

    if (productQuantity.quantity === 0) {
      throw new ConflictException('현재 품절된 상품입니다.');
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
    const { quantity, ...rest } = createProductDto;

    await this.prisma.$transaction(async transaction => {
      const product = await this.productRepository.create(
        rest,
        userId,
        transaction,
      );
      await this.productQuantityRepository.makeQuantity(
        createProductDto.quantity,
        product.id,
        transaction,
      );
    });
  }
}
