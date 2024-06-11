import { Injectable } from '@nestjs/common';
import { TransactionStatus } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { GetTransactionsDTO } from 'src/transactions/dto/get-transactions.dto';
import {
  TransactionIncludeProduct,
  TransactionRepositoryInterface,
} from 'src/transactions/interfaces/transaction.repository.interface';

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

  async findBuyList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    const productList = await this.prisma.transaction.findMany({
      where: {
        ...query,
        buyerId: userId,
      },
      include: {
        product: true,
      },
    });

    const count = await this.prisma.transaction.count({
      where: {
        buyerId: userId,
      },
    });

    return { productList, count };
  }
}
