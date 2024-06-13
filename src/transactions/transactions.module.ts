import { Module } from '@nestjs/common';
import { TransactionsService } from './transactions.service';
import { TransactionsController } from './transactions.controller';
import { TransactionRepository } from 'src/repositories/transaction.repository';
import { PrismaService } from 'prisma/prisma.service';
import { ProductsService } from 'src/products/products.service';
import { ProductRepository } from 'src/repositories/product.repository';
import { ProductQuantityRepository } from 'src/repositories/product-quantity.repository';

@Module({
  controllers: [TransactionsController],
  providers: [
    { provide: 'PRODUCT_SERVICE_INTERFACE', useClass: ProductsService },
    { provide: 'TRANSACTION_SERVICE_INTERFACE', useClass: TransactionsService },
    { provide: 'PRODUCT_REPOSITORY_INTERFACE', useClass: ProductRepository },
    {
      provide: 'PRODUCT_QUANTITY_REPOSITORY_INTERFACE',
      useClass: ProductQuantityRepository,
    },
    {
      provide: 'TRANSACTION_REPOSITORY_INTERFACE',
      useClass: TransactionRepository,
    },
    PrismaService,
  ],
})
export class TransactionsModule {}
