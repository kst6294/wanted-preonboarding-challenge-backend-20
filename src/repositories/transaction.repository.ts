import { Injectable } from '@nestjs/common';
import { ProductStatus, Transaction, TransactionStatus } from '@prisma/client';
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
  ): Promise<Transaction | null> {
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
  ): Promise<Transaction | null> {
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
    const { limit, page } = query;

    const productList = await this.prisma.transaction.findMany({
      where: {
        buyerId: userId,
        product: {
          is: {
            status: ProductStatus.SOLD,
          },
        },
      },
      include: {
        product: true,
      },
      skip: (page - 1) * limit,
      take: limit,
    });

    const count = await this.prisma.transaction.count({
      where: {
        buyerId: userId,
      },
    });

    return { productList, count };
  }

  async findReservationList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    const { limit, page } = query;

    const productList = await this.prisma.transaction.findMany({
      where: {
        OR: [{ sellerId: userId }, { buyerId: userId }],
        product: {
          is: {
            status: ProductStatus.RESERVED,
          },
        },
      },
      include: {
        product: true,
      },
      skip: (page - 1) * limit,
      take: Number(limit),
    });

    const count = await this.prisma.transaction.count({
      where: {
        OR: [{ sellerId: userId }, { buyerId: userId }],
        product: {
          is: {
            status: ProductStatus.RESERVED,
          },
        },
      },
    });

    return { productList, count };
  }
}
