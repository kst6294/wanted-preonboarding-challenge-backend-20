import { IsEmail, IsNotEmpty, IsString } from 'class-validator';
import { IUser } from '../interface/user.interface';

export class SignupRequestDto implements IUser.ISignupRequest {
  /**
   * 이메일
   */
  @IsString()
  @IsEmail()
  @IsNotEmpty()
  email: string;

  /**
   * 비밀번호
   */
  @IsString()
  @IsNotEmpty()
  password: string;

  /**
   * 이름
   */
  @IsString()
  @IsNotEmpty()
  name: string;
}

export class SignupResponseDto implements IUser.ISignupResponse {
  /**
   * 생성된 사용자 인덱스
   * @example 1
   */
  idx: number;

  static of(input: IUser.ISignupResponse): SignupResponseDto {
    return {
      idx: input.idx,
    };
  }
}
