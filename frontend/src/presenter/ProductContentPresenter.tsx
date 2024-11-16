import React, { useEffect } from 'react';
import '../style/style.css';
import useProductPageStore from "../store/ProductPageStore";
import useCategoryStore from '../store/CategoryStore';
import { Box } from '@mui/material';
import SearchBarView from '../view/SearchBarView';
import ProductsGridView from '../view/ProductsGridView';
import PaginationView from '../view/PaginationView';
import { ProductsContentStyle } from '../style/style';
import useAuthStore from '../store/AuthStore';

const ProductContentPresenter = () => {
    const isAuth = useAuthStore((state) => state.isAuth);
    const productPage = useProductPageStore((state) => state.productPage);
    const selectedPage = useProductPageStore((state) => state.selectedPage);
    const setSelectedPage = useProductPageStore((state) => state.setSelectedPage);
    const fetchProductPage = useProductPageStore((state) => state.fetchProductPage);
    const setProductPrefix = useProductPageStore((state) => state.setProductPrefix);
    const selectedCategoryId = useCategoryStore((state) => state.selectedCategoryId)

    useEffect(() => {fetchProductPage(selectedCategoryId)}, [fetchProductPage]);
    
    const onAddToCart = (productId: string) => {
        console.log("Add " + productId)
    }

    const handlePageChange = (newPage: number) => {
        console.log("Set Page " + newPage)
        setSelectedPage(newPage)
        fetchProductPage(selectedCategoryId)
    };

    const handleChangeSearchQuery = (e: React.ChangeEvent<HTMLInputElement>) => {
        console.log("Set ProductPrefix " + e.target.value)
        setProductPrefix(e.target.value)
    };

    const handleSubmitSearch = (e: React.FormEvent) => {
        e.preventDefault()
        console.log("handleSubmitSearch")
        fetchProductPage(selectedCategoryId)
    };

    return (
        <Box sx={ProductsContentStyle}>
            <SearchBarView handleInputChange={handleChangeSearchQuery} handleSubmit={handleSubmitSearch}></SearchBarView>
            <ProductsGridView productPage={productPage} isAuth={isAuth} onAddToCart={onAddToCart}>
            </ProductsGridView>
            <PaginationView currentPage={selectedPage} productPage={productPage} 
                handlePageChange={handlePageChange}></PaginationView>
        </Box>
    );
};

export default ProductContentPresenter;

