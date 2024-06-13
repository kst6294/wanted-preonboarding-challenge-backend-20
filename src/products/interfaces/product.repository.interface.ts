import { Prisma, Product } from '@prisma/client';
import { CreateProductDto } from '../dto/create-product.dto';

export interface ProductRepositoryInterface {
  findById(productId: number): Promise<Product | null>;
  findAll(): Promise<Product[]>;
  create(
    rest: Omit<CreateProductDto, 'quantity'>,
    userId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<Product>;
}
