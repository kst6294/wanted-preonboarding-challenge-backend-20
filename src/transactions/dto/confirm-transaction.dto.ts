import { IsInt } from 'class-validator';

export class ConfirmTransactionDto {
  @IsInt()
  buyerId: number;
}
