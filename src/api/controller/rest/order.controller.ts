import { Controller, DefaultValuePipe, Get, Logger, Param, ParseIntPipe, Post, Query, Req } from '@nestjs/common';
import { WantedPrincipal } from 'src/baseinfra/wanted.principal';
import { PageRequestDto } from 'src/model/dto/common.pagination.request.dto';
import OrderService from 'src/providers/order.service';
import { ProductService } from 'src/providers/product.service';
import { HttpUtils } from 'src/utils/util.http';


@Controller("order")
export class OrderController {

  private readonly logger = new Logger(OrderController.name);

  constructor(
    private orderSvc: OrderService,
    private productSvc: ProductService
  ) { }

  @Get()
  async getMyOrder(
    @Req() request: Request,
    @Query("page", new DefaultValuePipe(0), ParseIntPipe) page: number,
    @Query("size", new DefaultValuePipe(20), ParseIntPipe) size: number
  ) {
    const user = HttpUtils.getPrinciple(request);
    const orderPage = await this.orderSvc.getOrderPage(user, { page, size });
    return orderPage;
  }

  @Post("/confirm/:orderId")
  async confirmOrder(
    @Req() request: Request,
    @Param("orderId") orderId: number
  ) {
    const solvedOrder = await this.orderSvc.confirmOrder(orderId);
    return solvedOrder;
  }

  @Post("/new/:productId")
  async newOrder(
    @Req() reqeust: Request,
    @Param("productId") productId: number
  ) {
    const user = HttpUtils.getPrinciple(reqeust);
    const newOrder = await this.productSvc.purchaseProduct(productId, user);
    return newOrder;
  }
}