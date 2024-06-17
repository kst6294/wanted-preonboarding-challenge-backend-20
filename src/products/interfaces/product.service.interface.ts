import { Product, ProductStatus } from '@prisma/client';
import { CreateProductDto } from '../dto/create-product.dto';
import { ProductIncludeTransaction } from './product.repository.interface';

export interface ProductServiceInterface {
  checkSoldOut(productId: number): Promise<Product | null>;
  findOne(productId: number): Promise<ProductIncludeTransaction | null>;
  findAll(): Promise<Product[]>;
  create(createProductDto: CreateProductDto, userId: number): Promise<void>;
  updateStatus(productId: number, status: ProductStatus): Promise<void>;
}
