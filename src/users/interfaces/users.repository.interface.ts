import { User } from '@prisma/client';
import { CreateUserDto } from '../dto/create-user.dto';

export interface UsersRepositoryInterface {
  findOne(email: string): Promise<User | null>;
  create(body: CreateUserDto): Promise<User>;
}
