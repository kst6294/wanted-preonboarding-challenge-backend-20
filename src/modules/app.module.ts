import { Module } from '@nestjs/common';
import { DatabaseModule } from 'src/baseinfra/database/database.module';
import { AppController } from '../api/controller/rest/app.controller';
import { AppService } from '../providers/app.service';
import { AuthModule } from './auth.module';
import { ProductModule } from './product.module';
import { ProductController } from 'src/api/controller/rest/product.controller';
import { ProductService } from 'src/providers/product.service';
import OrderModule from './order.module';

@Module({
  imports: [
    DatabaseModule,
    AuthModule,
    ProductModule,
    OrderModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
