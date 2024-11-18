import { ProductPageResponse } from "../dto/ProductPageResponse";
import { ProductResponse } from "../dto/ProductResponse";
import $api from "./index";

export default class ProductsApi {
    static async getProducts(categoryId: string, pageNumber: number, productPrefix: string) {
        return await $api.get<ProductPageResponse>('/products', {
                params: {
                    pageNumber,
                    pageSize: 12,
                    ...(categoryId ? { categoryId } : {}),
                    ...(productPrefix ? { productPrefix } : {}),
                }
            })
            .then((response) => response.data)
    }

    static async getProductById(productId: string) {
        return await $api.get<ProductResponse>(`/products/` + productId)
            .then((response) => response.data)
    }
}

