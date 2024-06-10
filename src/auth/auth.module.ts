import { Module } from '@nestjs/common';
import { AuthService } from './auth.service';
import { UsersModule } from '../users/users.module';
import { PassportModule } from '@nestjs/passport';
import { JwtModule } from '@nestjs/jwt';
import { LocalStrategy } from './local.strategy';
import { AuthController } from './auth.controller';
import { UsersService } from 'src/users/users.service';
import { UsersRepository } from 'src/repositories/user.repository';
import { PrismaService } from 'prisma/prisma.service';
import { JwtStrategy } from './jwt.strategy';

@Module({
  imports: [
    UsersModule,
    PassportModule,
    JwtModule.register({
      secret: 'secretKey',
      signOptions: { expiresIn: '60s' },
    }),
  ],
  providers: [
    AuthService,
    LocalStrategy,
    JwtStrategy,
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
  controllers: [AuthController],
})
export class AuthModule {}
