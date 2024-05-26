import request from "supertest";

import app from "../app.js";
import dbClient from "../db/dbClient.js";

describe("GET /products", () => {
    afterAll(async () => {
        await dbClient.destroy();
    });

    it("목록 조회", async () => {
        const response = await request(app).get("/products");

        expect(response.status).toBe(200);
    });

    it("상세 조회", async () => {
        const response = await request(app).get("/products/1");

        expect(response.status).toBe(200);
    });

    it("제품 등록", async () => {
        const response = await request(app).post("/products").send({
            Name: "Test_Product",
            Price: "15.99",
            Seller: "Test_Seller",
        });

        expect(response.status).toBe(200);
    });
});
