import React, { useCallback } from 'react';
import '../style/style.css';
import useCategoryStore from '../store/CategoryStore';
import FiltersView from '../view/FiltersView';
import useProductPageStore from '../store/ProductPageStore';

const FiltersPresenter = () => {
    const categories = useCategoryStore((state) => state.categories)
    const fetchCategories = useCategoryStore((state) => state.fetchCategories)
    const fetchProductPage = useProductPageStore((state) => state.fetchProductPage);
    const setSelectedPage = useProductPageStore((state) => state.setSelectedPage);
    const setProductPrefix = useProductPageStore((state) => state.setProductPrefix);
    const setSelectedCategory = useCategoryStore((state) => state.setSelectedCategory)

    const handleFetchCategories = useCallback(() => {
        fetchCategories();
    }, [fetchCategories]);

    const handleCategoryChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const selectedCategoryId = event.target.value;
        setSelectedCategory(selectedCategoryId);
        setSelectedPage(1);
        setProductPrefix('');
        fetchProductPage(selectedCategoryId);
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
