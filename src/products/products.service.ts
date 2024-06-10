import { Inject, Injectable } from '@nestjs/common';
import { ProductServiceInterface } from './interfaces/product.service.interface';
import { ProductRepositoryInterface } from './interfaces/product.repository.interface';
import { CreateProductDto } from './dto/create-product.dto';

@Injectable()
export class ProductsService implements ProductServiceInterface {
  constructor(
    @Inject('PRODUCT_REPOSITORY_INTERFACE')
    private readonly productRepository: ProductRepositoryInterface,
  ) {}

  async findAll() {
    return await this.productRepository.findAll();
  }

  async create(createProductDto: CreateProductDto,userId:number): Promise<void> {
    return await this.productRepository.create(createProductDto,userId);
  }
}
