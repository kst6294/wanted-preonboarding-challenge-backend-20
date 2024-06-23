import { Injectable, Logger } from '@nestjs/common';

import { PageRequestDto } from 'src/model/dto/common.pagination.request.dto';
import { PageResponseDto } from 'src/model/dto/common.pagination.response.dto';
import { Order } from 'src/model/order.entity';
import { DataSource, Repository } from 'typeorm';

@Injectable()
export default class OrderRepoService extends Repository<Order> {

  private readonly logger = new Logger(OrderRepoService.name);

  constructor(private readonly datasource: DataSource) {
    super(Order, datasource.createEntityManager());
  }

  async orderPageByUserId(userId: number, pageObj: PageRequestDto) {
    const { page, size } = pageObj;
    const [orderList, totalCnt] = await this.createQueryBuilder()
      .where({ userId })
      .skip(page * size)
      .take(size)
      .getManyAndCount();

    return new PageResponseDto<Order>(orderList, page, totalCnt, size);
  }

}