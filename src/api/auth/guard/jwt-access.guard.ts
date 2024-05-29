import {
  ExecutionContext,
  Injectable,
  UnauthorizedException,
} from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { Response } from 'express';

@Injectable()
export class JwtAccessGuard extends AuthGuard('jwt') {
  handleRequest(err: any, user: any, info: any, context: ExecutionContext) {
    if (!user) {
      const response: Response = context.switchToHttp().getResponse();
      response.clearCookie('accessToken');
      throw new UnauthorizedException('회원만 이용 가능한 서비스입니다');
    }

    return user;
  }
}
