import {
  Body,
  Controller,
  Get,
  HttpStatus,
  Param,
  Post,
  Put,
  Res,
} from '@nestjs/common';
import { ProductService } from './product.service';
import { Response } from 'express';
import { ProductDto } from './dto/product.dto';

@Controller('product')
export class ProductController {
  constructor(private readonly productService: ProductService) {}

  // 제품 목록 조회
  @Get()
  async getAllProducts(@Res() res: Response) {
    const allProducts = await this.productService.findAllProducts();
    return res.status(HttpStatus.OK).json(allProducts);
  }

  // 제품 상세 조회
  @Get(':productId')
  async getProduct(
    @Param('productId') productId: number,
    @Res() res: Response,
  ) {
    if (res.locals.user) {
      const { userId } = res.locals.user;

      const product = await this.productService.findProductDetail(productId);
      let sellerId: number = product.userId;
      const transactionHistory =
        await this.productService.findTransactionHistory(userId, sellerId);

      const productDetail = { product, transactionHistory };
      return res.status(HttpStatus.OK).json(productDetail);
    }

    const productDetail =
      await this.productService.findProductDetail(productId);

    return res.status(HttpStatus.OK).json(productDetail);
  }

  // 제품 올리기
  @Post()
  async createProduct(@Body() productDto: ProductDto, @Res() res: Response) {
    const { userId } = res.locals.user;
    await this.productService.createProduct(userId, productDto);
    return res.status(HttpStatus.OK).json({ message: '제품 등록 완료' });
  }

  // 제품 판매 승인
  @Put(':productId')
  async approveProductSale(
    @Param('productId') productId: number,
    @Res() res: Response,
  ) {
    const { userId } = res.locals.user;
    await this.productService.approveProductSale(userId, productId);
    return res.status(HttpStatus.OK).json({ message: '제품 판매 승인 완료' });
  }
}
