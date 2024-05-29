import { IsNotEmpty } from 'class-validator';
import { IUser } from '../interface/user.interface';

export class LoginRequestDto implements IUser.ILocalLoginRequest {
  /**
   * 이메일
   * @example test@google.com
   */
  @IsNotEmpty()
  email: string;

  /**
   * 비밀번호
   * @example 1234Asd..
   */
  @IsNotEmpty()
  password: string;
}
