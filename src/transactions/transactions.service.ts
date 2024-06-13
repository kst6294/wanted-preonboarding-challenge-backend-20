import {
  ConflictException,
  Inject,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import {
  TransactionIncludeProduct,
  TransactionRepositoryInterface,
} from './interfaces/transaction.repository.interface';
import { TransactionServiceInterface } from './interfaces/transaction.service.interface';
import { TransactionStatus } from '@prisma/client';
import { GetTransactionsDTO } from './dto/get-transactions.dto';

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

    if (!transaction) {
      throw new NotFoundException('현재 거래내역이 존재하지 않습니다.');
    }
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
    const transaction =
      await this.transactionRepository.findByBuyerIdAndSellerId(
        productId,
        buyerId,
        sellerId,
      );

    if (!transaction) {
      return true;
    }

    if (transaction && transaction.status === TransactionStatus.PENDING) {
      throw new ConflictException('이미 구매 대기중인 상품입니다.');
    }

    if (transaction && transaction.status === TransactionStatus.APPROVED) {
      throw new ConflictException('이미 구매 완료된 상품입니다.');
    }

    return true;
  }

  async create(createTransacitonInfo: any) {
    return await this.transactionRepository.create(createTransacitonInfo);
  }

  async findBuyList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    return await this.transactionRepository.findBuyList(query, userId);
  }

  async findReservationList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    return await this.transactionRepository.findReservationList(query, userId);
  }
}
