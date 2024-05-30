import { PrimaryGeneratedColumn } from 'typeorm';
import { BaseEntity } from './base.entity';

export class BaseEntityWithId extends BaseEntity {

  @PrimaryGeneratedColumn()
  protected id: number;
}