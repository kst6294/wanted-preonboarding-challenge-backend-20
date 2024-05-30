import { Module } from '@nestjs/common';
import { DatabaseModule } from 'src/baseinfra/database/database.module';
import { AppController } from '../api/controller/rest/app.controller';
import { AppService } from '../providers/app.service';
import { AuthModule } from './auth.module';

@Module({
  imports: [DatabaseModule, AuthModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
