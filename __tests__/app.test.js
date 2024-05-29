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

            expect(response.status).toBe(200);
            expect(Array.isArray(response.body)).toBe(true);
        });

        it("상세 조회", async () => {
            const response = await request(app).get("/products/1");

            expect(response.status).toBe(200);
        });
    });

    describe("회원", () => {
        describe("토큰 발급", () => {
            it("판매자", async () => {
                const response = await request(app).post("/token").send({
                    username: "sellerA",
                    password: "pswd",
                });

                expect(response.status).toBe(200);
                expect(response.body).toHaveProperty("token");

                sellerA_token = response.body.token;
            });

            it("구매자A", async () => {
                const response = await request(app).post("/token").send({
                    username: "buyerA",
                    password: "pswd",
                });

                expect(response.status).toBe(200);
                expect(response.body).toHaveProperty("token");

                buyerA_token = response.body.token;
                buyerA_id = 2;
            });
            it("구매자B", async () => {
                const response = await request(app).post("/token").send({
                    username: "buyerB",
                    password: "pswd",
                });

                expect(response.status).toBe(200);
                expect(response.body).toHaveProperty("token");

                buyerB_token = response.body.token;
                buyerB_id = 3;
            });
        });

        describe("프로시저", () => {
            it("판매자/제품 등록", async () => {
                const response = await request(app).post("/products/register").set("Authorization", sellerA_token).send({
                    name: "Test_Product",
                    price: "15.99",
                    amount: 2,
                });
                product_id = response.body.product_id;

                expect(response.status).toBe(201);
            });

            it("구매자A/제품 구매", async () => {
                const response = await request(app).post(`/products/purchase`).set("Authorization", buyerA_token).send({
                    product_id,
                });

                expect(response.status).toBe(201);
                expect(response.body.status).toBe("Reserved");

                const check = await request(app).get(`/products/${product_id}`);
                expect(check.body.status).toBe("Available");
            });

            it("구매자B/제품 구매", async () => {
                const response = await request(app).post(`/products/purchase`).set("Authorization", buyerB_token).send({
                    product_id,
                });

                expect(response.status).toBe(201);
                expect(response.body.status).toBe("Reserved");

                const check = await request(app).get(`/products/${product_id}`);
                expect(check.body.status).toBe("Reserved");
            });

            it("구매자A/예약중인 용품 조회", async () => {
                const response = await request(app).get("/users/reserved-list").set("Authorization", buyerA_token);

                expect(response.status).toBe(200);
                expect(Array.isArray(response.body)).toBe(true);
                expect(response.body.length).toBe(1);
            });

            it("판매자/예약중인 용품 조회", async () => {
                const response = await request(app).get("/users/reserved-list").set("Authorization", sellerA_token);

                expect(response.status).toBe(200);
                expect(Array.isArray(response.body)).toBe(true);
                expect(response.body.length).toBe(2);
            });

            it("판매자/주문서 확인", async () => {
                let response = await request(app).post(`/orders`).set("Authorization", sellerA_token).send({
                    product_id,
                });

                expect(response.status).toBe(200);
                expect(response.body.length).toBe(2);
            });

            it("판매자/판매 승인", async () => {
                let response = await request(app).post(`/orders/sales-approval`).set("Authorization", sellerA_token).send({
                    buyer_id: buyerA_id,
                    product_id,
                });

                expect(response.status).toBe(201);
                expect(response.body.status).toBe("Approval");

                response = await request(app).post(`/orders/sales-approval`).set("Authorization", sellerA_token).send({
                    buyer_id: buyerB_id,
                    product_id,
                });

                expect(response.status).toBe(201);
                expect(response.body.status).toBe("Approval");
            });

            it("구매자A/주문서 확인", async () => {
                let response = await request(app).post(`/orders`).set("Authorization", buyerA_token).send({
                    product_id,
                });

                expect(response.status).toBe(200);
                expect(response.body.status).toBe("Approval");
            });

            it("구매자A/구매 확정", async () => {
                const response = await request(app).post(`/orders/purchase-confirm`).set("Authorization", buyerA_token).send({
                    product_id,
                });

                expect(response.status).toBe(201);
                expect(response.body.status).toBe("Confirm");
            });

            it("상품 재고 확인(재고)", async () => {
                const check = await request(app).get(`/products/${product_id}`);
                expect(check.body.status).not.toBe("SoldOut");
            });

            it("구매자B/구매 확정", async () => {
                const response = await request(app).post(`/orders/purchase-confirm`).set("Authorization", buyerB_token).send({
                    product_id,
                });

                expect(response.status).toBe(201);
                expect(response.body.status).toBe("Confirm");
            });

            it("상품 재고 확인(절판)", async () => {
                const check = await request(app).get(`/products/${product_id}`);
                expect(check.body.status).toBe("SoldOut");
            });

            it("구매자A/구매한 용품 조회", async () => {
                const response = await request(app).get("/users/purchased-list").set("Authorization", buyerA_token);

                expect(response.status).toBe(200);
                expect(Array.isArray(response.body)).toBe(true);
                expect(response.body.length).toBe(1);
            });
        });
    });
});
