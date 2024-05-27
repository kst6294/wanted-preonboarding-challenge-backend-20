import { Router } from "express";
import dbClient from "../db/dbClient.js";
import verifyToken from "../middlewares/auth.js";

var router = Router();

// 목록조회
// 비회원 가능
router.get("/", async function (req, res, next) {
    try {
        const item = await dbClient("Products");

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
    const { product_id } = req.params;

    try {
        const item = await dbClient("Products").where({ product_id }).first();

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
    const seller_id = req.decoded.id;
    const post_data = req.body;

    try {
        const [product_id] = await dbClient("Products").insert({
            status: "Available",
            seller_id,
            ...post_data,
        });

        res.status(201).json({ product_id });
    } catch (_) {
        const error = new Error("추가 실패");
        error.status = 500;
        return next(error);
    }
});

// 제품 구매
// 판매중 상품만 가능
router.post("/:product_id/purchase", verifyToken, async function (req, res, next) {
    const { product_id } = req.params;
    const buyer_id = req.decoded.id;

    try {
        const item = await dbClient("Products").where({ product_id }).first();

        if (item.status == "Available") {
            // 주문서 작성
            await dbClient("Orders").insert({
                buyer_id,
                product_id,
                price: item.price,
                status: "Reserved",
            });

            // 제품 수량 감소
            await dbClient("Products").where({ product_id }).decrement("amount", 1);
            // 주문서 확인
            const record = await dbClient("Orders").where({ buyer_id, product_id }).first();

            res.status(201).json(record);
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
    const { product_id } = req.params;
    const { buyer_id } = req.body;
    const seller_id = req.decoded.id;

    try {
        const is_sellers_proudct = await dbClient("Products").where({ product_id, seller_id }).first();

        if (is_sellers_proudct) {
            const item = await dbClient("Orders").where({ product_id, buyer_id }).first();

            if (item.status == "Reserved") {
                await dbClient("Orders").where({ product_id, buyer_id }).update({
                    status: "Approval",
                });

                const updated_record = await dbClient("Orders").where({ product_id, buyer_id }).first();

                res.status(201).json(updated_record);
            } else {
                const error = new Error("거래 체결 불이행 " + item.status);
                error.status = 409;
                return next(error);
            }
        } else {
            const error = new Error("판매자 상품이 아님");
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
