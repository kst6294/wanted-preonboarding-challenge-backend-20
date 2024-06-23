

export class PageResponseDto<T> {
  data: T[];
  page: number;
  totalItems: number;
  totalPages: number;
  hasNextPage: boolean;

  constructor(data: T[], page: number, totalItems: number, size: number) {
    console.log(totalItems);
    this.data = data;
    this.page = page;
    this.totalItems = totalItems;
    this.totalPages = Math.ceil(totalItems / size);
    this.hasNextPage = page < this.totalPages;
  }
}