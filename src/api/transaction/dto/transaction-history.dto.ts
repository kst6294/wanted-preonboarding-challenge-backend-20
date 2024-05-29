import { IProduct } from '../../product/interface/product.interface';
import { ITransaction } from '../interface/transaction.interface';
import { $Enums } from '@prisma/client';

export class TransactionHistoryReseponseDto
  implements ITransaction.ITransactionHistoryResponse
{
  /**
   * 거래 인덱스
   * @example 1
   */
  idx: ITransaction['idx'];

  /**
   * 사용자 인덱스
   * @example 1
   */
  userIdx: ITransaction['userIdx'];

  /**
   * 제품 인덱스
   * @example 1
   */
  productIdx: ITransaction['productIdx'];

  /**
   * 가격
   * @example 1000
   */
  price: ITransaction['price'];

  /**
   * 제품 명
   * @example 'name'
   */
  name: IProduct['name'];

  /**
   * 제품 등록 일
   * @example '2021-09-01T00:00:00.000Z'
   */
  createdAt: IProduct['createdAt'];

  /**
   * 거래 상태
   * @example 'RESERVED | SOLD | CONFIRM'
   */
  transactionStatus: $Enums.TransactionStatus;
}
