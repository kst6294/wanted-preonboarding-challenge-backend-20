import { Injectable } from '@nestjs/common';
import { Prisma, ProductQuantity } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { ProductQuantityRepositoryInterface } from 'src/products/interfaces/product.quantity.repository.interface';

@Injectable()
export class ProductQuantityRepository
  implements ProductQuantityRepositoryInterface
{
  constructor(private readonly prisma: PrismaService) {}
  async findById(productId: number): Promise<ProductQuantity | null> {
    console.log('productId', productId);
    return await this.prisma.productQuantity.findFirst({
      where: {
        productId,
      },
    });
  }

  async makeQuantity(
    quantity: number,
    productId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void> {
    await transaction.productQuantity.create({
      data: {
        quantity,
        productId,
      },
    });
  }
  async updateQuantity(
    productId: number,
    quantity: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void> {
    await transaction.productQuantity.update({
      where: {
        productId,
      },
      data: {
        quantity,
      },
    });
  }
}
