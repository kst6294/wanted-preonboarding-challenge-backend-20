import request from "supertest";
import { faker } from "@faker-js/faker";
import { RowDataPacket } from "mysql2";
import DeleteUser from "../scripts/delete-user";
import InsertProduct from "../scripts/insert-product";
import { ProductStatus } from "../src/interfaces/IProduct.dto";
import App from "../src/app";
import database from "../src/database";
import getUserID from "./utils";

describe("거래 API", () => {
  const app = new App().app;
  const price = faker.commerce.price({ dec: 0, min: 1_000, max: 10_000 });
  let cookie: string;
  let userID: number;

  beforeAll(() => database.connect());

  afterAll(async () => await database.pool.end());

  describe("[사전작업] 쿠키발급", () => {
    const name = faker.internet.userName();
    const password = faker.internet.password();

    it("1. 회원가입", async () => {
      const res = await request(app).post("/api/users/sign-up").send({
        name,
        password,
      });

      expect(res.status).toBe(201);
    });

    it("2. 성공", async () => {
      const res = await request(app).post("/api/users/log-in").send({
        name,
        password,
      });

      expect(res.status).toBe(201);

      const cookies = res.get("Set-Cookie");
      expect(Array.isArray(cookies)).toBe(true);

      [cookie] = cookies as string[];
      userID = getUserID(cookie) as number;
    });
  });

  describe("구매요청", () => {
    let productID: number;

    it("1. 상품등록", async () => {
      const { insertId } = await InsertProduct.run({ amount: 2, userID });
      expect(insertId).toBeGreaterThan(0);

      productID = insertId;
    });

    it("2. 성공", async () => {
      const res = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", cookie)
        .send({ price });

      expect(res.status).toBe(201);
    });

    it("3. 상품 판매중 상태 검증", async () => {
      const res = await request(app).get(`/api/products/${productID}`);

      expect(res.status).toBe(200);
      expect(res.body.data.productStatus).toBe(ProductStatus.판매중);
    });

    it("[유효성 검사] 중복 요청", async () => {
      const res = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", cookie)
        .send({ price });

      expect(res.status).toBe(409);
    });
  });

  describe("상품 상태 동시 검증", () => {
    let productID: number;

    it("1. 상품등록", async () => {
      const { insertId } = await InsertProduct.run({ amount: 1, userID });
      expect(insertId).toBeGreaterThan(0);

      productID = insertId;
    });

    it("2. 구매요청", async () => {
      try {
        const [res1, res2] = await Promise.all([
          request(app)
            .post(`/api/transactions/${productID}/request`)
            .set("Cookie", cookie)
            .send({ price }),
          request(app)
            .post(`/api/transactions/${productID}/request`)
            .set("Cookie", ["userID=1; Path=/;"])
            .send({ price }),
        ]);

        expect(res1.status).toBe(201);
        expect(res2.status).toBe(201);
      } catch (error) {
        const [[row]] = await database.pool.query<RowDataPacket[]>(
          "SHOW ENGINE INNODB STATUS;"
        );

        console.error(row.Status);
        throw error;
      }
    });
  });

  describe("상품 예약중 상태 검증", () => {
    let productID: number;

    it("1. 상품등록", async () => {
      const { insertId } = await InsertProduct.run({ amount: 1, userID });
      expect(insertId).toBeGreaterThan(0);

      productID = insertId;
    });

    it("2. 구매요청", async () => {
      const res1 = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", cookie)
        .send({ price });

      expect(res1.status).toBe(201);

      const res2 = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", ["userID=1; Path=/;"])
        .send({ price });

      expect(res2.status).toBe(201);
    });

    it("3. 성공", async () => {
      const res = await request(app).get(`/api/products/${productID}`);

      expect(res.status).toBe(200);
      expect(res.body.data.productStatus).toBe(ProductStatus.예약중);
    });
  });

  describe("판매승인", () => {
    let productID: number;

    it("1. 상품등록", async () => {
      const { insertId } = await InsertProduct.run({ amount: 1, userID });
      expect(insertId).toBeGreaterThan(0);

      productID = insertId;
    });

    it("2. 구매요청", async () => {
      const res = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", cookie)
        .send({ price });

      expect(res.status).toBe(201);
    });

    it("3. 성공", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/approve`)
        .set("Cookie", cookie)
        .send({ buyerID: userID });

      expect(res.status).toBe(200);
    });

    it("[유효성 검사] 중복 요청", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/approve`)
        .set("Cookie", cookie)
        .send({ buyerID: userID });

      expect(res.status).toBe(409);
    });

    it("[유효성 검사] 판매자 불일치", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/approve`)
        .set("Cookie", ["userID=1; Path=/;"])
        .send({ buyerID: userID });

      expect(res.status).toBe(403);
    });
  });

  describe("판매승인 미처리", () => {
    let productID: number;

    it("1. 상품등록", async () => {
      const { insertId } = await InsertProduct.run({ amount: 1, userID });
      expect(insertId).toBeGreaterThan(0);

      productID = insertId;
    });

    it("2. 구매요청", async () => {
      const res = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", cookie)
        .send({ price });

      expect(res.status).toBe(201);
    });

    it("3. 성공", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/confirm`)
        .set("Cookie", cookie);

      expect(res.status).toBe(403);
    });
  });

  describe("구매확정", () => {
    let productID: number;

    it("1. 상품등록", async () => {
      const { insertId } = await InsertProduct.run({ amount: 1, userID });
      expect(insertId).toBeGreaterThan(0);

      productID = insertId;
    });

    it("2. 구매요청", async () => {
      const res = await request(app)
        .post(`/api/transactions/${productID}/request`)
        .set("Cookie", cookie)
        .send({ price });

      expect(res.status).toBe(201);
    });

    it("3. 판매승인", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/approve`)
        .set("Cookie", cookie)
        .send({ buyerID: userID });

      expect(res.status).toBe(200);
    });

    it("4. 성공", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/confirm`)
        .set("Cookie", cookie);

      expect(res.status).toBe(200);
    });

    it("5. 상품 완료 상태 검증", async () => {
      const res = await request(app).get(`/api/products/${productID}`);

      expect(res.status).toBe(200);
      expect(res.body.data.productStatus).toBe(ProductStatus.완료);
    });

    it("[유효성 검사] 중복 요청", async () => {
      const res = await request(app)
        .put(`/api/transactions/${productID}/confirm`)
        .set("Cookie", cookie);

      expect(res.status).toBe(409);
    });
  });

  describe("[사후작업] 회원삭제", () => {
    it("1. 성공", async () => {
      const { affectedRows } = await DeleteUser.run({ userID });
      expect(affectedRows).toBeGreaterThan(0);
    });
  });
});
