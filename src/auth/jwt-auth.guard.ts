import { ExecutionContext, Injectable } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';

@Injectable()
export class JwtAuthGuard extends AuthGuard('jwt') {
  async canActivate(context: ExecutionContext): Promise<boolean> {
    const canActivate = (await super.canActivate(context)) as boolean;

    if (canActivate) {
      const request = context.switchToHttp().getRequest();
      const user = request.user;
      request.user = user;
    }
    return canActivate;
  }
}
