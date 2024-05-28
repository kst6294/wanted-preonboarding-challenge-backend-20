import { Router } from "express";
import { issue_token } from "../services/tokenService.js";

var router = Router();

// 토큰 발급
// 회원만
router.post("/token", async (req, res) => {
    const user = req.body;
    try {
        const token = await issue_token(user);

        res.status(200).json({
            message: "토큰 발급",
            token,
        });
    } catch (_) {
        res.status(401).json({ message: "계정 인증 실패" });
    }
});

export default router;
