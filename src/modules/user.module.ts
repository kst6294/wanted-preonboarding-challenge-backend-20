import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DatabaseModule } from 'src/baseinfra/database/database.module';
import { User } from 'src/model/user.entity';
import UserService from 'src/providers/user.service';


@Module({
  imports: [TypeOrmModule.forFeature([User])],
  providers: [UserService],
  exports: [UserService]
})
export class UserModule { }