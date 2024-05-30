import { Global, Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Order } from 'src/model/order.entity';
import { Product } from 'src/model/product.entity';
import { User } from 'src/model/user.entity';


@Global()
@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: "mysql",
      host: "localhost",
      database: "nest",
      port: 3306,
      username: "root",
      password: "1234",
      logging: true,
      synchronize: true,
      entities: [User, Product, Order]
    })
  ]
})

export class DatabaseModule { }

