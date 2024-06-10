import {
  Controller,
  Post,
  Body,
  Param,
  UseGuards,
  Req,
  Inject,
  ParseIntPipe,
} from '@nestjs/common';
import { CreateTransactionDto } from './dto/create-transaction.dto';
import { JwtAuthGuard } from 'src/auth/jwt-auth.guard';
import { TransactionServiceInterface } from './interfaces/transaction.service.interface';
import { ProductServiceInterface } from 'src/products/interfaces/product.service.interface';

@Controller('transactions')
export class TransactionsController {
  constructor(
    @Inject('TRANSACTION_SERVICE_INTERFACE')
    private readonly transactionsService: TransactionServiceInterface,
    @Inject('PRODUCT_SERVICE_INTERFACE')
    private readonly productService: ProductServiceInterface,
  ) {}

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
}
