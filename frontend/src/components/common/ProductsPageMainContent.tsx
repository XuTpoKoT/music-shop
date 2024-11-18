import React, { useEffect } from 'react';
import useProductPageStore from "@/store/ProductPageStore";
import useCategoryStore from '@/store/CategoryStore';
import { Box } from '@mui/material';
import { ProductsContentStyle } from '@/style/style';
import SearchBarPresenter from '@/components/presenter/SearchBarPresenter';
import ProductsGridPresenter from '@/components/presenter/ProductsGridPresenter';
import PaginationPresenter from '@/components/presenter/PaginationPresenter';

const ProductsPageMainContent = () => {
    const fetchProductPage = useProductPageStore((state) => state.fetchProductPage)

    useEffect(() => {
        const selectedCategoryId = useCategoryStore.getState().selectedCategoryId
        fetchProductPage(selectedCategoryId)
    }, [fetchProductPage]);

    return (
        <Box sx={ProductsContentStyle}>
            <SearchBarPresenter />
            <ProductsGridPresenter />
            <PaginationPresenter />
        </Box>
    );
};

export default ProductsPageMainContent;

