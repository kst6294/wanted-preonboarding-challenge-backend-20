import { Injectable, NotFoundException } from '@nestjs/common';
import { WantedPrincipal } from 'src/baseinfra/wanted.principal';
import { PageResponseDto } from 'src/model/dto/common.pagination.response.dto';
import ProductInputDto from 'src/model/dto/product.create.input';
import { ProductUpdateInput } from './../model/dto/product.update.input';


import { Product } from 'src/model/product.entity';
import ProductRepoService from 'src/repository/product.repository';
import { Transactional } from 'typeorm-transactional';
import UserService from './user.service';
import OrderService from './order.service';

@Injectable()
export class ProductService {

  constructor(
    private readonly productRepoSvc: ProductRepoService,
    private readonly userSvc: UserService,
    private readonly orderSvc: OrderService
  ) { }

  async productPage(page: number, size: number): Promise<PageResponseDto<Product>> {
    // 등록된 제품에는 "제품명", "가격", "예약상태"가 포함되어야하고, 목록조회와 상세조회시에 예약상태를 포함해야합니다
    const res = await this.productRepoSvc.productPage(page, size);
    console.log("query result", res);
    return res;
  }

  async detail(id: number) {
    const res = await this.productRepoSvc.findOneBy({ id });
    if (!res) {
      throw new NotFoundException(`product not Found: id=${id}`);
    }
    return res;
  }

  @Transactional()
  async purchaseProduct(productId: number, principal: WantedPrincipal) {
    const user = await this.userSvc.findById(principal.id);
    const product = await this.productRepoSvc.findById(productId);
    product.purchase();
    const order = this.orderSvc.makeOrder(productId, user);
    return order;
  }

  @Transactional()
  async enrollProduct(input: ProductInputDto, principal: WantedPrincipal) {
    const user = await this.userSvc.findById(principal.id);
    const product = Product.create(input, user);
    return await this.productRepoSvc.save(product);
  }

  @Transactional()
  async updateProduct(input: ProductUpdateInput) {
    const product = await this.productRepoSvc.findById(input.id);
    const modifiedProduct = product.update(input);
    return modifiedProduct;
  }
}