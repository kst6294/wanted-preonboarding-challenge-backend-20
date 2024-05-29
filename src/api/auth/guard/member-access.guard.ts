import { ExecutionContext, Injectable } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { Request } from 'express';
import { IJwtPayload } from '../interface/jwt-payload.interface';

@Injectable()
export class MemberAccessGuard extends AuthGuard('jwt') {
  handleRequest<TUser>(
    err: any,
    user: any,
    info: any,
    context: ExecutionContext,
    status?: any,
  ): TUser {
    const req = context.switchToHttp().getRequest<Request>();

    return user;
  }
}
