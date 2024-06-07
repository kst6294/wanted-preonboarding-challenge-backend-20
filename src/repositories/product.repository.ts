import { Injectable } from "@nestjs/common";
import { Product } from "@prisma/client";
import { PrismaService } from "prisma/prisma.service";
import { ProductRepositoryInterface } from "src/products/interfaces/product.repository.interface";

@Injectable()
export class ProductRepository implements ProductRepositoryInterface {
  constructor(private prisma: PrismaService) {}

  async findAll(): Promise<Product[]> {
    return this.prisma.product.findMany();
  }
}