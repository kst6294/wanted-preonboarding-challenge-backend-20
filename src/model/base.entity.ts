


export class BaseEntity {
  private createdAt: Date;
  private createdBy: number;
  private updateAt: Date;
  private deletedAt: Date;

  public delete(): void {
    this.deletedAt = new Date();
  }
}