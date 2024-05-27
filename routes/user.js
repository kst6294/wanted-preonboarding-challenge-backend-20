import { Router } from "express";
import dbClient from "../db/dbClient.js";
import verifyToken from "../middlewares/auth.js";

var router = Router();

// 구매한 용품
// 구매자로서
router.get("/purchased_list", verifyToken, async (req, res, next) => {
    const table = "products";
    const buyer_id = req.decoded.id;

    try {
        const item = await dbClient(table).where({
            status: "SoldOut",
            //buyer_id,
        });

        res.status(200).json(item);
    } catch (_) {
        const error = new Error("데이터베이스 오류");
        error.status = 500;
        return next(error);
    }
});

// 예약중인 용품
// 구매자/판매자로서
router.get("/reserved_list", verifyToken, async (req, res, next) => {
    const table = "products";
    const buyer_id = req.decoded.id;
    const seller_id = req.decoded.id;

    try {
        const item = await dbClient(table)
            .where("status", "Reserved")
            .andWhere(function () {
                this.where({ seller_id })
                //.orWhere({ buyer_id });
            });

        res.status(200).json(item);
    } catch (_) {
        const error = new Error("데이터베이스 오류");
        error.status = 500;
        return next(error);
    }
});

export default router;
