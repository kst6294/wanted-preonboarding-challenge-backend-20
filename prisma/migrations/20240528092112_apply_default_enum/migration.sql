/*
  Warnings:

  - The `status` column on the `product_tb` table would be dropped and recreated. This will lead to data loss if there is data in the column.
  - The `status` column on the `transaction_tb` table would be dropped and recreated. This will lead to data loss if there is data in the column.

*/
-- CreateEnum
CREATE TYPE "ProductStatus" AS ENUM ('AVAILABLE', 'RESERVED', 'SOLD');

-- CreateEnum
CREATE TYPE "TransactionStatus" AS ENUM ('RESERVED', 'Approval', 'Confirm');

-- AlterTable
ALTER TABLE "product_tb" DROP COLUMN "status",
ADD COLUMN     "status" "ProductStatus" NOT NULL DEFAULT 'AVAILABLE';

-- AlterTable
ALTER TABLE "transaction_tb" DROP COLUMN "status",
ADD COLUMN     "status" "TransactionStatus" NOT NULL DEFAULT 'RESERVED';
