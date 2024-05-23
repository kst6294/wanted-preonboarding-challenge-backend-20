export enum Status {
  진행중 = "진행중",
  완료 = "완료",
}

export default interface ITransaction {
  productID: number;
  userID: number;
  status: Status;
}
