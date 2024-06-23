import { Module } from '@nestjs/common';
import { ProductController } from 'src/api/controller/rest/product.controller';
import { ProductService } from 'src/providers/product.service';
import { UserModule } from './user.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Product } from 'src/model/product.entity';
import ProductRepoService from 'src/repository/product.repository';

@Module({
  imports: [
    UserModule
  ],
  providers: [ProductService, ProductRepoService],
  controllers: [ProductController],
  exports: [ProductService]
})
export class ProductModule { }