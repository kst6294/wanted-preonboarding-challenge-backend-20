import { Injectable } from '@nestjs/common';
import { Product } from 'src/model/product.entity';
import { DataSource, Repository } from 'typeorm';

@Injectable()
export default class ProductRepository extends Repository<Product> {
  constructor(private readonly datasource: DataSource) {
    super(Product, datasource.createEntityManager());
  }

  async productPage(filter: any, page: number = 0, size: number = 20) {
    console.log(page, size);
    this.createQueryBuilder()
      .select('product')
      .from(Product, 'product')
      .where(Product);
  }
}
