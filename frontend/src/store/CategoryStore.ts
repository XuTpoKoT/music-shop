import { AxiosError } from "axios";
import { create } from "zustand";
import { persist } from "zustand/middleware";
import CategoriesApi from "../api/CategoriesApi";
import { CategoryResponse } from "../dto/CategoryResponse";
import { ErrorResponse, RequestStatus } from "../dto/RequestState";
import { useErrorStore } from "./ErrorStore";


interface CategoriesState {
    status: RequestStatus;
    categories: CategoryResponse[] | null;
    selectedCategoryId: string;
    setSelectedCategory: (categoryId: string) => void;
    fetchCategories: () => void;
}

const useCategoryStore = create<CategoriesState>()(
    persist(
        (set, get) => ({
            status: RequestStatus.Idle,
            categories: null,
            selectedCategoryId: '',
            setSelectedCategory: (categoryId) => set({ selectedCategoryId: categoryId }),
            fetchCategories: async () => {
                set({status: RequestStatus.Loading})
                try {
                    const response = await CategoriesApi.getCategories()
                    set({ status: RequestStatus.Success, categories: response });
                } catch (e) {
                    const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
                    const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed')
                    console.log(errMsg)
                    set({ status: RequestStatus.Error, categories: null });
                    useErrorStore.setState({errorMessage: errMsg})
                }
            },
        }),
        {
            name: 'categories-storage',
            getStorage: () => sessionStorage,
        }
    )
);

export default useCategoryStore;