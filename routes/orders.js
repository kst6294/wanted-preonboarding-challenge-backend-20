import { Router } from "express";
import verifyToken from "../middlewares/auth.js";
import transactionHandler from "../middlewares/transactionHandler.js";
import { show_orders, approve_sale_order, confirm_purchase_order } from "../services/orderService.js";

var router = Router();

// 제품의 주문서 보기
// 구매자로서 주문서
// 또는 판매자로서 주문서 목록 제공
router.get("/", verifyToken, async function (req, res, next) {
    const { product_id } = req.body;
    const buyer_id = req.decoded.id;
    const seller_id = req.decoded.id;

    try {
        const orders = await show_orders({ buyer_id, product_id, seller_id });

        res.status(200).json(orders);
    } catch (err) {
        next(err);
    }
});

// 판매 승인
// 본인 상품만 가능
// 예약중 상품만 가능
router.post("/sales-approval", verifyToken, transactionHandler, async function (req, res, next) {
    const { buyer_id, product_id } = req.body;
    const seller_id = req.decoded.id;

    try {
        const updated_order = await approve_sale_order({ buyer_id, product_id, seller_id }, req.transactionProvider);

        res.status(201).json(updated_order);
    } catch (err) {
        next(err);
    }
});

// 구매 확정
// 본인 주문서만 가능
// 판매자 승인 받은 상품만 가능
router.post("/purchase-confirm", verifyToken, transactionHandler, async function (req, res, next) {
    const { product_id } = req.body;
    const buyer_id = req.decoded.id;

    try {
        const updated_order = await confirm_purchase_order({ buyer_id, product_id }, req.transactionProvider);

        res.status(201).json(updated_order);
    } catch (err) {
        next(err);
    }
});

export default router;
