import { Module } from '@nestjs/common';
import { ProductController } from 'src/api/controller/rest/product.controller';
import { ProductService } from 'src/providers/product.service';
import { UserModule } from './user.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Product } from 'src/model/product.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([Product]),
    UserModule
  ],
  providers: [ProductService],
  controllers: [ProductController]
})
export class ProductModule { }