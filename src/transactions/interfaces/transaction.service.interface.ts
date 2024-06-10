export interface TransactionServiceInterface {
  confirm(transactionId: number): Promise<void>;
  checkIsPending(transactionId: number, buyerId: number): Promise<any>;
  checkAlreadyBought(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<boolean>;
  create(createTransactionInfo: any): Promise<any>;
}
