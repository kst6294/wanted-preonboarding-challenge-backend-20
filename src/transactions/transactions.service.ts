import {
  BadRequestException,
  ConflictException,
  Inject,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import {
  TransactionIncludeProduct,
  TransactionRepositoryInterface,
} from './interfaces/transaction.repository.interface';
import { TransactionServiceInterface } from './interfaces/transaction.service.interface';
import { ProductStatus, Transaction, TransactionStatus } from '@prisma/client';
import { GetTransactionsDTO } from './dto/get-transactions.dto';
import { ProductServiceInterface } from 'src/products/interfaces/product.service.interface';
import { ProductQuantityServiceInterface } from 'src/products/interfaces/product.quantity.service.interface';
import { PrismaService } from 'prisma/prisma.service';

@Injectable()
export class TransactionsService implements TransactionServiceInterface {
  constructor(
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productService: ProductServiceInterface,
    @Inject('PRODUCT_QUANTITY_SERVICE_INTERFACE')
    private readonly productQuantityService: ProductQuantityServiceInterface,
    @Inject('TRANSACTION_REPOSITORY_INTERFACE')
    private readonly transactionRepository: TransactionRepositoryInterface,
    private readonly prisma: PrismaService,
  ) {}

  async confirm(transactionId: number): Promise<void> {
    await this.transactionRepository.confirm(transactionId);
  }

  async checkIsPending(transactionId: number, buyerId: number) {
    const transaction =
      await this.transactionRepository.findByTransactionIdAndBuyerId(
        transactionId,
        buyerId,
      );

    if (!transaction) {
      throw new NotFoundException('현재 거래내역이 존재하지 않습니다.');
    }
    if (transaction.status === TransactionStatus.APPROVED) {
      throw new ConflictException('이미 판매승인된 상품입니다.');
    }

    if (transaction.status === TransactionStatus.REJECTED) {
      throw new ConflictException('이미 판매거절된 상품입니다.');
    }

    return transaction;
  }

  async checkAlreadyBought(
    productId: number,
    buyerId: number,
    sellerId: number,
  ) {
    const transaction =
      await this.transactionRepository.findByBuyerIdAndSellerId(
        productId,
        buyerId,
        sellerId,
      );

    if (!transaction) {
      return true;
    }

    if (transaction && transaction.status === TransactionStatus.PENDING) {
      throw new ConflictException('이미 구매 대기중인 상품입니다.');
    }

    if (transaction && transaction.status === TransactionStatus.APPROVED) {
      throw new ConflictException('이미 구매 완료된 상품입니다.');
    }

    return true;
  }

  async create(createTransacitonInfo: any, buyerId: number) {
    const { productId, sellerId, quantity } = createTransacitonInfo;

    const product = await this.productService.findOne(productId);

    if (product.status === ProductStatus.RESERVED) {
      throw new ConflictException('이미 예약된 상품입니다.');
    }

    if (product.status === ProductStatus.SOLD) {
      throw new ConflictException('이미 판매된 상품입니다.');
    }

    // !! 수량에 따른 제품 상태 변경
    // !! 1. 상품 수량 조회
    // !! 2. 현재 거래중인 상품 조회
    // !! 3. 제품 상태 변경
    const remainingQuantity =
      await this.productQuantityService.findById(productId);
    console.log('현재 남은 수량', remainingQuantity.quantity);

    if (remainingQuantity.quantity - quantity < 0) {
      throw new BadRequestException(
        '현재 재고보다 많은 상품을 구매할 수 없습니다.',
      );
    }

    console.log('구매할 수량', quantity);
    //!! 추가 판매가 가능한 수량이 남아있는 경우 - 판매중
    if (remainingQuantity.quantity > quantity) {
      await this.productService.updateStatus(
        productId,
        ProductStatus.AVAILABLE,
      );
    }

    const purchaseConfirmedCount = await this.countByProductId(productId);
    //!! 추가 판매가 불가능하고 현재 구매확정을 대기하고 있는 경우 - 예약중
    if (
      product.transactions.length > 0 &&
      remainingQuantity.quantity - quantity === 0 &&
      purchaseConfirmedCount !== 0
    ) {
      await this.productService.updateStatus(productId, ProductStatus.RESERVED);
    }
    //!! 모든 수량에 대해 모든 구매자가 모두 구매확정한 경우 - 완료
    if (product.transactions.length > 0 && purchaseConfirmedCount === 0) {
      await this.productService.updateStatus(productId, ProductStatus.SOLD);
    }

    await this.prisma.$transaction(async transaction => {
      // !! 거래 생성 및 수량 업데이트
      await this.transactionRepository.create(
        createTransacitonInfo,
        product.price,
        transaction,
      );

      const finalQuantity =
        remainingQuantity.quantity - createTransacitonInfo.quantity;

      await this.productQuantityService.updateQuantity(
        productId,
        finalQuantity,
        transaction,
      );
    });
  }

  async findBuyList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    return await this.transactionRepository.findBuyList(query, userId);
  }

  async findReservationList(
    query: GetTransactionsDTO,
    userId: number,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    return await this.transactionRepository.findReservationList(query, userId);
  }

  async approve(transactionId: number): Promise<void> {
    // !!판매자가 판매승인을 했는지 확인
    const transaction =
      await this.transactionRepository.findById(transactionId);

    switch (transaction.status) {
      case TransactionStatus.SOLD:
        await this.transactionRepository.buyConfirm(transactionId);
        break;
      case TransactionStatus.PURCHASE_CONFIRMED:
        throw new ConflictException('이미 구매 확정된 상품입니다.');
      default:
        throw new BadRequestException('판매자가 판매승인하지 않은 상품입니다.');
    }
  }

  /**
   * @desription 특정 상품의 거래내역 중 구매확정이 아닌 거래내역 카운트
   * @param productId
   * @returns
   */
  async countByProductId(productId: number): Promise<number> {
    return await this.transactionRepository.countByProductId(productId);
  }
}
