import { Injectable } from '@nestjs/common';
import { PrismaService } from 'prisma/prisma.service';
import { TransactionRepositoryInterface } from 'src/transactions/interfaces/transaction.repository.interface';

@Injectable()
export class TransactionRepository implements TransactionRepositoryInterface {
  constructor(private readonly prisma: PrismaService) {}

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
