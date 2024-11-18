import { create } from "zustand";
import { persist } from "zustand/middleware";
import ProductsApi from "../api/ProductsApi";
import { ProductPageResponse, ProductPageResponseSchema } from "@/dto/ProductPageResponse";
import { RequestStatus } from "../dto/RequestState";
import { useErrorStore } from "./ErrorStore";
import { getServerErrorMessage } from "@/api";

interface ProductPageState {
    status: RequestStatus;
    productPage: ProductPageResponse | null;
    selectedPage: number;
    productPrefix: string;
    setSelectedPage: (pageNumber: number) => void;
    setProductPrefix: (productPrefix: string) => void;
    fetchProductPage: (categoryId: string) => void;
}

const useProductPageStore = create<ProductPageState>()(
    persist(
        (set, get) => ({
            status: RequestStatus.Idle,
            productPage: null,
            selectedPage: 1,
            productPrefix: '',
            setSelectedPage: (pageNumber) => set({ selectedPage: pageNumber }),
            setProductPrefix: (newProductPrefix) => set({ productPrefix: newProductPrefix }),
            fetchProductPage: async (categoryId) => {
                set({status: RequestStatus.Loading})
                try {
                    const response = await ProductsApi.getProducts(categoryId, get().selectedPage, get().productPrefix)
                    const validatedResponse = ProductPageResponseSchema.parse(response)
                    set({ status: RequestStatus.Success, productPage: validatedResponse });
                } catch (e) {
                    const errMsg = getServerErrorMessage(e)
                    console.log(errMsg)
                    set({ status: RequestStatus.Error, productPage: null });
                    useErrorStore.setState({errorMessage: errMsg})
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