import { Body, Controller, DefaultValuePipe, Get, Logger, LoggerService, NotFoundException, Param, ParseIntPipe, Post, Query, Req, UseGuards } from '@nestjs/common';
import { Public } from 'src/baseinfra/decorator/public.api.decorator';
import { PageResponseDto } from 'src/model/dto/common.pagination.response.dto';
import ProductInputDto from 'src/model/dto/product.create.input';

import { Product } from 'src/model/product.entity';
import { ProductService } from 'src/providers/product.service';
import { HttpUtils } from 'src/utils/util.http';

@Controller("product")
export class ProductController {

  private readonly logger = new Logger(ProductController.name);

  constructor(
    private readonly productSvc: ProductService
  ) { }

  @Public()
  @Get("list")
  async productPage(
    @Query("page", new DefaultValuePipe(0), ParseIntPipe) page: number,
    @Query("size", new DefaultValuePipe(20), ParseIntPipe) size: number
  ): Promise<PageResponseDto<Product>> {
    console.log("page", page, size);
    return this.productSvc.productPage(page, size);
  }


  @Public()
  @Get("/detail/:id")
  async detail(@Param("id", ParseIntPipe) id: number) {
    this.logger.debug("productForAdmin Call, 1");
    const res = await this.productSvc.detail(id);
    if (!res) {
      throw new NotFoundException(`the id: ${id} is not exists`);
    }
    return res;
  }

  @Post()
  async newProdict(
    @Req() request: Request,
    @Body() input: ProductInputDto
  ) {
    input.validateInput();
    const user = HttpUtils.getPrinciple(request);
    this.productSvc.enrollProduct(input, user);

  }

}