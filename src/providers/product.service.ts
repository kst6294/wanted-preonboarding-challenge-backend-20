import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Public } from 'src/baseinfra/decorator/public.api.decorator';
import { Product } from 'src/model/product.entity';
import { Repository } from 'typeorm';

@Injectable()
export class ProductService {

  constructor(
    @InjectRepository(Product)
    private readonly productRepo: Repository<Product>
  ) { }


  async list() {
    // 등록된 제품에는 "제품명", "가격", "예약상태"가 포함되어야하고, 목록조회와 상세조회시에 예약상태를 포함해야합니다
    return [];
  }

  @Public()
  detail() {

  }

  async purchaseProduct() { }

}