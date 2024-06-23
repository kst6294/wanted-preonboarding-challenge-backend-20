import { BadRequestException, Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import UserCreateInput from 'src/model/dto/user.create.Input';

import { User_Status } from 'src/model/enums/user.status.enum';
import { User } from 'src/model/user.entity';
import { Repository } from 'typeorm';

@Injectable()
export default class UserService {
  constructor(
    @InjectRepository(User)
    private readonly userRepo: Repository<User>
  ) { }

  async save(input: UserCreateInput): Promise<User> {
    const existsUser = await this.getUserByEmail(input.email);
    if (existsUser) {
      throw new BadRequestException("존재하는 이메일");
    }
    const user: User = User.Builder()
      .bdEmail(input.email)
      .bdPassword(input.password)
      .bdStasus(User_Status.Registered)
      .build();

    return await this.userRepo.save(user);
  }

  async findOneByEmail(email: string) {
    const user = await this.getUserByEmail(email);

    if (!user) {
      throw new NotFoundException("No User Exists");
    }
    return user;
  }

  async findById(id: number) {
    const user = await this.userRepo.findOneBy({ id });
    if (!user) {
      throw new NotFoundException(`user id: ${id} is not exists`);
    }
    return await this.userRepo.findOneBy({ id });
  }

  private async getUserByEmail(email: string) {
    return await this.userRepo.findOneBy({ email });
  }
}