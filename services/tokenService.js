import jwt from "jsonwebtoken";
import userDao from "../model/userDao.js";

const { get_user } = userDao;

export async function issue_token(user) {
    const record = await get_user(user);

    if (record) {
        const token = jwt.sign({ username: record.username, id: record.id }, process.env.JWT_SECRET, {
            expiresIn: "1h",
        });
        return token;
    } else {
        throw new Error();
    }
}
