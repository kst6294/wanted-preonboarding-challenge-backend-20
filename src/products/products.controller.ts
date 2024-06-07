import { Controller, Get, Inject } from '@nestjs/common';
import { ProductServiceInterface } from './interfaces/product.service.interface';

@Controller('products')
export class ProductsController {
  constructor(
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productsService: ProductServiceInterface) {}

  @Get()
  findAll() {
    return this.productsService.findAll();
  }
}