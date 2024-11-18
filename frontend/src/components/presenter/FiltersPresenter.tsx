import React, { useCallback, useEffect } from 'react';
import useCategoryStore from '@/store/CategoryStore';
import FiltersView from '@/components/view/FiltersView';
import useProductPageStore from '@/store/ProductPageStore';

const FiltersPresenter = () => {
    const categories = useCategoryStore((state) => state.categories)
    const fetchCategories = useCategoryStore((state) => state.fetchCategories)

    useEffect(() => {
        fetchCategories()
    }, [fetchCategories]);

    const handleFetchCategories = useCallback(() => {
        fetchCategories();
    }, [fetchCategories]);

    const handleCategoryChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const selectedCategoryId = event.target.value;
        useCategoryStore.getState().setSelectedCategory(selectedCategoryId);
        useProductPageStore.getState().setSelectedPage(1);
        useProductPageStore.getState().setProductPrefix('');
        useProductPageStore.getState().fetchProductPage(selectedCategoryId);
    };

    return (
        <FiltersView
            categories={categories}
            onComponentDidMount={handleFetchCategories}
            handleCategoryChange={handleCategoryChange}
        />
    );
    
};

export default FiltersPresenter;
