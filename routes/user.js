import { Router } from "express";
import dbClient from "../db/dbClient.js";
import verifyToken from "../middlewares/auth.js";

var router = Router();

router.get("/purchased_list", verifyToken, async (req, res, next) => {
    const table = "products";
    const user = req.decoded.username;

    try {
        const item = await dbClient(table).where({
            ReservationState: "SoldOut",
            Buyer: user,
        });

        res.setHeader("Content-Type", "application/json");
        res.json(item);
    } catch (_) {
        const error = new Error("No exist.");
        error.status = 404;
        return next(error);
    }
});

router.get("/reserved_list", verifyToken, async (req, res, next) => {
    const table = "products";
    const user = req.decoded.username;

    try {
        const item = await dbClient(table)
            .where("ReservationState", "Reserved")
            .andWhere(function () {
                this.where("Buyer", user).orWhere("Seller", user);
            });

        res.setHeader("Content-Type", "application/json");
        res.json(item);
    } catch (_) {
        const error = new Error("No exist.");
        error.status = 404;
        return next(error);
    }
});

export default router;
