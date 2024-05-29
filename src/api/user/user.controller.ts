import {
  Body,
  Controller,
  Get,
  HttpCode,
  Post,
  Query,
  Res,
  UseGuards,
} from '@nestjs/common';
import { UserService } from './user.service';
import {
  ApiBadRequestResponse,
  ApiConflictResponse,
  ApiExtraModels,
  ApiOkResponse,
  ApiOperation,
  ApiResponse,
  ApiTags,
  getSchemaPath,
} from '@nestjs/swagger';
import { SignupRequestDto, SignupResponseDto } from './dto/signup.dto';
import { ResponseEntity } from '../../common/response.common';
import { LoginRequestDto } from './dto/login.dto';
import { AuthService } from '../auth/auth.service';
import { Response } from 'express';
import { ApiCommonResponse } from '../../common/decorator/common-response.decorator';
import { JwtAccessGuard } from '../auth/guard/jwt-access.guard';
import { ApiPagenationRequest } from '../../common/decorator/pagination-request.decorator';
import { User } from '../../common/decorator/user.decorator';
import { IJwtPayload } from '../auth/interface/jwt-payload.interface';
import { MyPurchasedProductResponseDto } from './dto/purchased-product.dto';
import { PaginationRequestDto } from '../../common/dto/pagination-request.dto';
import { ReservedProductResponseDto } from './dto/reserved-product.dto';
import { SoldProductListResponseDto } from './dto/selling-product.dto';

@ApiTags('User')
@Controller('user')
export class UserController {
  constructor(
    private readonly userService: UserService,
    private readonly authService: AuthService,
  ) {}

  /**
   * 회원가입
   */
  @ApiExtraModels(SignupResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(SignupResponseDto),
  })
  @ApiConflictResponse({
    description: '이미 존재하는 이메일입니다',
  })
  @HttpCode(201)
  @Post('signup')
  async signup(
    @Body() signupDto: SignupRequestDto,
  ): Promise<ResponseEntity<SignupResponseDto>> {
    const createdUserIdx = await this.userService.createUser(signupDto);

    return ResponseEntity.SUCCESS_WITH(SignupResponseDto.of(createdUserIdx));
  }

  /**
   * 로그인
   */
  @HttpCode(200)
  @ApiOkResponse({
    headers: {
      accessToken: {
        schema: {
          type: 'string',
          example:
            'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHgiOjMsIm5hbWUiOiLsnKDrj5nshKAiLCJpYXQiOjE3MTY4OTg1OTIsImV4cCI6MTcxNjk4NDk5Mn0.OIJJx8gvXzrCSxFTjOK0WybWM_KP4WzsZ_MKrxKahvQ;',
        },
      },
    },
  })
  @ApiBadRequestResponse({
    description: '아이디 또는 비밀번호가 일치하지 않습니다',
  })
  @Post('login')
  async login(
    @Body() loginDto: LoginRequestDto,
    @Res({ passthrough: false }) res: Response,
  ) {
    // 로그인
    const userInfo = await this.userService.login(loginDto);

    // accessToken 생성
    const accessToken = await this.authService.generateAccessToken(userInfo);

    // accessToken
    res.cookie('accessToken', accessToken, {
      httpOnly: true,
    });

    return res.end();
  }

  /**
   * 내가 구매한 제품 목록
   */
  @ApiPagenationRequest()
  @ApiExtraModels(MyPurchasedProductResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(MyPurchasedProductResponseDto),
  })
  @Get('purchased/list')
  @UseGuards(JwtAccessGuard)
  async getMyPurchasedProduct(
    @User() user: IJwtPayload,
    @Query() paginateQuery: PaginationRequestDto,
  ): Promise<ResponseEntity<MyPurchasedProductResponseDto>> {
    const userIdx = user.idx;
    const result = await this.userService.getMyPurchasedProduct(
      userIdx,
      paginateQuery,
    );

    return ResponseEntity.SUCCESS_WITH(
      MyPurchasedProductResponseDto.of(result.purchagedProduct),
    );
  }

  /**
   * 내가 예약중인 제품 목록
   */
  @ApiPagenationRequest()
  @ApiExtraModels(ReservedProductResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(ReservedProductResponseDto),
  })
  @Get('reserved/list')
  @UseGuards(JwtAccessGuard)
  async getMyReservedProduct(
    @Query() paginateQuery: PaginationRequestDto,
    @User() user: IJwtPayload,
  ): Promise<ResponseEntity<ReservedProductResponseDto>> {
    const userIdx = user.idx;
    const result = await this.userService.getMyReservedProduct(
      userIdx,
      paginateQuery,
    );

    return ResponseEntity.SUCCESS_WITH(ReservedProductResponseDto.of(result));
  }

  /**
   * 내가 판매중인 제품 목록
   */
  @ApiPagenationRequest()
  @ApiExtraModels(SoldProductListResponseDto)
  @ApiCommonResponse({
    $ref: getSchemaPath(SoldProductListResponseDto),
  })
  @Get('sold/list')
  @UseGuards(JwtAccessGuard)
  async getMySellingProduct(
    @Query() paginateQuery: PaginationRequestDto,
    @User() user: IJwtPayload,
  ): Promise<ResponseEntity<SoldProductListResponseDto>> {
    const userIdx = user.idx;
    const result = await this.userService.getMySellingProduct(
      userIdx,
      paginateQuery,
    );

    return ResponseEntity.SUCCESS_WITH(SoldProductListResponseDto.of(result));
  }
}
