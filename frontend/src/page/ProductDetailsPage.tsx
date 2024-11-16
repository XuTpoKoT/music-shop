import React from 'react';
import '../style/style.css';
import {Box, Container, CssBaseline} from "@mui/material";
import { ProductsPageStyle } from '../style/style';
import NavBar from '../component/NavBar';
import ProductDetailsPresenter from '../presenter/ProductDetailsPresenter';

const ProductDetailsPage = () => {
    return (
        <Container>
            <Box sx={ProductsPageStyle}>
                <CssBaseline />
                <NavBar/>
                <ProductDetailsPresenter></ProductDetailsPresenter>
            </Box>
        </Container>
        
    );
};

export default ProductDetailsPage;
