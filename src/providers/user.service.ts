import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { UserCreateInput } from 'src/model/dto/user.create.Input';
import { User } from 'src/model/user.entity';
import { Repository } from 'typeorm';


@Injectable()
export class UserService {

  constructor(
  ) { }


  async create(input: UserCreateInput): Promise<User> {
    return new Promise(res => {
      res({} as User);
    });
  }
}