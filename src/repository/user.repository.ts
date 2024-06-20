import { Injectable } from '@nestjs/common';
import { User } from 'src/model/user.entity';
import { DataSource, Repository } from 'typeorm';

@Injectable()
export class UserRepositoryExt extends Repository<User> {
  constructor(datasource: DataSource) {
    super(User, datasource.createEntityManager());
  }
}