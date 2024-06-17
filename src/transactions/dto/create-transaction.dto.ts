import { TransactionStatus } from '@prisma/client';
import { IsEnum, IsInt } from 'class-validator';

export class CreateTransactionDto {
  @IsInt()
  sellerId: number;

  @IsInt()
  quantity: number;
}
