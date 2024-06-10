import { ConflictException, Inject, Injectable } from '@nestjs/common';
import { TransactionRepositoryInterface } from './interfaces/transaction.repository.interface';
import { TransactionServiceInterface } from './interfaces/transaction.service.interface';
import { TransactionStatus } from '@prisma/client';

@Injectable()
export class TransactionsService implements TransactionServiceInterface {
  constructor(
    @Inject('TRANSACTION_REPOSITORY_INTERFACE')
    private readonly transactionRepository: TransactionRepositoryInterface,
  ) {}

  async confirm(transactionId: number): Promise<void> {
    await this.transactionRepository.confirm(transactionId);
  }

  async checkIsPending(transactionId: number, buyerId: number) {
    const transaction =
      await this.transactionRepository.findByTransactionIdAndBuyerId(
        transactionId,
        buyerId,
      );

    if (transaction.status === TransactionStatus.APPROVED) {
      throw new ConflictException('이미 판매승인된 상품입니다.');
    }

    if (transaction.status === TransactionStatus.REJECTED) {
      throw new ConflictException('이미 판매거절된 상품입니다.');
    }

    return transaction;
  }

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
