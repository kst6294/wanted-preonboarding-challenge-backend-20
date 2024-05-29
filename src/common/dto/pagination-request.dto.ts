import { Type } from 'class-transformer';
import { IsOptional } from 'class-validator';

export class PaginationRequestDto {
  @Type(() => Number)
  @IsOptional()
  page: number = 1;

  @Type(() => Number)
  @IsOptional()
  limit: number = 10;

  getOffset() {
    return (this.page - 1) * this.limit;
  }
}
