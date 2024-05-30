import { Body, Controller, Get, Global, Injectable, Param, Post, Query, UseGuards } from '@nestjs/common';
import { Public } from 'src/baseinfra/decorator/public.api.decorator';
import { UserCreateInput } from 'src/model/dto/user.create.Input';
import { AuthService } from 'src/providers/auth.service';
import { UserService } from 'src/providers/user.service';


@Controller("auth")
export class AuthController {

  constructor(
    private readonly userSvc: UserService,
    private readonly authSvc: AuthService
  ) { }


  @Public()
  @Post("/sign-up")
  async signUp(@Body() createInput: UserCreateInput) {
    const newUser = await this.authSvc.signUp(createInput);
    return newUser;
  }

  @Public()
  @Get("sign-in")
  async signIn(@Query("id") id: string, @Query("password") password: string) {
    return await this.authSvc.signIn(id, password);
  }


  logOut() {

  }

}