import {create} from 'zustand';
import {persist} from "zustand/middleware";
import ProductService from '../service/ProductService';
import { ProductPageResponse } from '../service/response/ProductPageResponse';
import { AxiosError } from 'axios';
import { ErrorResponse } from '../http';

interface ProductPageState {
    productPage: RequestState<ProductPageResponse>;
    selectedPage: number;
    productPrefix: string;
    setSelectedPage: (pageNumber: number) => void;
    setProductPrefix: (productPrefix: string) => void;
    fetchProductPage: (categoryId: string) => void;
}

const useProductPageStore = create<ProductPageState>()(
    persist(
        (set, get) => ({
            productPage: { status: 'idle', data: null, errorMessage: null },
            selectedPage: 1,
            productPrefix: '',
            setSelectedPage: (pageNumber) => set({ selectedPage: pageNumber }),
            setProductPrefix: (newProductPrefix) => set({ productPrefix: newProductPrefix }),
            fetchProductPage: async (categoryId) => {
                set({productPage: { status: 'loading', data: null, errorMessage: null }})
                try {
                    const response = await ProductService.getProductPage(categoryId, get().selectedPage, get().productPrefix)
                    set({ productPage: { status: 'success', data: response, errorMessage: null } });
                } catch (e) {                    
                    const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
                    const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed')
                    console.log(errMsg)
                    set({ productPage: { status: 'error', data: null, errorMessage: errMsg } });
                }
            },
        }),
        {
            name: 'product-storage',
            getStorage: () => sessionStorage,
        }
    )
);

export default useProductPageStore;