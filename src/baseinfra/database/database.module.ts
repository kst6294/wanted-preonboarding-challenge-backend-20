import { Global, Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Order } from 'src/model/order.entity';
import { Product } from 'src/model/product.entity';
import { User } from 'src/model/user.entity';
import { DataSource, DataSourceOptions } from 'typeorm';
import { addTransactionalDataSource } from 'typeorm-transactional';


export interface PageRequest {
  page: number;
  size: number;
}

@Global()
@Module({
  imports: [
    TypeOrmModule.forRootAsync({
      useFactory() {
        return {
          type: "mysql",
          host: "localhost",
          database: "nest",
          port: 3306,
          username: "root",
          password: "1234",
          logging: true,
          synchronize: true,
          entities: [User, Product, Order]
        };
      },
      async dataSourceFactory(options?: DataSourceOptions) {
        if (!options) {
          throw new Error('Invalid Options passed');
        }
        return addTransactionalDataSource(new DataSource(options));
      }
    })
  ]
})

export class DatabaseModule { }


// TypeOrmModule.forRoot({
//   type: "mysql",
//   host: "localhost",
//   database: "nest",
//   port: 3306,
//   username: "root",
//   password: "1234",
//   logging: true,
//   synchronize: true,
//   entities: [User, Product, Order]
// }),




