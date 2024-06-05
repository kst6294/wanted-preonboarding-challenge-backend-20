import {
  ConflictException,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { UserRepository } from './user.repository';
import { UserDto } from './dto/user.dto';
import { User } from './entity/user.entity';
import * as bcrypt from 'bcrypt';
import * as jwt from 'jsonwebtoken';
import { SignInDto } from './dto/signIn.dto';

@Injectable()
export class UserService {
  constructor(private readonly userRepository: UserRepository) {}

  async signUp(userDto: UserDto): Promise<User> {
    const { email, password, name } = userDto;
    const exUser = await this.userRepository.validateUser(email);
    if (exUser) {
      throw new ConflictException('이미 존재하는 사용자입니다.');
    }

    // 비밀번호 암호화
    const hashedPassword = await bcrypt.hash(password, 10);

    return await this.userRepository.createUser(email, hashedPassword, name);
  }

  async signIn(signInDto: SignInDto): Promise<string> {
    const { email, password } = signInDto;
    const user = await this.userRepository.validateUser(email);
    if (!user) {
      throw new NotFoundException('존재하지 않는 계정입니다.');
    }

    const validatepassword = await bcrypt.compare(password, user.password);
    if (!validatepassword) {
      throw new UnauthorizedException('비밀번호가 일치하지 않습니다.');
    }

    const userId = user.userId;
    const token = jwt.sign({ userId }, process.env.JWT_SECRET, {
      expiresIn: '1h',
    });
    return token;
  }
}
