import { Router } from "express";
import verifyToken from "../middlewares/auth.js";
import { approve_sale_order, confirm_purchase_order } from "../services/orderService.js";

var router = Router();

// 판매 승인
// 본인 상품만 가능
// 예약중 상품만 가능
router.post("/:product_id/sales_approval", verifyToken, async function (req, res, next) {
    const { product_id } = req.params;
    const { buyer_id } = req.body;
    const seller_id = req.decoded.id;

    try {
        const updated_order = await approve_sale_order(product_id, buyer_id, seller_id);

        res.status(201).json(updated_order);
    } catch (err) {
        next(err);
    }
});

// 구매 확정
// 본인 주문서만 가능
// 판매자 승인 받은 상품만 가능
router.post("/:product_id/purchase_confirm", verifyToken, async function (req, res, next) {
    const { product_id } = req.params;
    const buyer_id = req.decoded.id;

    try {
        const updated_order = await confirm_purchase_order(product_id, buyer_id);

        res.status(201).json(updated_order);
    } catch (err) {
        next(err);
    }
});

export default router;
