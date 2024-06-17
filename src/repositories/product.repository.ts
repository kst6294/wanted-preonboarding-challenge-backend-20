import { Injectable } from '@nestjs/common';
import { $Enums, Prisma, Product } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { CreateProductDto } from 'src/products/dto/create-product.dto';
import {
  ProductIncludeTransaction,
  ProductRepositoryInterface,
} from 'src/products/interfaces/product.repository.interface';

@Injectable()
export class ProductRepository implements ProductRepositoryInterface {
  constructor(private readonly prisma: PrismaService) {}

  async findById(productId: number): Promise<ProductIncludeTransaction | null> {
    return this.prisma.product.findUnique({
      where: {
        id: productId,
      },
      include: {
        transactions: true,
      },
    });
  }

  async findAll(): Promise<Product[]> {
    return this.prisma.product.findMany();
  }

  async create(
    createProductDto: Omit<CreateProductDto, 'quantity'>,
    userId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<Product> {
    console.log('createProductDto', createProductDto);
    return await transaction.product.create({
      data: {
        ...createProductDto,
        ownerId: userId,
      },
    });
  }

  async updateStatus(
    productId: number,
    status: $Enums.ProductStatus,
  ): Promise<void> {
    await this.prisma.product.update({
      where: {
        id: productId,
      },
      data: {
        status,
      },
    });
  }
}
