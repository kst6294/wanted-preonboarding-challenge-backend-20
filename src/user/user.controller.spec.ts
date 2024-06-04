import { Test, TestingModule } from '@nestjs/testing';
import { UserController } from './user.controller';
import { UserService } from './user.service';
import { MockUserRepository } from './user.service.spec';
import { UserDto } from './dto/user.dto';
import { Response } from 'express';

class MockUserService {
  signUp = jest.fn();
  signIn = jest.fn();
}

jest.mock('bcrypt', () => ({
  hash: jest.fn(),
  compare: jest.fn(),
}));

jest.mock('jsonwebtoken', () => ({
  sign: jest.fn(),
}));

describe('UserController', () => {
  let controller: UserController;
  let service: UserService;

  const mockUser = new MockUserRepository().mockUser;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [UserController],
      providers: [{ provide: UserService, useClass: MockUserService }],
    }).compile();

    controller = module.get<UserController>(UserController);
    service = module.get<UserService>(UserService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  describe('signUp', () => {
    it('올바른 parameters로 user를 생성한다.', async () => {
      const userDto: UserDto = { email: 'test@test.com', password: 'password' };
      const mockResponse = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
      } as any as Response;

      await controller.signUp(userDto, mockResponse);

      expect(service.signUp).toHaveBeenCalledWith(userDto);
      expect(mockResponse.status).toHaveBeenCalledWith(200);
      expect(mockResponse.json).toHaveBeenCalledWith({
        message: 'SignUp success',
      });
    });
  });

  describe('signIn', () => {
    it('올바른 parameters로 호출되고 cookie가 설정된다.', async () => {
      const userDto: UserDto = { email: 'test@test.com', password: 'password' };
      const mockToken = 'mockToken';
      const mockResponse = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn(),
        cookie: jest.fn(),
      } as any as Response;

      (service.signIn as jest.Mock).mockResolvedValue(mockToken);

      await controller.signIn(userDto, mockResponse);

      expect(service.signIn).toHaveBeenCalledWith(userDto);
      expect(mockResponse.cookie).toHaveBeenCalledWith(
        'authorization',
        `Bearer ${mockToken}`,
      );
      expect(mockResponse.status).toHaveBeenCalledWith(200);
      expect(mockResponse.json).toHaveBeenCalledWith({
        message: 'SignIn success',
      });
    });
  });
});
