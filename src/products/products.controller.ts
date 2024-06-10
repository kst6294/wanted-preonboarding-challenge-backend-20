import { Body, Controller, Get, Inject, Post, Req, UseGuards } from '@nestjs/common';
import { ProductServiceInterface } from './interfaces/product.service.interface';
import { CreateProductDto } from './dto/create-product.dto';
import { JwtAuthGuard } from 'src/auth/jwt-auth.guard';

@Controller('products')
export class ProductsController {
  constructor(
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productsService: ProductServiceInterface,
  ) {}

  @Get()
  async findAll() {
    return await this.productsService.findAll();
  }

  @Post()
  @UseGuards(JwtAuthGuard)
  async create(
    @Req() req: any,
    @Body() createProductDto: CreateProductDto,
  ): Promise<void> {
    return this.productsService.create(createProductDto, 2);
  }
}
