import { Injectable, Logger, NotFoundException } from '@nestjs/common';
import { PageRequest } from 'src/baseinfra/database/database.module';
import { WantedPrincipal } from 'src/baseinfra/wanted.principal';
import OrderRepoService from 'src/repository/order.repository';
import { Transactional } from 'typeorm-transactional';
import { ProductService } from './product.service';
import { Order } from 'src/model/order.entity';
import { User } from 'src/model/user.entity';
import { ORDER_STATUS } from 'src/model/enums/order.status.enum';


@Injectable()
export default class OrderService {

  private readonly logger = new Logger(OrderService.name);

  constructor(
    private readonly orderRepoSvc: OrderRepoService,
    private readonly productSvc: ProductService
  ) { }

  async getOrderPage(user: WantedPrincipal, pageReq: PageRequest) {
    const res = await this.orderRepoSvc.orderPageByUserId(user.id, pageReq);
    return res;
  }

  @Transactional()
  async makeOrder(productId: number, user: User) {
    this.productSvc.detail(productId);
    const order = Order.create(productId, user);
    return await this.orderRepoSvc.save(order);
  }

  @Transactional()
  async confirmOrder(orderId: number) {
    const order = await this.orderRepoSvc.findOneBy({ id: orderId });
    if (!order) {
      throw new NotFoundException(`Order is not Found id=${orderId}`);
    }
    order.status = ORDER_STATUS.Complete;
    return order;
  }
}