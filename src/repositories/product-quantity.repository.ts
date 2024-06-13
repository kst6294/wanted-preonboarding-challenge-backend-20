import { Prisma, ProductQuantity } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { ProductQuantityRepositoryInterface } from 'src/products/interfaces/product.quantity.interface';

export class ProductQuantityRepository
  implements ProductQuantityRepositoryInterface
{
  constructor(private readonly prisma: PrismaService) {}
  async findById(productId: number): Promise<ProductQuantity | null> {
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
}
