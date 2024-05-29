import { IUser } from '../interface/user.interface';
import { ITransaction } from '../../transaction/interface/transaction.interface';
import { TransactionHistoryReseponseDto } from '../../transaction/dto/transaction-history.dto';

export class MyPurchasedProductResponseDto
  implements IUser.IPurchasedProductResponse
{
  purchagedProduct: TransactionHistoryReseponseDto[];

  static of(
    purchagedProduct: ITransaction.ITransactionHistoryResponse[],
  ): MyPurchasedProductResponseDto {
    return {
      purchagedProduct,
    };
  }
}
