import { Strategy, ExtractJwt, VerifyCallback } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { Injectable, UnauthorizedException } from '@nestjs/common';
import { AuthService } from './auth.service';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(private authService: AuthService) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      ignoreExpiration: false,
      secretOrKey: 'secretKey', // 안전을 위해 실제 서비스에서는 환경 변수로 관리하세요
    });
  }

  async validate(payload: any) {
    return { userId: payload.sub, username: payload.username };
  }
}
