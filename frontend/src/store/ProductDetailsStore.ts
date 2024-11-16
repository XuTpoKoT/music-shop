import { ErrorResponse } from "../http";
import { ProductResponse } from "../service/response/ProductResponse";
import {create} from 'zustand';
import {persist} from "zustand/middleware";
import ProductService from '../service/ProductService';
import { AxiosError } from 'axios';

interface ProductDetailsState {
    productDetails: RequestState<ProductResponse>;
    fetchProductDetails: (productId: string) => void;
}

const useProductDetailsStore = create<ProductDetailsState>()(
    persist(
        (set, get) => ({
            productDetails: { status: 'idle', data: null, errorMessage: null },
            fetchProductDetails: async (productId) => {
                set({productDetails: { status: 'loading', data: null, errorMessage: null }})
                try {
                    const response = await ProductService.getProductById(productId);
                    set({ productDetails: { status: 'success', data: response, errorMessage: null } });
                } catch (e) {                    
                    const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
                    const errMsg = 'Error: ' + (errorResponse.message ?? 'connection failed')
                    console.log(errMsg)
                    set({ productDetails: { status: 'error', data: null, errorMessage: errMsg } });
                }
            },
        }),
        {
            name: 'product-storage',
            getStorage: () => sessionStorage,
        }
    )
);

export default useProductDetailsStore;