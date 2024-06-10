import { Module } from '@nestjs/common';
import { UsersService } from './users.service';
import { UsersController } from './users.controller';
import { UsersRepository } from 'src/repositories/user.repository';
import { PrismaService } from 'prisma/prisma.service';

@Module({
  controllers: [UsersController],
  providers: [
    {
      provide: 'USERS_SERVICE_INTERFACE',
      useClass: UsersService,
    },
    {
      provide: 'USERS_REPOSITORY_INTERFACE',
      useClass: UsersRepository,
    },
    PrismaService,
  ],
})
export class UsersModule {}
