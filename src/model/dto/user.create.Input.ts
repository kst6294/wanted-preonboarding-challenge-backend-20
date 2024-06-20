import { IsEmail, IsNotEmpty } from 'class-validator';

export default class UserCreateInput {
  @IsEmail()
  email: string;

  @IsNotEmpty()
  password: string;
}
