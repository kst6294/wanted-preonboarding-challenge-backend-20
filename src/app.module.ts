import Joi from 'joi';
import { MiddlewareConsumer, Module, NestModule } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PrismaModule } from './common/prisma/prisma.module';
import { ConfigModule } from '@nestjs/config';
import { LoggerMiddleware } from './common/middleware/logger.middleware';
import { AuthModule } from './api/auth/auth.module';
import { UserModule } from './api/user/user.module';
import { ProductModule } from './api/product/product.module';
import { TransactionModule } from './api/transaction/transaction.module';

@Module({
  imports: [
    PrismaModule,
    ConfigModule.forRoot({
      isGlobal: true,
      validationSchema: Joi.object({
        NODE_ENV: Joi.string().valid('dev', 'prod', 'test'),
        PORT: Joi.number().port().default(3000),
        JWT_SECRET_KEY: Joi.string(),
        JWT_EXPIRED_TIME: Joi.string(),
      }),
    }),
    AuthModule,
    UserModule,
    ProductModule,
    TransactionModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer.apply(LoggerMiddleware).forRoutes('*');
  }
}
