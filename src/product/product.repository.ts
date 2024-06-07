import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Product } from './entity/product.entity';
import { QueryRunner, Repository, UpdateResult } from 'typeorm';
import { ProductDto } from './dto/product.dto';
import { ProductStatus } from './enum/productStatus.enum';

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
      .leftJoin('order', 'order', 'order.userId')
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

  // 제품 예약
  async reserveProduct(
    queryRunner: QueryRunner,
    productId: number,
  ): Promise<void> {
    await queryRunner.manager.update(
      Product,
      { productId },
      { productStatus: ProductStatus.RESERVED },
    );
  }

  // 제품 판매 승인
  async approveProductSale(productId: number): Promise<void> {
    await this.productRepository.update(
      { productId },
      { productStatus: ProductStatus.COMPLETED },
    );
  }

  // 구매한 제품 목록 조회
  async findPurchasedProducts(userId: number): Promise<Product[]> {
    return await this.productRepository
      .createQueryBuilder('product')
      .select([
        'product.productId as productId',
        'product.productName as productName',
        'product.productPrice as productPrice',
        'product.productStatus as productStatus',
        'product.userId as sellerId',
        'user.name as sellerName',
      ])
      .leftJoin('order', 'order', 'product.productId = order.productId')
      .leftJoin('user', 'user', 'user.userId = product.userId')
      .where('order.userId = :userId', { userId })
      .andWhere('product.productStatus = :productStatus', {
        productStatus: ProductStatus.COMPLETED,
      })
      .groupBy('order.productId')
      .getRawMany();
  }

  // 예약중인 제품 목록 조회(판매자)
  async findReservedProductsBySeller(
    userId: number,
  ): Promise<Record<string, any>> {
    return await this.productRepository
      .createQueryBuilder('product')
      .select([
        'product.productId as productId',
        'product.productName as productName',
        'product.productPrice as productPrice',
        'product.productStatus as productStatus',
        'order.userId as buyerId',
        'user.name as buyerName',
      ])
      .leftJoin('order', 'order', 'order.productId = product.productId')
      .leftJoin('user', 'user', 'user.userId = order.userId')
      .where('product.userId = :userId', { userId })
      .andWhere('product.productStatus = :productStatus', {
        productStatus: ProductStatus.RESERVED,
      })
      .groupBy('product.productId')
      .getRawMany();
  }

  // 예약중인 제품 목록 조회(구매자)
  async findReservedProductsByBuyer(
    userId: number,
  ): Promise<Record<string, any>> {
    return await this.productRepository
      .createQueryBuilder('product')
      .select([
        'product.productId as productId',
        'product.productName as productName',
        'product.productPrice as productPrice',
        'product.productStatus as productStatus',
        'product.userId as sellerId',
        'user.name as sellerName',
      ])
      .leftJoin('order', 'order', 'product.productId = order.productId')
      .leftJoin('user', 'user', 'user.userId = product.userId')
      .where('order.userId = :userId', { userId })
      .andWhere('product.productStatus = :productStatus', {
        productStatus: ProductStatus.RESERVED,
      })
      .groupBy('order.productId')
      .getRawMany();
  }

  // 거래내역
  async findTransactionHistory(
    userId: number,
    sellerId: number,
  ): Promise<Product[]> {
    // 판매자일 경우
    if (userId === sellerId) {
      return await this.productRepository
        .createQueryBuilder('product')
        .select([
          'product.productId as productId',
          'product.productName as productName',
          'product.productPrice as productPrice',
          'product.productStatus as productStatus',
          'order.userId as buyerId',
          'user.name as buyerName',
        ])
        .leftJoin('order', 'order', 'product.productId = order.productId')
        .leftJoin('user', 'user', 'user.userId = order.userId')
        .where('order.orderId is not null')
        .groupBy('order.orderId')
        .getRawMany();
    }
    // 구매자일 경우
    if (userId !== sellerId) {
      return await this.productRepository
        .createQueryBuilder('product')
        .select([
          'product.productId as productId',
          'product.productName as productName',
          'product.productPrice as productPrice',
          'product.productStatus as productStatus',
          'product.userId as sellerId',
          'user.name as sellerName',
        ])
        .leftJoin('order', 'order', 'product.productId = order.productId')
        .leftJoin('user', 'user', 'user.userId = product.userId')
        .where('order.userId = :userId', { userId })
        .andWhere('product.userId = :sellerId', { sellerId })
        .groupBy('order.orderId')
        .getRawMany();
    }
  }
}
