import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Product } from './entity/product.entity';
import { Repository } from 'typeorm';
import { ProductDto } from './dto/product.dto';

@Injectable()
export class ProductRepository {
  constructor(
    @InjectRepository(Product) private productRepository: Repository<Product>,
  ) {}

  // 제품 목록 조회
  async findAllProducts(): Promise<Product[]> {
    return await this.productRepository
      .createQueryBuilder('product')
      .select([
        'product.productId AS productId',
        'product.productName AS productName',
        'product.productPrice AS productPrice',
        'product.productStatus AS productStatus',
        'product.userId AS userId',
        'user.name AS sellerName',
      ])
      .leftJoin('user', 'user', 'user.userId = product.userId')
      .groupBy('product.productId')
      .orderBy('product.productCreatedAt', 'DESC')
      .getRawMany();
  }

  // 제품 상세 조회
  async findProductDetail(productId: number): Promise<Product> {
    return await this.productRepository
      .createQueryBuilder('product')
      .select([
        'product.productId AS productId',
        'product.productName AS productName',
        'product.productPrice AS productPrice',
        'product.productStatus AS productStatus',
        'product.userId AS userId',
        'user.name AS sellerName',
      ])
      .leftJoin('user', 'user', 'user.userId = product.userId')
      .where('product.productId = :productId', { productId })
      .getRawOne();
  }

  // 제품 올리기
  async createProduct(
    userId: number,
    productDto: ProductDto,
  ): Promise<Product> {
    const newProduct = new Product();
    const { productName, productPrice } = productDto;
    newProduct.userId = userId;
    newProduct.productName = productName;
    newProduct.productPrice = productPrice;
    return await this.productRepository.save(newProduct);
  }
}
