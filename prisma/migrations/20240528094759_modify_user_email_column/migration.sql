-- AlterTable
ALTER TABLE "user_tb" ALTER COLUMN "email" SET DEFAULT nextval('user_tb_email_seq'::regclass),
ALTER COLUMN "email" DROP DEFAULT,
ALTER COLUMN "email" SET DATA TYPE VARCHAR;
DROP SEQUENCE "user_tb_email_seq";
