import { Body, Controller, HttpStatus, Post, Res } from '@nestjs/common';
import { UserService } from './user.service';
import { UserDto } from './dto/user.dto';
import { Response } from 'express';
import { SignInDto } from './dto/signIn.dto';

@Controller('user')
export class UserController {
  constructor(private readonly userService: UserService) {}

  @Post('signUp')
  async signUp(@Body() userDto: UserDto, @Res() res: Response) {
    await this.userService.signUp(userDto);
    return res.status(HttpStatus.OK).json({ message: 'SignUp success' });
  }

  @Post('signIn')
  async signIn(@Body() signInDto: SignInDto, @Res() res: Response) {
    const token = await this.userService.signIn(signInDto);
    res.cookie('authorization', `Bearer ${token}`);
    return res.status(HttpStatus.OK).json({ message: 'SignIn success' });
  }
}
