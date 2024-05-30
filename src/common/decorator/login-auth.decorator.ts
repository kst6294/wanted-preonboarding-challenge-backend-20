import { UseGuards, applyDecorators } from '@nestjs/common';
import { JwtAccessGuard } from '../../api/auth/guard/jwt-access.guard';
import { ApiBearerAuth } from '@nestjs/swagger';
import { Exception } from './exception.decorator';

export const LoginAuth = () => {
  return applyDecorators(
    ApiBearerAuth(),
    UseGuards(JwtAccessGuard),
    Exception(401, '로그인이 필요합니다'),
  );
};
