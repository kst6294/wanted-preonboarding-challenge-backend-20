import request from "supertest";

import app from "../app.js";
import dbClient from "../db/dbClient.js";

describe("API TEST", () => {
    afterAll(async () => {
        await dbClient.destroy();
    });

    it("토큰 발급", async () => {
        const response = await request(app).post("/token").send({
            username: "tester",
            password: "pswd",
        });

        expect(response.status).toBe(200);
        expect(response.body.code).toBe(200);
        expect(response.body).toHaveProperty("token");

        token = response.body.token;
    });

    describe("GET /products", () => {
        it("목록 조회", async () => {
            const response = await request(app).get("/products");

            expect(response.status).toBe(200);
        });

        it("상세 조회", async () => {
            const response = await request(app).get("/products/1");

            expect(response.status).toBe(200);
        });

        it("제품 등록", async () => {
            const response = await request(app).post("/products").set("Authorization", token).send({
                Name: "Test_Product",
                Price: "15.99",
                Seller: "Test_Seller",
            });

            expect(response.status).toBe(200);
        });

        it("제품 구매", async () => {
            const response = await request(app).post("/products/8/purchase").set("Authorization", token);

            expect(response.status).toBe(200);
        });

        it("판매 승인", async () => {
            const response = await request(app).post("/products/8/sales_approval").set("Authorization", token);

            expect(response.status).toBe(200);
        });
    });

    describe("GET /user", () => {
        it("구매한 용품 조회", async () => {
            const response = await request(app).get("/user/purchased_list").set("Authorization", token);

            expect(response.status).toBe(200);
            expect(Array.isArray(response.body)).toBe(true);
        });

        it("예약중인 용품 조회", async () => {
            const response = await request(app).get("/user/reserved_list").set("Authorization", token);

            expect(response.status).toBe(200);
            expect(Array.isArray(response.body)).toBe(true);
        });
    });
});
