import { ConflictException, Inject, Injectable } from '@nestjs/common';
import { User } from '@prisma/client';
import { UsersServiceInterface } from './interfaces/users.service.interface';
import { UsersRepositoryInterface } from './interfaces/users.repository.interface';
import * as bcrypt from 'bcrypt';
@Injectable()
export class UsersService implements UsersServiceInterface {
  constructor(
    @Inject('USERS_REPOSITORY_INTERFACE')
    private readonly userRepository: UsersRepositoryInterface,
  ) {}

  async findOne(email: string): Promise<User | null> {
    return this.userRepository.findOne(email);
  }

  async create(email: string, name: string, password: string): Promise<User> {
    const user = await this.userRepository.findOne(email);

    if (user) {
      throw new ConflictException('이미 등록된 이메일 입니다.');
    }

    const salt = await bcrypt.genSalt(10);

    const hashedPassword = await bcrypt.hash(password, salt);

    const createUserBody = {
      email,
      name,
      password: hashedPassword,
    };

    return this.userRepository.create(createUserBody);
  }
}
