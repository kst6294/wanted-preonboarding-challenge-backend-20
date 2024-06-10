import { ConflictException, Inject, Injectable } from '@nestjs/common';
import { TransactionRepositoryInterface } from './interfaces/transaction.repository.interface';
import { TransactionServiceInterface } from './interfaces/transaction.service.interface';

@Injectable()
export class TransactionsService implements TransactionServiceInterface {
  constructor(
    @Inject('TRANSACTION_REPOSITORY_INTERFACE')
    private readonly transactionRepository: TransactionRepositoryInterface,
  ) {}

  async checkAlreadyBought(
    productId: number,
    buyerId: number,
    sellerId: number,
  ) {
    const product = await this.transactionRepository.findByBuyerIdAndSellerId(
      productId,
      buyerId,
      sellerId,
    );

    if (product) {
      throw new ConflictException('이미 구매한 상품입니다.');
    }

    return true;
  }

  async create(createTransacitonInfo: any) {
    return await this.transactionRepository.create(createTransacitonInfo);
  }
}
