import {
  Body,
  Controller,
  Get,
  HttpCode,
  Param,
  Post,
  Query,
  Req,
  UseGuards,
} from '@nestjs/common';
import { ProductService } from './product.service';
import {
  ApiBadRequestResponse,
  ApiExtraModels,
  ApiNotFoundResponse,
  ApiTags,
  getSchemaPath,
} from '@nestjs/swagger';
import {
  ProductRegisterRequestDto,
  ProductRegisterResponseDto,
} from './dto/product-register.dto';
import { JwtAccessGuard } from '../auth/guard/jwt-access.guard';
import { User } from '../../common/decorator/user.decorator';
import { IJwtPayload } from '../auth/interface/jwt-payload.interface';
import { ResponseEntity } from '../../common/response.common';
import { ProductSummaryResponseDto } from './dto/product-summary.dto';
import { ApiPagenationRequest } from '../../common/decorator/pagination-request.decorator';
import { PaginationRequestDto } from '../../common/dto/pagination-request.dto';
import { ApiCommonResponse } from '../../common/decorator/common-response.decorator';
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
import { MemberAccessGuard } from '../auth/guard/member-access.guard';
import { ProductDetailWithTransactionResponseDto } from './dto/product-deatil-with-transaction.dto';
import { ProductApproveRequestDto } from './dto/product-approve.dto';

@ApiTags('Product')
@Controller('product')
export class ProductController {
  constructor(private readonly productService: ProductService) {}

  /**
   * 제품 등록
   */
  @Post()
  @ApiExtraModels(ProductRegisterResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(ProductRegisterResponseDto),
  })
  @HttpCode(201)
  @UseGuards(JwtAccessGuard)
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
  @ApiPagenationRequest()
  @ApiExtraModels(ProductSummaryResponseDto)
  @ApiCommonResponse({
    type: 'array',
    items: {
      $ref: getSchemaPath(ProductSummaryResponseDto),
    },
  })
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
  @ApiExtraModels(ProductPurchaseResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(ProductPurchaseResponseDto),
  })
  @ApiNotFoundResponse({ description: '해당하는 제품이 존재하지 않을 경우' })
  @ApiBadRequestResponse({
    description: '해당하는 제품이 판매 가능 상태가 아닐 경우',
  })
  @Post('/purchase')
  @HttpCode(200)
  @UseGuards(JwtAccessGuard)
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
  @ApiExtraModels(ProductDetailWithTransactionResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(ProductDetailWithTransactionResponseDto),
  })
  @ApiNotFoundResponse({ description: '해당하는 제품이 존재하지 않을 경우' })
  @UseGuards(MemberAccessGuard)
  @Get('/:productIdx')
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
  @ApiExtraModels()
  @ApiCommonResponse({}, '판매 승인')
  @ApiNotFoundResponse({ description: '해당하는 제품이 존재하지 않을 경우' })
  @ApiBadRequestResponse({ description: '해당하는 제품의 판매자가 아닐 경우' })
  @UseGuards(JwtAccessGuard)
  @Post('/:productIdx/approve')
  async approveProduct(
    @Body() productApproveDto: ProductApproveRequestDto,
    @User() user: IJwtPayload,
  ): Promise<ResponseEntity<string>> {
    const userIdx = user.idx;

    await this.productService.approveProduct(productApproveDto, userIdx);

    return ResponseEntity.SUCCESS();
  }
}
