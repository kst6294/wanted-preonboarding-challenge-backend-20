import { Router } from "express";
import verifyToken from "../middlewares/auth.js";
import transactionHandler from "../middlewares/transactionHandler.js";
import { view_product_list, view_product_detail, register_product, purchase_product } from "../services/productService.js";

var router = Router();

// 목록조회
// 비회원 가능
router.get("/", async function (req, res, next) {
    try {
        const items = await view_product_list();

        res.status(200).json(items);
    } catch (err) {
        next(err);
    }
});

// 상세조회
// 비회원 가능
router.get("/:product_id", async function (req, res, next) {
    const { product_id } = req.params;

    try {
        const item = await view_product_detail(product_id);

        res.status(200).json(item);
    } catch (err) {
        next(err);
    }
});

// 제품 등록
// 회원만 가능
router.post("/register", verifyToken, async function (req, res, next) {
    const seller_id = req.decoded.id;
    const post_data = req.body;

    try {
        const product_id = await register_product({ seller_id, post_data });

        res.status(201).json({ product_id });
    } catch (err) {
        next(err);
    }
});

// 제품 구매
// 판매중 상품만 가능
router.post("/purchase", verifyToken, transactionHandler, async function (req, res, next) {
    const { product_id } = req.body;
    const buyer_id = req.decoded.id;

    try {
        const order = await purchase_product({ product_id, buyer_id }, req.transactionProvider);

        res.status(201).json(order);
    } catch (err) {
        next(err);
    }
});

export default router;
