import { Module } from '@nestjs/common';
import { ProductsService } from './products.service';
import { ProductsController } from './products.controller';
import { PrismaService } from 'prisma/prisma.service';
import { ProductRepository } from 'src/repositories/product.repository';
import { ProductQuantityRepository } from 'src/repositories/product-quantity.repository';

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
    {
      provide: 'PRODUCT_QUANTITY_REPOSITORY_INTERFACE',
      useClass: ProductQuantityRepository,
    },
    PrismaService,
  ],
})
export class ProductsModule {}
