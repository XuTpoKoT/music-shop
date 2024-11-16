import $api from "../http";
import { ProductPageResponse } from "./response/ProductPageResponse";
import { ProductResponse } from "./response/ProductResponse";

export default class ProductService {

    static async getProductPage(categoryId: string, pageNumber: number, productPrefix: string) {
        return await $api.get<ProductPageResponse>(`/products`, {
                params: {
                    pageNumber,
                    pageSize: 12,
                    ...(categoryId ? { categoryId } : {}),
                    ...(productPrefix ? { productPrefix } : {}),
                }
            })
            .then(response => response.data)
    }

    static async getProductById(productId: string) {
        return await $api.get<ProductResponse>(`/products/` + productId)
            .then(response => response.data)
    }
}

