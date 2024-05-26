import { Router } from "express";
import dbClient from "../db/dbClient.js";
import verifyToken from "../middlewares/auth.js";

var router = Router();

// 목록조회
// 비회원 가능
router.get("/", async function (req, res, next) {
    const table = "products";

    try {
        const item = await dbClient(table);

        res.status(200).json(item);
    } catch (_) {
        const error = new Error("데이터베이스 오류");
        error.status = 500;
        return next(error);
    }
});

// 상세조회
// 비회원 가능
router.get("/:product_id", async function (req, res, next) {
    const table = "products";
    const { product_id } = req.params;

    try {
        const item = await dbClient(table).where("ID", product_id).first();

        res.status(200).json(item);
    } catch (_) {
        const error = new Error("제품 없음");
        error.status = 404;
        return next(error);
    }
});

// 제품 등록
// 회원만 가능
router.post("/", verifyToken, async function (req, res, next) {
    const table = "products";
    const seller = req.decoded.username;
    const post_dat = req.body;

    try {
        const [id] = await dbClient(table).insert({
            ReservationState: "Available",
            Seller: seller,
            ...post_dat,
        });

        res.status(201).json({ id });
    } catch (_) {
        const error = new Error("추가 실패");
        error.status = 500;
        return next(error);
    }
});

// 제품 구매
// 판매중 상품만 가능
router.post("/:product_id/purchase", verifyToken, async function (req, res, next) {
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
            const updatedRecord = await dbClient(table).where("ID", product_id).first();

            res.status(201).json(updatedRecord);
        } else {
            const error = new Error("판매 중이지 않음");
            error.status = 409;
            return next(error);
        }
    } catch (_) {
        const error = new Error("제품 없음");
        error.status = 404;
        return next(error);
    }
});

// 판매 승인
// 본인 상품만 가능
// 예약중 상품만 가능
router.post("/:product_id/sales_approval", verifyToken, async function (req, res, next) {
    const table = "products";
    const { product_id } = req.params;
    const seller = req.decoded.username;

    try {
        const item = await dbClient(table).where({ ID: product_id, Seller: seller }).first();

        if (item.ReservationState == "Reserved") {
            await dbClient(table).where("ID", product_id).update({
                ReservationState: "SoldOut",
            });
            const updatedRecord = await dbClient(table).where("ID", product_id).first();

            res.status(201).json(updatedRecord);
        } else {
            const error = new Error("거래 체결 불이행 " + item.ReservationState);
            error.status = 409;
            return next(error);
        }
    } catch (_) {
        const error = new Error("제품 없음");
        error.status = 404;
        return next(error);
    }
});

export default router;
