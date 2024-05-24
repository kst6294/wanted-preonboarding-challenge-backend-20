export enum TransactionStatus {
  구매요청 = "구매요청",
  판매승인 = "판매승인",
  구매확정 = "구매확정",
}

export default interface ITransaction {
  productID: number;
  userID: number;
  status: TransactionStatus;
}
