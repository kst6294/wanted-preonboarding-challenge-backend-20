import {
  Controller,
  Post,
  Body,
  Param,
  UseGuards,
  Req,
  Inject,
  ParseIntPipe,
  Patch,
  Get,
  Query,
} from '@nestjs/common';
import { CreateTransactionDto } from './dto/create-transaction.dto';
import { JwtAuthGuard } from 'src/auth/jwt-auth.guard';
import { TransactionServiceInterface } from './interfaces/transaction.service.interface';
import { ProductServiceInterface } from 'src/products/interfaces/product.service.interface';
import { ConfirmTransactionDto } from './dto/confirm-transaction.dto';
import { GetTransactionsDTO } from './dto/get-transactions.dto';
import { TransactionIncludeProduct } from './interfaces/transaction.repository.interface';
import { ProductQuantityServiceInterface } from 'src/products/interfaces/product.quantity.service.interface';

@Controller('transactions')
export class TransactionsController {
  constructor(
    @Inject('TRANSACTION_SERVICE_INTERFACE')
    private readonly transactionsService: TransactionServiceInterface,
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productService: ProductServiceInterface,
    @Inject('PRODUCT_QUANTITY_SERVICE_INTERFACE')
    private readonly productQuantityService: ProductQuantityServiceInterface,
  ) {}

  /**
   *
   * @param productId
   * @param createTransactionDto
   * @param req
   * @description 상품 구매 신청
   */

  @UseGuards(JwtAuthGuard)
  @Post('/:productId')
  async buy(
    @Param('productId', ParseIntPipe) productId: number,
    @Body() createTransactionDto: CreateTransactionDto,
    @Req() req: any,
  ): Promise<void> {
    const buyerId = req.user.userId;
    const sellerId = createTransactionDto.sellerId;

    const createTransacitonInfo = {
      ...createTransactionDto,
      productId,
      buyerId,
    };

    await this.transactionsService.create(createTransacitonInfo, buyerId);
  }

  /**
   * @description 판매자가 상품 판매 완료 승인
   */
  @UseGuards(JwtAuthGuard)
  @Patch('/:transactionId/confirm')
  async confirm(
    @Param('transactionId', ParseIntPipe) transactionId: number,
    @Body() confirmTransactionDto: ConfirmTransactionDto,
    @Req() req: any,
  ): Promise<void> {
    // !! 현재 PENDING인지 확인
    await this.transactionsService.checkIsPending(
      transactionId,
      confirmTransactionDto.buyerId,
    );

    await this.transactionsService.confirm(transactionId);
  }

  /**
   * @description 구매한 용품 조회하기
   */
  @UseGuards(JwtAuthGuard)
  @Get('/')
  async findBuyList(
    @Query() query: GetTransactionsDTO,
    @Req() req: any,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    const userId = req.user.userId;
    console.log('실행!', query);
    return await this.transactionsService.findBuyList(query, userId);
  }

  /**
   * @description 예약중인 용품 조회하기
   */
  @UseGuards(JwtAuthGuard)
  @Get('/reservations')
  async findReservationList(
    @Query() query: GetTransactionsDTO,
    @Req() req: any,
  ): Promise<{ productList: TransactionIncludeProduct[]; count: number }> {
    const userId = req.user.userId;

    return await this.transactionsService.findReservationList(query, userId);
  }

  /**
   * @description 구매확정하기
   */
  @UseGuards(JwtAuthGuard)
  @Patch('/:transactionId/approve')
  async approve(
    @Param('transactionId', ParseIntPipe) transactionId: number,
  ): Promise<void> {
    await this.transactionsService.approve(transactionId);
  }
}
