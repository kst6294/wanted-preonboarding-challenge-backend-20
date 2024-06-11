import { GetTransactionsDTO } from '../dto/get-transactions.dto';
import { TransactionIncludeProduct } from './transaction.repository.interface';

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
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }>;
  findReservationList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }>;
}
