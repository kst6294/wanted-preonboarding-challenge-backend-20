import { Injectable } from '@nestjs/common';
import { Prisma, Product } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { CreateProductDto } from 'src/products/dto/create-product.dto';
import { ProductRepositoryInterface } from 'src/products/interfaces/product.repository.interface';

@Injectable()
export class ProductRepository implements ProductRepositoryInterface {
  constructor(private readonly prisma: PrismaService) {}

  async findById(productId: number): Promise<Product | null> {
    return this.prisma.product.findUnique({
      where: {
        id: productId,
      },
    });
  }

  async findAll(): Promise<Product[]> {
    return this.prisma.product.findMany();
  }

  async create(
    createProductDto: CreateProductDto,
    userId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<Product> {
    return await transaction.product.create({
      data: {
        ...createProductDto,
        ownerId: userId,
      },
    });
  }
}
