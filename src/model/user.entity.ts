import { Column, Entity } from 'typeorm';
import { BaseEntityWithId } from './base.entity.withid';
import { User_Status } from './enums/user.status.enum';

@Entity()
export class User extends BaseEntityWithId {

  @Column()
  name?: string;
  @Column()
  email: string;
  @Column()
  status: User_Status;

  constructor() {
    super();
  }

  static Builder() {
    return new User();
  }

  bdName(name?: string): User {
    this.name = name;
    return this;
  }

  bdEmail(email: string): User {
    this.email = email;
    return this;
  }

  bdStasus(status: User_Status = User_Status.Registered): User {
    this.status = status;
    return this;
  }
}