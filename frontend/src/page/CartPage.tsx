import React from 'react';
import '../style/style.css';
import {Box, Container, CssBaseline} from "@mui/material";
import NavBar from '../component/NavBar';
import ProductsPageMain from '../component/ProductsPageMain';
import { ProductsPageStyle } from '../style/style';

const CartPage = () => {
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

export default CartPage;
