import {
  BadRequestException,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import { PrismaService } from '../../common/prisma/prisma.service';
import { ProductRegisterRequestDto } from './dto/product-register.dto';
import { IUser } from '../user/interface/user.interface';
import { IProduct } from './interface/product.interface';
import { PaginationRequestDto } from '../../common/dto/pagination-request.dto';
import { ProductPurchaseRequestDto } from './dto/product-purchage.dto';
import { ITransaction } from '../transaction/interface/transaction.interface';
import { ProductDetailWithTransactionResponseDto } from './dto/product-deatil-with-transaction.dto';

@Injectable()
export class ProductService {
  constructor(private readonly prismaService: PrismaService) {}

  /**
   * 제품 등록
   * @param userIdx 판매자 인덱스
   * @param dto ProductRegisterRequestDto
   * @returns 등록된 제품 인덱스
   */
  async registerProduct(
    userIdx: IUser['idx'],
    dto: ProductRegisterRequestDto,
  ): Promise<IProduct.IRegisterResponse> {
    const createdProductIdx = await this.prismaService.product.create({
      data: {
        userIdx,
        name: dto.name,
        price: dto.price,
      },
      select: {
        idx: true,
      },
    });

    return {
      idx: createdProductIdx.idx,
    };
  }

  /**
   * 제품 목록 조회
   */
  async getProductList(
    pageNation: PaginationRequestDto,
  ): Promise<IProduct.ISummaryResponse[]> {
    const summaryProductList = await this.prismaService.product.findMany({
      select: {
        idx: true,
        name: true,
        price: true,
        status: true,
        createdAt: true,
      },
      where: {
        deletedAt: null,
      },
      skip: pageNation.getOffset(),
      take: pageNation.limit,
    });

    return summaryProductList.map((product) => ({
      idx: product.idx,
      name: product.name,
      price: product.price,
      status: product.status,
      createdAt: product.createdAt,
    }));
  }

  /**
   * 제품 구매 신청
   */
  async purchaseProduct(
    userIdx: IUser['idx'],
    dto: ProductPurchaseRequestDto,
  ): Promise<ITransaction['idx']> {
    // 해당하는 제품이 존재하는지 확인
    const findExistProductResult = await this.prismaService.product.findUnique({
      select: {
        idx: true,
        price: true,
        status: true,
        userIdx: true,
      },
      where: {
        idx: dto.productIdx,
        deletedAt: null,
      },
    });

    // 해당하는 제품이 존재하지 않을 경우
    if (!findExistProductResult) {
      throw new NotFoundException('해당하는 제품이 존재하지 않습니다');
    }

    // 해당하는 제품이 본인이 올린 제품인 경우
    if (findExistProductResult.userIdx === userIdx) {
      throw new BadRequestException('본인이 올린 제품입니다');
    }

    // 해당하는 제품이 판매 가능 상태가 아닐 경우
    if (findExistProductResult.status !== 'AVAILABLE') {
      throw new BadRequestException('판매 가능한 상태가 아닙니다');
    }

    // @TODO 이미 거래 내역이 존재하는 경우

    // 거래 트랜잭션 시작
    const transactionTx = await this.prismaService.$transaction(async (tx) => {
      // 제품 상태 예약중으로 변경
      await tx.product.update({
        data: {
          status: 'RESERVED',
        },
        where: {
          idx: findExistProductResult.idx,
        },
      });

      // 거래 내역 생성
      const transactionResult = await tx.transaction.create({
        data: {
          userIdx,
          productIdx: findExistProductResult.idx,
          price: findExistProductResult.price,
          status: 'RESERVED',
        },
        select: {
          idx: true,
        },
      });

      return transactionResult;
    });

    return transactionTx.idx;
  }

  /**
   * 제품 상세 조회
   * @param productIdx 제품 인덱스
   * @returns 제품 상세 정보
   */
  async getProductDetail(
    productIdx: IProduct['idx'],
  ): Promise<IProduct.IProductDetailResopnse> {
    const findProductResult = await this.prismaService.product.findUnique({
      where: {
        idx: productIdx,
        deletedAt: null,
      },
    });

    if (!findProductResult) {
      throw new NotFoundException('해당하는 제품이 존재하지 않습니다');
    }

    return {
      ...findProductResult,
      sellerIdx: findProductResult.userIdx,
    };
  }

  /**
   * 제품 상세 조회 및 거래 내역 조회
   * @param userIdx 사용자 인덱스
   * @param productIdx 제품 인덱스
   * @returns IProductDetailWithTransactionResponse
   */
  async getProductDetailWithTransaction(
    userIdx: IUser['idx'],
    productIdx: IProduct['idx'],
  ): Promise<IProduct.IProductDetailWithTransactionResponse> {
    const result = await this.prismaService.product.findFirst({
      select: {
        idx: true,
        name: true,
        userIdx: true,
        updatedAt: true,
        price: true,
        status: true,
        createdAt: true,
      },
      where: {
        idx: productIdx,
        deletedAt: null,
      },
    });

    if (!result) {
      throw new NotFoundException('해당하는 제품이 존재하지 않습니다');
    }

    const transactionHistory =
      await this.prismaService.transaction.findUniqueOrThrow({
        select: {
          idx: true,
          userIdx: true,
          productIdx: true,
          price: true,
          status: true,
          createdAt: true,
        },
        where: {
          userIdx_productIdx: {
            userIdx,
            productIdx: result.idx,
          },
        },
      });

    return {
      idx: result.idx,
      name: result.name,
      sellerIdx: result.userIdx,
      price: result.price,
      status: result.status,
      createdAt: result.createdAt,
      updatedAt: result.updatedAt,
      transactionIdx: transactionHistory.idx,
      buyerIdx: transactionHistory.userIdx,
      transactionHistory: {
        idx: transactionHistory.idx,
        userIdx: transactionHistory.userIdx,
        productIdx: transactionHistory.productIdx,
        price: transactionHistory.price,
        name: result.name,
        createdAt: transactionHistory.createdAt,
        transactionStatus: transactionHistory.status,
      },
    };
  }

  /**
   * 해당 사용자가 조회할 제품의 판매자인지 조회
   * @param userIdx 사용자 인덱스
   * @param productIdx 제품 인덱스
   * @returns 판매자일 경우 true, 아닐 경우 false
   */
  async isSeller(
    userIdx: IUser['idx'],
    productIdx: IProduct['idx'],
  ): Promise<boolean> {
    const checkSeller = await this.prismaService.product.findUnique({
      select: {
        userIdx: true,
      },
      where: {
        idx: productIdx,
        userIdx,
        deletedAt: null,
      },
    });

    if (!checkSeller) {
      return false;
    }

    return true;
  }

  /**
   * 해당 사용자가 조회할 제품의 구매자인지 조회
   * @param userIdx 사용자 인덱스
   * @param productIdx 제품 인덱스
   * @returns 판매자일 경우 true, 아닐 경우 false
   */
  async isPurchaser(
    userIdx: IUser['idx'],
    productIdx: IProduct['idx'],
  ): Promise<boolean> {
    const checkPurchaser = await this.prismaService.transaction.findFirst({
      where: {
        userIdx,
        productIdx,
        status: 'Confirm',
      },
    });

    if (!checkPurchaser) {
      return false;
    }

    return true;
  }

  /**
   * 제품 판매 승인
   */
  async approveProduct(
    dto: IProduct.IProductApproveRequest,
    userIdx: IUser['idx'],
  ): Promise<void> {
    // 1. 해당하는 제품이 존재하는지 확인
    const findProductResult = await this.prismaService.product.findUnique({
      select: {
        idx: true,
      },
      where: {
        idx: dto.idx,
        deletedAt: null,
      },
    });

    if (!findProductResult?.idx) {
      throw new NotFoundException('해당하는 제품이 존재하지 않습니다');
    }

    // 2. 해당하는 제품의 판매자인지 확인
    const isSeller = await this.isSeller(userIdx, dto.idx);

    if (!isSeller) {
      throw new BadRequestException('해당 제품의 판매자가 아닙니다');
    }

    // 3. 제품 상태 변경 & 거래 내역 상태 변경 (거래 완료)
    await this.prismaService.$transaction(async (tx) => {
      await tx.product.update({
        data: {
          status: 'SOLD',
        },
        where: {
          idx: dto.idx,
          deletedAt: null,
        },
      });

      await tx.transaction.update({
        data: {
          status: 'Confirm',
        },
        where: {
          idx: dto.idx,
        },
      });
    });

    return;
  }
}
