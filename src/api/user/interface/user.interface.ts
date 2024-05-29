import { IProduct } from '../../product/interface/product.interface';
import { ITransaction } from '../../transaction/interface/transaction.interface';

export interface IUser {
  idx: number;
  name: string;
  email: string;
  password: string;
  createdAt: Date;
  updatedAt: Date;
}

export namespace IUser {
  export interface ISignupRequest {
    email: string;
    password: string;
    name: string;
  }

  export interface ISignupResponse {
    idx: number;
  }

  export interface ILocalLoginRequest {
    email: string;
    password: string;
  }

  export interface IPurchasedProductResponse {
    purchagedProduct: ITransaction.ITransactionHistoryResponse[];
  }

  export interface IReservedProductResponse {
    reservedProduct: IProduct.ISummaryResponse[];
  }

  export interface ISoldProductResponse {
    soldProduct: IProduct.ISummaryResponse[];
  }
}
