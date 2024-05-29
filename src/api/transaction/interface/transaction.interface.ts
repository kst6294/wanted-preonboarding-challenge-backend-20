import { TransactionStatus } from '@prisma/client';
import { IUser } from '../../user/interface/user.interface';
import { IProduct } from '../../product/interface/product.interface';

export interface ITransaction {
  idx: number;
  userIdx: IUser['idx'];
  productIdx: IProduct['idx'];
  price: number;
  status: TransactionStatus;
  createdAt: Date;
  updatedAt: Date;
}

export namespace ITransaction {
  export interface ITransactionHistoryResponse
    extends Pick<ITransaction, 'idx' | 'userIdx' | 'productIdx' | 'price'>,
      Pick<IProduct, 'name' | 'createdAt'> {
    transactionStatus: TransactionStatus;
  }
}

// export type TransactionHistoryType = Pick<
//   ITransaction,
//   'idx' | 'userIdx' | 'productIdx' | 'price' | 'status'
// > &
//   Pick<IProduct, 'name' | 'createdAt'>;

export type ProductDetailOrProductDetailWithTransaction =
  | IProduct.IProductDetailResopnse
  | IProduct.IProductDetailWithTransactionResponse;
