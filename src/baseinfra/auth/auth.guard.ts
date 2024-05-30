import { CanActivate, ExecutionContext, Injectable, UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { JWT_CONST } from 'src/app.const';
import { Request } from 'express';
import { Reflector } from '@nestjs/core';
import { IS_PUBLIC_KEY } from '../decorator/public.api.decorator';


@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private readonly jwtSvc: JwtService,
    private readonly reflector: Reflector
  ) { }

  async canActivate(context: ExecutionContext): Promise<boolean> {

    if (this.checkPublicAPi(context)) {
      return true;
    }


    const req = context.switchToHttp().getRequest();
    const jwtToken = this.extractTokenFromHeader(req);


    if (!jwtToken) {
      throw new UnauthorizedException("로그인 되지 않은 사용자");
    }

    try {
      const payload = await this.jwtSvc.verifyAsync(jwtToken, { secret: JWT_CONST.secret });
      req['user'] = payload;
    } catch (e) {
      throw new UnauthorizedException("인증되지 않은 사용자");
    }
    return true;
  }

  private extractTokenFromHeader(request: Request): string | undefined {
    const [type, token] = request.headers.authorization?.split(" ") ?? [];
    return type === "Bearer" ? token : undefined;
  }

  private checkPublicAPi(context: ExecutionContext) {
    const isPublic = this.reflector.getAllAndOverride<boolean>(IS_PUBLIC_KEY, [
      context.getHandler(),
      context.getClass()
    ]);
    return isPublic;
  }

}