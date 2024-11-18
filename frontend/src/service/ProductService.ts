import { AxiosError } from "axios";
import { ErrorResponse } from "react-router-dom";
import { string, number } from "zod";
import $api from "../api";
import ProductsApi from "../api/ProductsApi";
import { ProductPageResponse } from "../dto/ProductPageResponse";
import { ProductResponse } from "../dto/ProductResponse";
import { RequestStatus } from "../dto/RequestState";
import useProductPageStore from "../store/ProductPageStore";

// export const fetchProductPage = async (categoryId: string) => {
//     const set = useProductPageStore.se; // Получение set из Zustand
//     const { selectedPage, productPrefix } = useProductPageStore.getState(); // Получение нужных значений из хранилища

//     set({ status: RequestStatus.Loading });

//     try {
//         const response = await ProductService.getProductPage(categoryId, selectedPage, productPrefix);
//         set({ status: RequestStatus.Success, productPage: response });
//     } catch (e) {
//         const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
//         const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed');
//         console.log(errMsg);
//         set({ status: RequestStatus.Error, errorMessage: errMsg, productPage: null });
//     }
// };

// export default class ProductService {

//     static async fetchProductPage (categoryId: string) {
//         const set = useProductPageStore.getState(); // Получение set из Zustand
//         const { selectedPage, productPrefix } = useProductPageStore.getState(); // Получение нужных значений из хранилища
    
//         set({ status: RequestStatus.Loading });
    
//         try {
//             const response = await ProductsApi.getProducts(categoryId, selectedPage, productPrefix);
//             set({ status: RequestStatus.Success, productPage: response });
//         } catch (e) {
//             const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
//             const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed');
//             console.log(errMsg);
//             set({ status: RequestStatus.Error, errorMessage: errMsg, productPage: null });
//         }
//     }
// }

