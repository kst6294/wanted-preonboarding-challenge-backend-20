import { Injectable, NotFoundException } from '@nestjs/common';
import { WantedPrincipal } from 'src/baseinfra/wanted.principal';
import { PageResponseDto } from 'src/model/dto/common.pagination.response.dto';
import ProductInputDto from 'src/model/dto/product.create.input';


import { Product } from 'src/model/product.entity';
import { DataSource, Repository } from 'typeorm';
import { Transactional } from 'typeorm-transactional';

@Injectable()
export default class ProductRepoService extends Repository<Product> {
  constructor(private readonly datasource: DataSource) {
    super(Product, datasource.createEntityManager());
  }

  async productPage(page: number = 0, size: number = 20, filter?: any) {
    const [product, totalCnt] = await this.createQueryBuilder('product')
      .skip(page * size)
      .take(size)
      .getManyAndCount();

    return new PageResponseDto(product, page, totalCnt, size);
  }

  async findById(id: number) {
    const product = this.findOneBy({ id });
    if (!product) {
      throw new NotFoundException(`Product id ${id} is not Found`);
    }
    return product;
  }

  @Transactional()
  async upsertProduct(input: ProductInputDto, principal: WantedPrincipal) {

  }

}
