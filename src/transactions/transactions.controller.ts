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

@Controller('transactions')
export class TransactionsController {
  constructor(
    @Inject('TRANSACTION_SERVICE_INTERFACE')
    private readonly transactionsService: TransactionServiceInterface,
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productService: ProductServiceInterface,
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
    // !! 이미 구매한 상품인지
    await this.transactionsService.checkAlreadyBought(
      productId,
      buyerId,
      sellerId,
    );
    // !! 품절 상태인지 확인
    await this.productService.checkSoldOut(productId);

    return await this.transactionsService.create(createTransacitonInfo);
  }

  /**
   * @description 판매자가 상품 구매 확정
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
  async findBuyList(@Query() query: GetTransactionsDTO, @Req() req: any) {
    const userId = req.user.userId;

    return await this.transactionsService.findBuyList(query, userId);
  }

  /**
   * @description 예약중인 용품 조회하기
   */
}
