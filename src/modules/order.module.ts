import { Module } from '@nestjs/common';
import { OrderController } from 'src/api/controller/rest/order.controller';
import OrderService from 'src/providers/order.service';
import OrderRepoService from 'src/repository/order.repository';


@Module({
  providers: [OrderService, OrderRepoService],
  controllers: [OrderController],
  exports: [OrderService]
})
export default class OrderModule { }