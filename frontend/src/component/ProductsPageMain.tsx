import React from 'react';
import '../style/style.css';
import {Box} from "@mui/material";
import FiltersPresenter from '../presenter/FiltersPresenter';
import { ProductsPageMainStyle } from '../style/style';
import ProductContentPresenter from '../presenter/ProductContentPresenter';

const ProductsPageMain = () => {
    console.log("Render ProductsPageMain")

    return (
        <Box sx={ProductsPageMainStyle}>
            <FiltersPresenter></FiltersPresenter>
            <ProductContentPresenter></ProductContentPresenter>
        </Box>
    )
}

export default ProductsPageMain;
