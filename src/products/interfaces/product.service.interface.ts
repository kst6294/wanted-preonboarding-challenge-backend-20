import { Product } from "@prisma/client";

export interface ProductServiceInterface {
    findAll(): Promise<Product[]>
}