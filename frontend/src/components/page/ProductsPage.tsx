import React from 'react';
import '@/style/style.css';
import {Box, Container, CssBaseline} from "@mui/material";
import { ProductsPageStyle } from '@/style/style';
import NavBar from '@/components/common/NavBar';
import ProductsPageMain from '@/components/common/ProductsPageMain';
import ErrorPanel from '@/components/presenter/ErrorPanel';

const ProductsPage = () => {
    return (
        <Container>
            <Box sx={ProductsPageStyle}>
                <CssBaseline />
                <NavBar/>
                <ProductsPageMain></ProductsPageMain>
                <ErrorPanel />
            </Box>
        </Container>
        
    );
};

export default ProductsPage;
