import { Injectable } from '@nestjs/common';
import { TransactionStatus } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { TransactionRepositoryInterface } from 'src/transactions/interfaces/transaction.repository.interface';

@Injectable()
export class TransactionRepository implements TransactionRepositoryInterface {
  constructor(private readonly prisma: PrismaService) {}
  async confirm(transactionId: number): Promise<void> {
    await this.prisma.transaction.update({
      where: {
        id: transactionId,
      },
      data: {
        status: TransactionStatus.APPROVED,
      },
    });
  }

  async findByTransactionIdAndBuyerId(
    transactionId: number,
    buyerId: number,
  ): Promise<any> {
    return await this.prisma.transaction.findUnique({
      where: {
        id: transactionId,
        buyerId,
      },
    });
  }

  async findByBuyerIdAndSellerId(
    productId: number,
    buyerId: number,
    sellerId: number,
  ): Promise<any> {
    return await this.prisma.transaction.findFirst({
      where: {
        productId,
        buyerId,
        sellerId,
      },
    });
  }

  async create(createTransactionInfo: any): Promise<any> {
    return await this.prisma.transaction.create({
      data: createTransactionInfo,
    });
  }
}
