import {create} from 'zustand';
import {persist} from "zustand/middleware";
import { CategoryResponse } from '../service/response/CategoryResponse';
import CategoryService from '../service/CategoryService';
import { AxiosError } from 'axios';
import { ErrorResponse } from '../http';

interface CategoriesState {
    categories: RequestState<CategoryResponse[]>;
    selectedCategoryId: string;
    setSelectedCategory: (categoryId: string) => void;
    fetchCategories: () => void;
}

const useCategoryStore = create<CategoriesState>()(
    persist(
        (set, get) => ({
            categories: { status: 'idle', data: null, errorMessage: null },
            selectedCategoryId: '',
            setSelectedCategory: (categoryId) => set({ selectedCategoryId: categoryId }),
            fetchCategories: async () => {
                set({categories: { status: 'loading', data: null, errorMessage: null }})
                try {
                    const response = await CategoryService.getCategories()
                    set({ categories: { status: 'success', data: response, errorMessage: null } });
                } catch (e) {
                    const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
                    const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed')
                    console.log(errMsg)
                    set({ categories: { status: 'error', data: null, errorMessage: errMsg } });
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