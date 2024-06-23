import { CreateDateColumn, DeleteDateColumn, UpdateDateColumn } from 'typeorm';

export default class BaseEntity {

  @CreateDateColumn({ name: "created_at" })
  private createdAt: Date;

  private createdBy: number;

  @UpdateDateColumn({ name: "updated_at" })
  private updatedAt: Date;

  @DeleteDateColumn({ name: "deleted_at" })
  private deletedAt: Date;

  public delete(): void {
    this.deletedAt = new Date();
  }
}
