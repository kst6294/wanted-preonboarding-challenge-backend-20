import {
  BadRequestException,
  ConflictException,
  Injectable,
  Logger,
} from '@nestjs/common';
import { PrismaService } from '../../common/prisma/prisma.service';
import { IUser } from './interface/user.interface';
import { SignupRequestDto } from './dto/signup.dto';
import * as bcrypt from 'bcrypt';
import { LoginRequestDto } from './dto/login.dto';
import { IJwtPayload } from '../auth/interface/jwt-payload.interface';
import { PaginationRequestDto } from '../../common/dto/pagination-request.dto';
import { IProduct } from '../product/interface/product.interface';

@Injectable()
export class UserService {
  private readonly logger = new Logger(UserService.name);
  constructor(private readonly prismaService: PrismaService) {}

  /**
   * 유저 생성
   * @param dto SignupRequestDto
   * @returns SignupResponseDto
   */
  async createUser(dto: SignupRequestDto): Promise<IUser.ISignupResponse> {
    // 이메일 중복 체크
    await this.checkDuplicateEmail(dto.email);

    // bcrypt 암호화
    const hashedPassword = await bcrypt.hash(dto.password, 10);

    // 유저 생성
    const createdUser = await this.prismaService.user.create({
      data: {
        ...dto,
        password: hashedPassword,
      },
      select: {
        idx: true,
      },
    });

    return {
      idx: createdUser.idx,
    };
  }

  /**
   * 로그인
   */
  async login(dto: LoginRequestDto): Promise<IJwtPayload> {
    const checkUser = await this.prismaService.user.findFirst({
      select: {
        idx: true,
        email: true,
        name: true,
        password: true,
      },
      where: {
        email: dto.email,
        deletedAt: null,
      },
    });

    if (!checkUser) {
      this.logger.log('아이디가 일치하지 않습니다');
      throw new BadRequestException('아이디 또는 비밀번호가 일치하지 않습니다');
    }

    const comparePassword = await bcrypt.compare(
      dto.password,
      checkUser.password,
    );

    if (!comparePassword) {
      this.logger.log('비밀번호가 일치하지 않습니다');
      throw new BadRequestException('아이디 또는 비밀번호가 일치하지 않습니다');
    }

    return {
      idx: checkUser.idx,
      name: checkUser.name,
    };
  }

  /**
   * 이메일 중복 체크
   * @param email 검사 할 이메일
   * @returns 성공 여부
   */
  async checkDuplicateEmail(email: IUser['email']): Promise<boolean> {
    const findExistEmail = await this.prismaService.user.findFirst({
      select: {
        idx: true,
      },
      where: {
        email,
        deletedAt: null,
      },
    });

    if (findExistEmail) {
      throw new ConflictException('이미 존재하는 이메일입니다');
    }

    return true;
  }

  /**
   * 내가 구매한 제품 리스트 조회
   * @param userIdx 로그인 한 사용자 인덱스
   */
  async getMyPurchasedProduct(
    userIdx: IUser['idx'],
    pageNation: PaginationRequestDto,
  ): Promise<IUser.IPurchasedProductResponse> {
    const result = await this.prismaService.transaction.findMany({
      include: {
        Product: true,
      },
      where: {
        userIdx,
        status: 'Confirm',
      },
      skip: pageNation.getOffset(),
      take: pageNation.limit,
    });

    return {
      purchagedProduct: result.map((item) => ({
        idx: item.idx,
        name: item.Product.name,
        price: item.price,
        productIdx: item.productIdx,
        transactionStatus: item.status,
        createdAt: item.createdAt,
        userIdx: item.userIdx,
      })),
    };
  }

  /**
   * 내가 예약중인 제품 리스트 조회
   */
  async getMyReservedProduct(
    userIdx: IUser['idx'],
    page: PaginationRequestDto,
  ): Promise<IProduct.ISummaryResponse[]> {
    const result = await this.prismaService.product.findMany({
      select: {
        idx: true,
        name: true,
        price: true,
        status: true,
        createdAt: true,
      },
      where: {
        deletedAt: null,
        Transaction: {
          some: {
            userIdx,
            status: 'RESERVED',
          },
        },
      },
      skip: page.getOffset(),
      take: page.limit,
    });

    return result.map((item) => ({
      idx: item.idx,
      name: item.name,
      price: item.price,
      status: item.status,
      createdAt: item.createdAt,
    }));
  }

  /**
   * 내가 판매 완료 한 제품 리스트 조회
   */
  async getMySellingProduct(
    userIdx: IUser['idx'],
    page: PaginationRequestDto,
  ): Promise<IProduct.ISummaryResponse[]> {
    const result = await this.prismaService.product.findMany({
      select: {
        idx: true,
        name: true,
        price: true,
        status: true,
        createdAt: true,
      },
      where: {
        userIdx,
        deletedAt: null,
        Transaction: {
          some: {
            status: 'Confirm',
          },
        },
      },
    });

    return result.map((item) => ({
      idx: item.idx,
      name: item.name,
      price: item.price,
      status: item.status,
      createdAt: item.createdAt,
    }));
  }
}
