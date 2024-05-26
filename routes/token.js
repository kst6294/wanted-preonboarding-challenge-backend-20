import { Router } from "express";
import jwt from "jsonwebtoken";
import dbClient from "../db/dbClient.js";

var router = Router();

// 토큰 발급
// 회원만
router.post("/token", async (req, res) => {
    const user = req.body;
    const record = await dbClient("Users").where(user).first();

    if (record) {
        const token = jwt.sign({ username: record.Username }, process.env.JWT_SECRET, {
            expiresIn: "1h",
        });

        res.status(200).json({
            message: "토큰 발급",
            token,
        });
    } else {
        res.status(401).json({ message: "계정 인증 실패" });
    }
});

export default router;
