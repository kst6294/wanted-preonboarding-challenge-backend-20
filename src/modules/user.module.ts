import { Module } from '@nestjs/common';
import { DatabaseModule } from 'src/baseinfra/database/database.module';
import { UserService } from 'src/providers/user.service';


@Module({
  providers: [UserService],
  exports: [UserService]
})
export class UserModule { }