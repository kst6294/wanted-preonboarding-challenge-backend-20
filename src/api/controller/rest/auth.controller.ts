import { Body, Controller, Injectable, Post } from '@nestjs/common';
import { UserCreateInput } from 'src/model/dto/user.create.Input';
import { AuthService } from 'src/providers/auth.service';
import { UserService } from 'src/providers/user.service';



@Injectable()
@Controller("auth")
export class AuthController {

  constructor(
    private readonly userSvc: UserService,
    private readonly authSvc: AuthService
  ) { }


  @Post("/signin")
  signUp(@Body() createInput: UserCreateInput) {
    console.log(createInput);
    return "it's okay";
  }

  signIn() {

  }

  logOut() {

  }

}