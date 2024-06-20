import { Controller, Get, Logger, LoggerService, Req, UseGuards } from '@nestjs/common';
import { Public } from 'src/baseinfra/decorator/public.api.decorator';

@Controller("product")
export class ProductController {

  private readonly logger = new Logger(ProductController.name);

  @Public()
  @Get("list")
  async list() {
    return new Promise(res => {
      res([1, 2, 3]);
    });
  }


  @Public()
  @Get("/detail/:id")
  productsForAdmin(@Req() request: Request) {
    this.logger.debug("productForAdmin Call");

    return [1, 2, 4];
  }

}