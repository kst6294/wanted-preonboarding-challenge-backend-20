import { Prisma, ProductQuantity } from '@prisma/client';

export interface ProductQuantityServiceInterface {
  findById(productId: number): Promise<ProductQuantity | null>;
  makeQuantity(
    quantity: number,
    productId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void>;
  updateQuantity(
    productId: number,
    quantity: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void>;
}
