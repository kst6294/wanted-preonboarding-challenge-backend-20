import { Product } from '@prisma/client';
import { CreateProductDto } from '../dto/create-product.dto';

export interface ProductRepositoryInterface {
  findById(productId: number): Promise<Product | null>;
  findAll(): Promise<Product[]>;
  create(createProductDto: CreateProductDto, userId: number): Promise<void>;
}
