import { Injectable, NotFoundException } from '@nestjs/common';
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
    const user: User = User.Builder()
      .bdEmail(input.email)
      .bdPassword(input.password)
      .bdStasus(User_Status.Registered)
      .build();

    return await this.userRepo.save(user);
  }

  async findOneByEmail(email: string) {
    const ret = await this.userRepo.findOneBy({ email });

    if (!ret) {
      throw new NotFoundException("No User Exists");
    }
    return ret;
  }
}