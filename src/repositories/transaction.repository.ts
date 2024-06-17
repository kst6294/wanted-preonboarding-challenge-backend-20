import { Injectable } from '@nestjs/common';
import {
  Prisma,
  ProductStatus,
  Transaction,
  TransactionStatus,
} from '@prisma/client';
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

  async create(
    createTransactionInfo: any,
    price: number,
    transaction: Prisma.TransactionClient,
  ): Promise<any> {
    return await transaction.transaction.create({
      data: { ...createTransactionInfo, price },
    });
  }

  async findBuyList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    const { limit, page } = query;
    console.log('query', query);

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
  /**
   * 판매 승인 상태인 거래 조회
   * @param transactionId
   * @returns
   */
  async findById(transactionId: number): Promise<Transaction | null> {
    return await this.prisma.transaction.findUnique({
      where: {
        id: transactionId,
        // status: TransactionStatus.APPROVED,
      },
    });
  }
  /**
   * 구매 확정 상태로 변경
   * @param transactionId
   */
  async buyConfirm(transactionId: number): Promise<void> {
    await this.prisma.transaction.update({
      where: {
        id: transactionId,
      },
      data: {
        status: TransactionStatus.PURCHASE_CONFIRMED,
      },
    });
  }
  /**
   *
   * @param productId
   * @description 특정 상품의 구매확정 개수 확인
   */
  async countByProductId(productId: number): Promise<number> {
    return await this.prisma.transaction.count({
      where: {
        productId,
        status: {
          notIn: [TransactionStatus.PURCHASE_CONFIRMED],
        },
      },
    });
  }
}
