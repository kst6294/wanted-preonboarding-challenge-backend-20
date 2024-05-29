import { Module } from '@nestjs/common';
import { ProductService } from './product.service';
import { ProductController } from './product.controller';
import { PrismaModule } from '../../common/prisma/prisma.module';
import { JwtAccessStrategy } from '../auth/strategy/jwt-access.strategy';
import { AuthModule } from '../auth/auth.module';

@Module({
  imports: [PrismaModule, AuthModule],
  controllers: [ProductController],
  providers: [ProductService, JwtAccessStrategy],
})
export class ProductModule {}
