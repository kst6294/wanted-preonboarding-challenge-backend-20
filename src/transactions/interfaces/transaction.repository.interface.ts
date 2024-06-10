import { Transaction } from '@prisma/client';

export interface TransactionRepositoryInterface {
  confirm(transactionId: number): Promise<void>;
  create(createTransactionInfo: any): Promise<any>;
  findByTransactionIdAndBuyerId(
    transactionId: number,
    buyerId: number,
  ): Promise<Transaction | null>;
  findByBuyerIdAndSellerId(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<Transaction | null>;
}
