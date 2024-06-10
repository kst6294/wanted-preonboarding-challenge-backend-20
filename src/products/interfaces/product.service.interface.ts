import { Product } from '@prisma/client';
import { CreateProductDto } from '../dto/create-product.dto';

export interface ProductServiceInterface {
  checkSoldOut(productId: number): Promise<Product | null>;
  findOne(productId: number): Promise<Product | null>;
  findAll(): Promise<Product[]>;
  create(createProductDto: CreateProductDto, userId: number): Promise<void>;
}
