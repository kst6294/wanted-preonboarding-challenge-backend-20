import { Injectable } from '@nestjs/common';
import { User } from '@prisma/client';
import { PrismaService } from 'prisma/prisma.service';
import { CreateUserDto } from 'src/users/dto/create-user.dto';
import { UsersRepositoryInterface } from 'src/users/interfaces/users.repository.interface';

@Injectable()
export class UsersRepository implements UsersRepositoryInterface {
  constructor(private readonly prisma: PrismaService) {}

  async findOne(email: string): Promise<User | null> {
    return this.prisma.user.findUnique({
      where: {
        email,
      },
    });
  }

  async create(body: CreateUserDto): Promise<User> {
    return this.prisma.user.create({
      data: {
        ...body,
      },
    });
  }
}
