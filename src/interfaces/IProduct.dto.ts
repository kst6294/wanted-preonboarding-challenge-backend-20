export enum ProductStatus {
  판매중 = "판매중",
  예약중 = "예약중",
  완료 = "완료",
}

export interface IProductItem {
  productID: number;
  userID?: number;
}

export interface IProductList {
  isBought?: boolean;
  isReserved?: boolean;
  userID?: number;
}
