import { Product } from "@prisma/client";

export interface ProductRepositoryInterface {
    findAll(): Promise<Product[]>
}