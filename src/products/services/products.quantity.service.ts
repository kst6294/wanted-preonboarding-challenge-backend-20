import { Inject, Injectable, NotFoundException } from '@nestjs/common';
import { ProductQuantityRepositoryInterface } from '../interfaces/product.quantity.repository.interface';
import { Prisma, ProductQuantity } from '@prisma/client';
import { ProductQuantityServiceInterface } from '../interfaces/product.quantity.service.interface';

@Injectable()
export class ProductQuantityService implements ProductQuantityServiceInterface {
  constructor(
    @Inject('PRODUCT_QUANTITY_REPOSITORY_INTERFACE')
    private productQuantityRepository: ProductQuantityRepositoryInterface,
  ) {}
  async findById(productId: number): Promise<ProductQuantity | null> {
    const productQuantity =
      await this.productQuantityRepository.findById(productId);

    if (!productQuantity) {
      throw new NotFoundException(
        '상품 수량이 존재하지 않습니다. 고객센터를 통해 문의 부탁드립니다.',
      );
    }
    return productQuantity;
  }
  async makeQuantity(
    quantity: number,
    productId: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void> {
    return await this.productQuantityRepository.makeQuantity(
      quantity,
      productId,
      transaction,
    );
  }

  async updateQuantity(
    productId: number,
    quantity: number,
    transaction: Prisma.TransactionClient,
  ): Promise<void> {
    return await this.productQuantityRepository.updateQuantity(
      productId,
      quantity,
      transaction,
    );
  }
}
