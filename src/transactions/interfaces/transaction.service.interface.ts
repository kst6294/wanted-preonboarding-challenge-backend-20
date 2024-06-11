import { GetTransactionsDTO } from '../dto/get-transactions.dto';

export interface TransactionServiceInterface {
  confirm(transactionId: number): Promise<void>;
  checkIsPending(transactionId: number, buyerId: number): Promise<any>;
  checkAlreadyBought(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<boolean>;
  create(createTransactionInfo: any): Promise<any>;
  findBuyList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: any[]; count: number }>;
}
