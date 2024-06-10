import { Body, Controller, Get, Inject, Post } from '@nestjs/common';
import { UsersServiceInterface } from './interfaces/users.service.interface';
import { User } from '@prisma/client';
import { CreateUserDto } from './dto/create-user.dto';

@Controller('users')
export class UsersController {
  constructor(
    @Inject('USERS_SERVICE_INTERFACE')
    private readonly usersService: UsersServiceInterface,
    // @Inject("AUTH_SERVICE_INTERFACE")
    // private readonly authService: AuthServiceInterface,
  ) {}

  @Post()
  async create(@Body() body: CreateUserDto): Promise<User> {
    console.log('body', body);
    const { email, name, password } = body;
    return await this.usersService.create(email, name, password);
  }

  @Get('/:id')
  async findOne(email: string): Promise<User | null> {
    return this.usersService.findOne(email);
  }
}
