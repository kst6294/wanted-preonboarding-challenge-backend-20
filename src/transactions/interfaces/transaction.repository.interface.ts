import { Transaction } from '@prisma/client';

export interface TransactionRepositoryInterface {
  create(createTransactionInfo: any): Promise<any>;
  findByBuyerIdAndSellerId(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<Transaction | null>;
}
