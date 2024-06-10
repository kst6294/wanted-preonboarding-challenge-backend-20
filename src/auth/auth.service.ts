import * as bcrypt from 'bcrypt';
import { Inject, Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { User } from '@prisma/client';
import { UsersServiceInterface } from 'src/users/interfaces/users.service.interface';

@Injectable()
export class AuthService {
  constructor(
    @Inject('USERS_SERVICE_INTERFACE')
    private usersService: UsersServiceInterface,
    private jwtService: JwtService,
  ) {}

  async validateUser(email: string, password: string): Promise<any> {
    const user = await this.usersService.findOne(email);
    const isCorrectPassword = await bcrypt.compare(password, user.password);

    if (user && isCorrectPassword) {
      // 실제 구현에서는 해시된 비밀번호를 비교해야 합니다.
      const { password, ...result } = user;
      return result;
    }

    return null;
  }

  async login(user: User) {
    const payload = { email: user.email, sub: user.id };
    return {
      access_token: this.jwtService.sign(payload),
    };
  }
}
