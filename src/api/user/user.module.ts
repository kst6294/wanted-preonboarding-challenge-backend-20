import { Module } from '@nestjs/common';
import { UserService } from './user.service';
import { UserController } from './user.controller';
import { AuthModule } from '../auth/auth.module';
import { PrismaModule } from '../../common/prisma/prisma.module';

@Module({
  imports: [AuthModule, PrismaModule],
  controllers: [UserController],
  providers: [UserService],
})
export class UserModule {}
