import { PRODUCT_STATUS } from '../enums/product.status.enum';


export class ProductUpdateInput {
  id: number;
  name: string;
  status: PRODUCT_STATUS;
  price: number;
}