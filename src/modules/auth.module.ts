import { Module } from '@nestjs/common';
import { AuthController } from 'src/api/controller/rest/auth.controller';
import { AuthService } from 'src/providers/auth.service';
import { UserModule } from './user.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from 'src/model/user.entity';
import { JwtModule } from '@nestjs/jwt';
import { configDotenv } from 'dotenv';
import { JWT_CONST } from 'src/app.const';
import { APP_GUARD } from '@nestjs/core';
import { AuthGuard } from 'src/baseinfra/auth/auth.guard';



@Module({
  imports: [
    TypeOrmModule.forFeature([User]),
    UserModule,
    JwtModule.register({
      global: true,
      secret: JWT_CONST.secret,
      signOptions: { expiresIn: '1080s' }
    }),
  ],
  controllers: [AuthController],
  providers: [AuthService,
    { provide: APP_GUARD, useClass: AuthGuard }
  ]
})
export class AuthModule { }