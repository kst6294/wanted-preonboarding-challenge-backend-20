import { Module } from '@nestjs/common';
import { ProductsService } from './products.service';
import { ProductsController } from './products.controller';
import { PrismaService } from 'prisma/prisma.service';
import { ProductRepository } from 'src/repositories/product.repository';

@Module({
  controllers: [ProductsController],
  providers: [
    {
      provide: 'PRODUCT_SERVICE_INTERFACE',
      useClass: ProductsService,
    },
    {
      provide: 'PRODUCT_REPOSITORY_INTERFACE',
      useClass: ProductRepository,
    },
    PrismaService,
  ],
})
export class ProductsModule {}
