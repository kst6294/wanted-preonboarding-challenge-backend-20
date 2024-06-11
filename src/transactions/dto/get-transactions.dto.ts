import { Type } from 'class-transformer';
import { IsInt, IsOptional, IsString, Min } from 'class-validator';

export class GetTransactionsDTO {
  @IsInt()
  @Min(1)
  @Type(() => Number)
  limit: number;

  @IsInt()
  @Min(1)
  @Type(() => Number)
  page: number;

  @IsString()
  @IsOptional()
  name: string;
}
