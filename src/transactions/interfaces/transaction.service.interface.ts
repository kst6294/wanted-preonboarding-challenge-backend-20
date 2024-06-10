export interface TransactionServiceInterface {
  checkAlreadyBought(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<boolean>;
  create(createTransactionInfo: any): Promise<any>;
}
