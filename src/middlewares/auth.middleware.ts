import {
  Injectable,
  NestMiddleware,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { NextFunction, Request, Response } from 'express';
import { UserRepository } from 'src/user/user.repository';

@Injectable()
export class AuthMiddleware implements NestMiddleware<Request, Response> {
  constructor(
    private readonly jwtService: JwtService,
    private readonly userRepository: UserRepository,
  ) {}
  async use(req: Request, res: Response, next: NextFunction) {
    try {
      let authorization: string = req.cookies.authorization;

      if (!authorization) {
        throw new UnauthorizedException();
      }

      const [tokenType, tokenValue] = authorization.split(' ');
      if (tokenType !== 'Bearer') {
        res.clearCookie('authorization');
        throw new UnauthorizedException('잘못된 쿠키 형식입니다.');
      }

      const { userId } = this.jwtService.verify(tokenValue, {
        secret: process.env.JWT_SECRET,
      });
      const user = await this.userRepository.findUserByPk(userId);
      if (user) {
        res.locals.user = user;
        next();
      } else {
        throw new NotFoundException('회원 정보가 없습니다.');
      }
    } catch (error) {
      res.clearCookie('authorization');
      next(error);
    }
  }
}
