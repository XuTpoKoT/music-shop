import React from 'react';
import '@/style/style.css';
import {Box, Container, CssBaseline} from "@mui/material";
import NavBar from '@/components/common/NavBar';
import ProductsPageMain from '@/components/common/ProductsPageMain';
import { ProductsPageStyle } from '@/style/style';

const OrdersPage = () => {
    return (
        <Container>
            <Box sx={ProductsPageStyle}>
                <CssBaseline />
                <NavBar/>
                <ProductsPageMain></ProductsPageMain>
            </Box>
        </Container>
        
    );
};

export default OrdersPage;
