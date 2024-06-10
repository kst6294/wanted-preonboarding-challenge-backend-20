import { User } from '@prisma/client';

export interface UsersServiceInterface {
  findOne(email: string): Promise<User | null>;
  create(email: string, name: string, password: string): Promise<User>;
}
