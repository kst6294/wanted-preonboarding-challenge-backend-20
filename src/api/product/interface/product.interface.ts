import { ProductStatus } from '@prisma/client';
import { IUser } from '../../user/interface/user.interface';
import { ITransaction } from '../../transaction/interface/transaction.interface';

export interface IProduct {
  idx: number;
  userIdx: IUser['idx'];
  name: string;
  price: number;
  status: ProductStatus;
  createdAt: Date;
  updatedAt: Date;
}

export namespace IProduct {
  export interface IRegisterRequest extends Pick<IProduct, 'name' | 'price'> {}

  export interface IRegisterResponse extends Pick<IProduct, 'idx'> {}

  export interface ISummaryResponse
    extends Pick<IProduct, 'idx' | 'name' | 'price' | 'status' | 'createdAt'> {}

  export interface IPurchaseRequest {
    productIdx: IProduct['idx'];
  }

  export interface IPurchaseResponse {
    transactionIdx: ITransaction['idx'];
  }

  export interface IProductDetailResopnse extends Omit<IProduct, 'userIdx'> {
    sellerIdx: IUser['idx'];
  }

  export interface IProductDetailWithTransactionResponse
    extends Omit<IProduct, 'userIdx'> {
    sellerIdx: IUser['idx'];
    transactionIdx: ITransaction['idx'];
    buyerIdx: IUser['idx'];

    transactionHistory: ITransaction.ITransactionHistoryResponse;
  }

  export interface IProductApproveRequest extends Pick<IProduct, 'idx'> {}
}
