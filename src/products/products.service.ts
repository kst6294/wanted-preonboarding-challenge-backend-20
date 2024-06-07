import { Inject, Injectable } from "@nestjs/common";
import { PrismaService } from "prisma/prisma.service";
import { ProductServiceInterface } from "./interfaces/product.service.interface";
import { ProductRepositoryInterface } from "./interfaces/product.repository.interface";

@Injectable()
export class ProductsService implements ProductServiceInterface {
  constructor(
    @Inject('PRODUCT_REPOSITORY_INTERFACE')
    private readonly productRepository : ProductRepositoryInterface) {}

  findAll() {
    return this.productRepository.findAll();
  }

}