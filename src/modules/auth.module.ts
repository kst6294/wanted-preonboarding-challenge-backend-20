import { Module } from '@nestjs/common';
import { AuthController } from 'src/api/controller/rest/auth.controller';
import { AuthService } from 'src/providers/auth.service';
import { UserModule } from './user.module';



@Module({
  imports: [UserModule],
  controllers: [AuthController],
  providers: [AuthService]
})
export class AuthModule { }