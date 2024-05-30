
import { Injectable } from '@nestjs/common';
import { UserCreateInput } from 'src/model/dto/user.create.Input';
import { User } from 'src/model/user.entity';
import { UserService } from './user.service';
import { JwtService } from '@nestjs/jwt';


@Injectable()
export class AuthService {

  constructor(
    private readonly userSvc: UserService,
    private readonly jwtSvc: JwtService
  ) { }

  async signUp(createInput: UserCreateInput): Promise<User> {
    return await this.userSvc.save(createInput);
  }

  async signIn(id: string, password: string) {
    const find = await this.userSvc.findOneByEmail(id);
    find.validatePassword(password);
    const payload = { sub: find.getId(), username: find.email };
    return { access_token: await this.jwtSvc.signAsync(payload) };
  }

  logOut() {

  }



}