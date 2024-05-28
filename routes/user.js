import { Router } from "express";
import verifyToken from "../middlewares/auth.js";
import { show_purchased_orders, show_reserved_orders } from "../services/userService.js";

var router = Router();

// 구매한 용품
// 구매자로서
router.get("/purchased_list", verifyToken, async (req, res, next) => {
    const buyer_id = req.decoded.id;

    try {
        const orders = await show_purchased_orders(buyer_id);

        res.status(200).json(orders);
    } catch (err) {
        next(err);
    }
});

// 예약중인 용품
// 구매자/판매자로서
router.get("/reserved_list", verifyToken, async (req, res, next) => {
    const buyer_id = req.decoded.id;
    const seller_id = req.decoded.id;

    try {
        const orders = await show_reserved_orders(buyer_id, seller_id);

        res.status(200).json(orders);
    } catch (err) {
        next(err);
    }
});

export default router;
