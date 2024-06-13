import {
  Body,
  Controller,
  Get,
  Inject,
  Param,
  ParseIntPipe,
  Post,
  Req,
  UseGuards,
} from '@nestjs/common';
import { ProductServiceInterface } from './interfaces/product.service.interface';
import { CreateProductDto } from './dto/create-product.dto';
import { JwtAuthGuard } from 'src/auth/jwt-auth.guard';
import { Product } from '@prisma/client';

@Controller('products')
export class ProductsController {
  constructor(
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productsService: ProductServiceInterface,
  ) {}
  /**
   *
   * @description 상품 전체 목록 조회
   */
  @Get()
  async findAll() {
    return await this.productsService.findAll();
  }
  /**
   * @description 상품 상세 조회
   */
  @Get('/:productId')
  async findOne(
    @Param('productId', ParseIntPipe) productId: number,
  ): Promise<Product | null> {
    return await this.productsService.findOne(productId);
  }

  /**
   *
   * @param req
   * @body createProductDto
   * @description 상품 등록 w.수량추가
   */
  @Post()
  @UseGuards(JwtAuthGuard)
  async create(
    @Req() req: any,
    @Body() createProductDto: CreateProductDto,
  ): Promise<void> {
    return this.productsService.create(createProductDto, req.user.userId);
  }
}
