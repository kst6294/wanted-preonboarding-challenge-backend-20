import request from "supertest";

import app from "../app.js";
import dbClient from "../db/dbClient.js";

describe("API TEST", () => {
    afterAll(async () => {
        await dbClient.destroy();
    });

    describe("비회원", () => {
        it("목록 조회", async () => {
            const response = await request(app).get("/products");

            console.log(response.body);
            expect(response.status).toBe(200);
            expect(Array.isArray(response.body)).toBe(true);
        });

        it("상세 조회", async () => {
            const response = await request(app).get("/products/1");

            expect(response.status).toBe(200);
            expect(response.headers["content-type"]).toEqual(expect.stringContaining("application/json")); // 응답의 Content-Type이 application/json인지 확인
            expect(response.body).toBeInstanceOf(Object);
        });
    });

    describe("회원", () => {
        it("토큰 발급", async () => {
            const response = await request(app).post("/token").send({
                username: "tester",
                password: "pswd",
            });

            expect(response.status).toBe(200);
            expect(response.body).toHaveProperty("token");

            token = response.body.token;
        });

        describe("/products", () => {
            it("제품 등록", async () => {
                const response = await request(app).post("/products").set("Authorization", token).send({
                    Name: "Test_Product",
                    Price: "15.99",
                });
                product_id = response.body.id;

                expect(response.status).toBe(201);
            });

            it("제품 구매", async () => {
                const response = await request(app).post(`/products/${product_id}/purchase`).set("Authorization", token);

                expect(response.status).toBe(201);
            });

            it("판매 승인", async () => {
                const response = await request(app).post(`/products/${product_id}/sales_approval`).set("Authorization", token);

                expect(response.status).toBe(201);
            });
        });

        describe("/user", () => {
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
});
