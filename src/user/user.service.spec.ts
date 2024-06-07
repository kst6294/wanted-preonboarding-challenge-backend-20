import { Test, TestingModule } from '@nestjs/testing';
import { UserService } from './user.service';
import { User } from './entity/user.entity';
import { UserRepository } from './user.repository';
import * as bcrypt from 'bcrypt';
import * as jwt from 'jsonwebtoken';

export class MockUserRepository {
  readonly mockUser: User = {
    userId: 1,
    name: '홍길동',
    email: 'test@test.com',
    password: 'testpassword',
    product: [],
    order: [],
  };
  validateUser = jest.fn();
  createUser = jest.fn();
  findUserByPk = jest.fn();
}

jest.mock('bcrypt', () => ({
  hash: jest.fn(),
  compare: jest.fn(),
}));

jest.mock('jsonwebtoken', () => ({
  sign: jest.fn(),
}));

describe('UserService', () => {
  let service: UserService;
  let repository: UserRepository;

  const mockUser = new MockUserRepository().mockUser;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        UserService,
        { provide: UserRepository, useClass: MockUserRepository },
      ],
    }).compile();

    service = module.get<UserService>(UserService);
    repository = module.get<UserRepository>(UserRepository);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('signUp', () => {
    it('exUser가 없을 경우 newUser를 생성', async () => {
      const newUser = {
        email: 'test@test.com',
        password: 'password',
        name: '홍길동',
      };
      const mockHashedPassword = 'hashedpassword';
      (repository.validateUser as jest.Mock).mockResolvedValue(null);
      (repository.createUser as jest.Mock).mockResolvedValue({
        ...newUser,
        userId: 1,
      });
      (bcrypt.hash as jest.Mock).mockResolvedValue(mockHashedPassword);

      await service.signUp(newUser);

      expect(repository.validateUser).toHaveBeenCalledWith(newUser.email);
      expect(bcrypt.hash).toHaveBeenCalledWith(newUser.password, 10);
      expect(repository.createUser).toHaveBeenCalledWith(
        newUser.email,
        mockHashedPassword,
        newUser.name,
      );
    });
  });

  describe('signIn', () => {
    it('유효한 user 정보를 제공하면 token을 return', async () => {
      const userDto = { email: 'test@test.com', password: 'password' };
      const mockToken = 'mockToken';

      (repository.validateUser as jest.Mock).mockResolvedValue(mockUser);
      (bcrypt.compare as jest.Mock).mockResolvedValue(true);
      (jwt.sign as jest.Mock).mockReturnValue(mockToken);

      const result = await service.signIn(userDto);

      expect(repository.validateUser).toHaveBeenCalledWith(userDto.email);
      expect(bcrypt.compare).toHaveBeenCalledWith(
        userDto.password,
        mockUser.password,
      );
      expect(jwt.sign).toHaveBeenCalledWith(
        { userId: mockUser.userId },
        process.env.JWT_SECRET,
        { expiresIn: '1h' },
      );
      expect(result).toBe(mockToken);
    });
  });
});
