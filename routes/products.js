import { Router } from "express";
import dbClient from "../db/dbClient.js";
import verifyToken from "../middlewares/auth.js";

var router = Router();

// 목록조회
// 비회원 가능
router.get("/", async function (req, res, next) {
    try {
        const items = await dbClient("Products");

        res.status(200).json(items);
    } catch (err) {
        next(err)
    }
});

// 상세조회
// 비회원 가능
router.get("/:product_id", async function (req, res, next) {
    const { product_id } = req.params;

    try {
        const item = await dbClient("Products").where({ product_id }).first();

        res.status(200).json(item);
    } catch (err) {
        next(err)
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
    } catch (err) {
        next(err)
    }
});

// 제품 구매
// 판매중 상품만 가능
router.post("/:product_id/purchase", verifyToken, async function (req, res, next) {
    const { product_id } = req.params;
    const buyer_id = req.decoded.id;

    try {
        const item = await dbClient("Products").where({ product_id, status: "Available" }).first();

        if (item) {
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
    } catch (err) {
        next(err)
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
            const item = await dbClient("Orders").where({ product_id, buyer_id, status: "Reserved" }).first();

            if (item) {
                await dbClient("Orders").where({ product_id, buyer_id, status: "Reserved" }).update({
                    status: "Approval",
                });

                const updated_record = await dbClient("Orders").where({ product_id, buyer_id }).first();

                res.status(201).json(updated_record);
            } else {
                const error = new Error("예약 상품 아님");
                error.status = 409;
                return next(error);
            }
        } else {
            const error = new Error("판매자 상품이 아님");
            error.status = 409;
            return next(error);
        }
    } catch (err) {
        next(err)
    }
});

// 구매 확정
// 본인 주문서만 가능
// 판매자 승인 받은 상품만 가능
router.post("/:product_id/purchase_confirm", verifyToken, async function (req, res, next) {
    const { product_id } = req.params;
    const buyer_id = req.decoded.id;

    try {
        const item = await dbClient("Orders").where({ product_id, buyer_id, status: "Approval" }).first();

        if (item) {
            await dbClient("Orders").where({ product_id, buyer_id, status: "Approval" }).update({
                status: "Confirm",
            });

            const updated_record = await dbClient("Orders").where({ product_id, buyer_id }).first();

            res.status(201).json(updated_record);
        } else {
            const error = new Error("판매 승인 상품 아님");
            error.status = 409;
            return next(error);
        }
    } catch (err) {
        next(err)
    }
});

export default router;
