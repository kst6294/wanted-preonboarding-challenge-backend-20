import { IUser } from '../../user/interface/user.interface';

export interface IJwtPayload extends Pick<IUser, 'idx' | 'name'> {}
