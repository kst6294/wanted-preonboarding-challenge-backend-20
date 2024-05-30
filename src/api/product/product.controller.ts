import {
  Body,
  Controller,
  Get,
  HttpCode,
  Param,
  Post,
  Query,
} from '@nestjs/common';
import { ProductService } from './product.service';
import { ApiExtraModels, ApiTags } from '@nestjs/swagger';
import {
  ProductRegisterRequestDto,
  ProductRegisterResponseDto,
} from './dto/product-register.dto';
import { User } from '../../common/decorator/user.decorator';
import { IJwtPayload } from '../auth/interface/jwt-payload.interface';
import { ResponseEntity } from '../../common/response.common';
import { ProductSummaryResponseDto } from './dto/product-summary.dto';
import { PaginationRequestDto } from '../../common/dto/pagination-request.dto';
import { OkResponse } from '../../common/decorator/common-response.decorator';
import {
  ProductPurchaseRequestDto,
  ProductPurchaseResponseDto,
} from './dto/product-purchage.dto';
import {
  ITransaction,
  ProductDetailOrProductDetailWithTransaction,
} from '../transaction/interface/transaction.interface';
import {
  ProductDetailResponseDto,
  ProductDetailResquestDto,
} from './dto/product-detail.dto';
import { ProductDetailWithTransactionResponseDto } from './dto/product-deatil-with-transaction.dto';
import { ProductApproveRequestDto } from './dto/product-approve.dto';
import { LoginAuth } from '../../common/decorator/login-auth.decorator';
import { Exception } from '../../common/decorator/exception.decorator';

@ApiTags('Product')
@Controller('product')
export class ProductController {
  constructor(private readonly productService: ProductService) {}

  /**
   * 제품 등록
   */
  @Post()
  @HttpCode(201)
  @LoginAuth()
  @OkResponse(ProductRegisterResponseDto)
  async registerProduct(
    @Body() productDto: ProductRegisterRequestDto,
    @User() user: IJwtPayload,
  ): Promise<ResponseEntity<ProductRegisterResponseDto>> {
    const userIdx = user.idx;

    const createdProductResult = await this.productService.registerProduct(
      userIdx,
      productDto,
    );

    return ResponseEntity.SUCCESS_WITH(
      ProductRegisterResponseDto.of(createdProductResult),
    );
  }

  /**
   * 제품 목록 보기
   */
  @Get('/list')
  @OkResponse(ProductSummaryResponseDto)
  async getProductList(
    @Query() paginateQuery: PaginationRequestDto,
  ): Promise<ResponseEntity<ProductSummaryResponseDto[]>> {
    const productList = await this.productService.getProductList(paginateQuery);

    return ResponseEntity.SUCCESS_WITH(
      ProductSummaryResponseDto.of(productList),
    );
  }

  /**
   * 제품 구매 신청
   */
  @Post('/purchase')
  @HttpCode(200)
  @LoginAuth()
  @OkResponse(ProductPurchaseResponseDto)
  @Exception(400, '해당하는 제품이 판매 가능 상태가 아닙니다')
  @Exception(404, '해당하는 제품이 존재하지 않습니다')
  async purchaseProduct(
    @Body() productDto: ProductPurchaseRequestDto,
    @User() user: IJwtPayload,
  ): Promise<ResponseEntity<ITransaction['idx']>> {
    const userIdx = user.idx;

    const transactionIdx = await this.productService.purchaseProduct(
      userIdx,
      productDto,
    );

    return ResponseEntity.SUCCESS_WITH(transactionIdx);
  }

  /**
   * 제품 상세 보기
   */
  @Get('/:productIdx')
  @LoginAuth()
  @Exception(404, '해당하는 제품이 존재하지 않습니다')
  @OkResponse(ProductDetailResponseDto)
  @OkResponse(ProductDetailWithTransactionResponseDto)
  async getProductDetail(
    @Param() productDetailDto: ProductDetailResquestDto,
    @User() user?: IJwtPayload,
  ): Promise<ResponseEntity<ProductDetailOrProductDetailWithTransaction>> {
    const userIdx = user?.idx;
    console.log(userIdx);
    if (userIdx) {
      const isSeller = await this.productService.isSeller(
        userIdx,
        productDetailDto.productIdx,
      );

      const isPurchaser = await this.productService.isPurchaser(
        userIdx,
        productDetailDto.productIdx,
      );

      //  제품을 구매하거나 판매한 사람이 상세보기를 하면 해당 제품에 대한 거래 상황을 확인 가능
      if (isSeller || isPurchaser) {
        const result =
          await this.productService.getProductDetailWithTransaction(
            userIdx,
            productDetailDto.productIdx,
          );

        return ResponseEntity.SUCCESS_WITH(
          ProductDetailWithTransactionResponseDto.of(result),
        );
      }
    }

    const result = await this.productService.getProductDetail(
      productDetailDto.productIdx,
    );

    return ResponseEntity.SUCCESS_WITH(ProductDetailResponseDto.of(result));
  }

  /**
   * 판매 승인
   */
  @Post('/:productIdx/approve')
  @LoginAuth()
  @ApiExtraModels()
  @Exception(400, '해당하는 제품의 판매자가 아닙니다')
  @Exception(404, '해당하는 제품이 존재하지 않습니다')
  async approveProduct(
    @Body() productApproveDto: ProductApproveRequestDto,
    @User() user: IJwtPayload,
  ): Promise<ResponseEntity<string>> {
    const userIdx = user.idx;

    await this.productService.approveProduct(productApproveDto, userIdx);

    return ResponseEntity.SUCCESS();
  }
}
