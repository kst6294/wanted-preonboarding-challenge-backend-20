import { Injectable, Logger, UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { IJwtPayload } from './interface/jwt-payload.interface';
import { IUser } from '../user/interface/user.interface';
import { PrismaService } from '../../common/prisma/prisma.service';

@Injectable()
export class AuthService {
  private readonly logger = new Logger(AuthService.name);
  constructor(
    private readonly jwtService: JwtService,
    private readonly prismaService: PrismaService,
  ) {}

  /**
   * JwtAccessStrategy에서 사용자를 검증
   */
  async checkExistUser(userIdx: IUser['idx']): Promise<IJwtPayload> {
    const findUserInfoResut = await this.prismaService.user.findUnique({
      select: {
        idx: true,
        name: true,
      },
      where: {
        idx: userIdx,
      },
    });

    if (!findUserInfoResut) {
      throw new UnauthorizedException('로그인 후 이용가능합니다');
    }

    return {
      idx: findUserInfoResut.idx,
      name: findUserInfoResut.name,
    };
  }

  async generateAccessToken(jwtOption: IJwtPayload): Promise<string> {
    return this.jwtService.sign(jwtOption);
  }
}
