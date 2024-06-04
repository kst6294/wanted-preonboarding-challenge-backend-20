import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from './entity/user.entity';
import { Repository } from 'typeorm';

@Injectable()
export class UserRepository {
  constructor(
    @InjectRepository(User) private userRepository: Repository<User>,
  ) {}

  async validateUser(email: string): Promise<User> {
    const user = await this.userRepository.findOneBy({ email });
    if (!user) {
      return null;
    }
    return user;
  }

  async createUser(email: string, hashedPassword: string): Promise<User> {
    const newUser = new User();
    newUser.email = email;
    newUser.password = hashedPassword;
    return await this.userRepository.save(newUser);
  }

  async findUserByPk(userId: number): Promise<User> {
    const user = await this.userRepository.findOneBy({ userId });
    if (!user) {
      return null;
    }
    return user;
  }
}
