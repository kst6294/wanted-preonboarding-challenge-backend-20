import { Controller, Get, UseGuards } from '@nestjs/common';


@Controller("product")
export class ProductController {


  @Get("list")
  async products() {
    return new Promise(res => {
      res([1, 2, 3]);
    });
  }

  @Get("/admin")
  @UseGuards()
  productsForAdmin() {
    return [1, 2, 4];
  }

}