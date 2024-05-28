-- CreateTable
CREATE TABLE "product_tb" (
    "idx" SERIAL NOT NULL,
    "user_idx" INTEGER NOT NULL,
    "name" VARCHAR NOT NULL,
    "price" INTEGER NOT NULL,
    "status" VARCHAR NOT NULL,
    "created_at" TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMPTZ(6),

    CONSTRAINT "product_tb_pkey" PRIMARY KEY ("idx")
);

-- CreateTable
CREATE TABLE "transaction_tb" (
    "idx" SERIAL NOT NULL,
    "user_idx" INTEGER NOT NULL,
    "product_idx" INTEGER NOT NULL,
    "price" INTEGER NOT NULL,
    "status" VARCHAR NOT NULL,
    "created_at" TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMPTZ(6),

    CONSTRAINT "transaction_tb_pkey" PRIMARY KEY ("idx")
);

-- CreateTable
CREATE TABLE "user_tb" (
    "idx" SERIAL NOT NULL,
    "name" VARCHAR NOT NULL,
    "email" SERIAL NOT NULL,
    "password" CHAR(60) NOT NULL,
    "created_at" TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMPTZ(6),

    CONSTRAINT "user_tb_pkey" PRIMARY KEY ("idx")
);

-- CreateIndex
CREATE UNIQUE INDEX "email_deleted_at_unique" ON "user_tb"("email", "deleted_at");

-- AddForeignKey
ALTER TABLE "product_tb" ADD CONSTRAINT "fk_user_tb_to_product_tb" FOREIGN KEY ("user_idx") REFERENCES "user_tb"("idx") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "transaction_tb" ADD CONSTRAINT "fk_product_tb_to_transaction_tb" FOREIGN KEY ("product_idx") REFERENCES "product_tb"("idx") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "transaction_tb" ADD CONSTRAINT "fk_user_tb_to_transaction_tb" FOREIGN KEY ("user_idx") REFERENCES "user_tb"("idx") ON DELETE NO ACTION ON UPDATE NO ACTION;
