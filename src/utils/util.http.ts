import { MethodNotAllowedException } from '@nestjs/common';
import { WantedPrincipal } from 'src/baseinfra/wanted.principal';


export class HttpUtils {
  static getPrinciple(request: Request): WantedPrincipal {
    const user = request['user'];
    if (!user) {
      throw new MethodNotAllowedException("로그인하지 않은 사용자");
    }
    return new WantedPrincipal(user);
  }
}