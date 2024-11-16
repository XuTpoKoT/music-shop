import { ProductResponse } from "./ProductResponse";

export interface ProductPageResponse {
  totalPages: number;
  currentPage: number;
  content: ProductResponse[];
}