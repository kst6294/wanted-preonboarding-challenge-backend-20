import { Router } from "express";
import dbClient from "../db/dbClient.js";
import verifyToken from "../middlewares/auth.js";

var router = Router();

// 구매한 용품
// 구매자로서
router.get("/purchased_list", verifyToken, async (req, res, next) => {
    const buyer_id = req.decoded.id;

    try {
        const items = await dbClient("Orders").where({
            status: "SoldOut",
            buyer_id,
        });

        res.status(200).json(items);
    } catch (err) {
        next(err)
    }
});

// 예약중인 용품
// 구매자/판매자로서
router.get("/reserved_list", verifyToken, async (req, res, next) => {
    const buyer_id = req.decoded.id;
    const seller_id = req.decoded.id;

    try {
        // 판매자로서 상품 모음
        const sellers_item_ids = (await dbClient("Products").where({ seller_id })).map((each) => each.product_id);

        const items = await dbClient("Orders")
            .where({ status: "Reserved" })
            .andWhere(function () {
                this.where({ buyer_id }) // 구매자이거나
                    .orWhereIn("product_id", sellers_item_ids); // 판매 상품이거나
            });

        res.status(200).json(items);
    } catch (err) {
        next(err)
    }
});

export default router;
