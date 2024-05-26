import { Router } from "express";
import dbClient from "../db/dbClient.js";

var router = Router();

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
        const error = new Error("No exist.");z
        error.status = 404;
        return next(error);
    }
});

router.post("/products", async function (req, res, next) {
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

export default router;
