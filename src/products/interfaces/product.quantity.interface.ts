import { Prisma, ProductQuantity } from '@prisma/client';

export interface ProductQuantityRepositoryInterface {
  findById(productId: number): Promise<ProductQuantity | null>;
  makeQuantity(
    quantity: number,
    productId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void>;
}
