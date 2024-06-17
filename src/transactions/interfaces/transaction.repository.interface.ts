import { Prisma, Transaction } from '@prisma/client';
import { GetTransactionsDTO } from '../dto/get-transactions.dto';

export type TransactionIncludeProduct = Prisma.TransactionGetPayload<{
  include: {
    product: true;
  };
}>;

export interface TransactionRepositoryInterface {
  confirm(transactionId: number): Promise<void>;
  create(
    createTransactionInfo: any,
    price: number,
    transaction: Prisma.TransactionClient,
  ): Promise<any>;
  findByTransactionIdAndBuyerId(
    transactionId: number,
    buyerId: number,
  ): Promise<Transaction | null>;
  findByBuyerIdAndSellerId(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<Transaction | null>;
  findBuyList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }>;
  findReservationList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }>;
  findById(transactionId: number): Promise<Transaction | null>;
  buyConfirm(transactionId: number): Promise<void>;
  countByProductId(productId: number): Promise<number>;
}
