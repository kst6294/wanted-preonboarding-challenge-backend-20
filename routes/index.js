import { Router } from "express";
import dbClient from "../db/dbClient.js";
import jwt from "jsonwebtoken";
import verifyToken from "../middlewares/auth.js";

var router = Router();

router.post("/token", async (req, res) => {
    const user = req.body;
    const record = await dbClient("Users").where(user).first();

    if (record) {
        const token = jwt.sign({ username: record.Username }, process.env.JWT_SECRET, {
            expiresIn: "1h",
        });

        res.json({
            code: 200,
            message: "토큰 발급",
            token,
        });
    } else {
        res.status(401).json({ message: "계정 인증 실패" });
    }
});

router.get("/products", async function (req, res, next) {
    const table = "products";

    try {
        const item = await dbClient(table);

        res.setHeader("Content-Type", "application/json");
        res.json(item);
    } catch (_) {
        const error = new Error("No exist.");
        error.status = 404;
        return next(error);
    }
});

router.get("/products/:product_id", async function (req, res, next) {
    const table = "products";
    const { product_id } = req.params;

    try {
        const item = await dbClient(table).where("ID", product_id).first();

        res.setHeader("Content-Type", "application/json");
        res.json(item);
    } catch (_) {
        const error = new Error("No exist.");

        error.status = 404;
        return next(error);
    }
});

router.post("/products", verifyToken, async function (req, res, next) {
    const table = "products";
    const post_dat = req.body;

    try {
        await dbClient(table).insert({
            ReservationState: "Available",
            ...post_dat,
        });
        res.end("successful");
    } catch (_) {
        error.status = 404;
        return next(error);
    }
});

router.post("/products/:product_id/purchase", verifyToken, async function (req, res, next) {
    const table = "products";
    const { product_id } = req.params;
    const buyer = req.decoded.username;

    try {
        const item = await dbClient(table).where("ID", product_id).first();

        if (item.ReservationState == "Available") {
            await dbClient(table).where("ID", product_id).update({
                Buyer: buyer,
                ReservationState: "Reserved",
            });

            res.end("successful");
        } else {
            const error = new Error("판매 중이지 않습니다.");
            error.status = 404;
            return next(error);
        }
    } catch (_) {
        const error = new Error("No exist.");
        error.status = 404;
        return next(error);
    }
});

router.post("/products/:product_id/sales_approval", verifyToken, async function (req, res, next) {
    const table = "products";
    const { product_id } = req.params;
    const seller = req.decoded.username;

    try {
        const item = await dbClient(table).where({ ID: product_id, Seller: seller }).first();

        if (item && item.ReservationState == "Reserved") {
            await dbClient(table).where("ID", product_id).update({
                ReservationState: "SoldOut",
            });

            res.end("successful");
        } else {
            const error = new Error("거래 체결 불이행 " + item.ReservationState);
            error.status = 404;
            return next(error);
        }
    } catch (_) {
        const error = new Error("No exist.");
        error.status = 404;
        return next(error);
    }
});

export default router;
