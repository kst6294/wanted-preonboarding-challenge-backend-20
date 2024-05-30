import { IsEmail, IsNotEmpty } from 'class-validator';

export class UserCreateInput {
  @IsEmail()
  email: string;

  @IsNotEmpty()
  password: string;

}