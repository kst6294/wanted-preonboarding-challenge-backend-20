import {
  Controller,
  Get,
  HttpStatus,
  Param,
  Post,
  Put,
  Res,
} from '@nestjs/common';
import { OrderService } from './order.service';
import { Response } from 'express';

@Controller('order')
export class OrderController {
  constructor(private readonly orderService: OrderService) {}

  // 제품 예약
  @Post(':productId')
  async createOrder(
    @Param('productId') productId: number,
    @Res() res: Response,
  ) {
    const { userId } = res.locals.user;
    await this.orderService.createOrder(userId, productId);
    return res.status(HttpStatus.OK).json({ message: '제품 예약 완료' });
  }

  // 구매한 제품 목록 확인
  @Get('purchasedList')
  async getPurchasedProducts(@Res() res: Response) {
    const { userId } = res.locals.user;
    const purchasedProducts =
      await this.orderService.findPurchasedProducts(userId);
    return res.status(HttpStatus.OK).json(purchasedProducts);
  }

  // 예약중인 제품 목록
  @Get('reservedList')
  async getReservedProducts(@Res() res: Response) {
    const { userId } = res.locals.user;
    const reservedProducts =
      await this.orderService.findReservedProducts(userId);
    return res.status(HttpStatus.OK).json(reservedProducts);
  }

  // 제품 판매 승인
  @Put(':productId')
  async approveProductSale(
    @Param('productId') productId: number,
    @Res() res: Response,
  ) {
    const { userId } = res.locals.user;
    await this.orderService.approveProductSale(userId, productId);
    return res.status(HttpStatus.OK).json({ message: '판매 승인 완료' });
  }
}
