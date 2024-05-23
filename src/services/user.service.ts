import HttpError from "../errors/HttpError";
import IUser from "../interfaces/IUser.dto";
import UserRepository from "../repositories/user.repository";

export default class UserService {
  repository = new UserRepository();

  async signUp(dto: IUser) {
    const [row] = await this.repository.selectUser(dto.name);

    if (row) {
      const message = "동일한 name 의 회원이 존재합니다.";
      throw new HttpError(409, message);
    }

    await this.repository.insertUser(dto);
  }

  async logIn(dto: IUser) {
    const [row] = await this.repository.selectUser(dto.name);

    if (!row) {
      const message = "요청하신 name 의 회원이 존재하지 않습니다.";
      throw new HttpError(404, message);
    }

    if (row.password !== dto.password) {
      const message = "요청하신 password 가 일치하지 않습니다.";
      throw new HttpError(400, message);
    }

    return row.userID;
  }
}
