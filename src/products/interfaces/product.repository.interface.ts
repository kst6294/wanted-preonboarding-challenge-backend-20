import { Prisma, Product, ProductStatus, Transaction } from '@prisma/client';
import { CreateProductDto } from '../dto/create-product.dto';
export type ProductIncludeTransaction = Product & {
  transactions: Transaction[];
};
export interface ProductRepositoryInterface {
  findById(productId: number): Promise<ProductIncludeTransaction | null>;
  findAll(): Promise<Product[]>;
  create(
    rest: Omit<CreateProductDto, 'quantity'>,
    userId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<Product>;
  updateStatus(productId: number, status: ProductStatus): Promise<void>;
}
