import { Product } from '@prisma/client';
import { CreateProductDto } from '../dto/create-product.dto';

export interface ProductServiceInterface {
  findAll(): Promise<Product[]>;
  create(createProductDto: CreateProductDto, userId: number): Promise<void>;
}
