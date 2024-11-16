import React from 'react';
import '../style/style.css';
import {Box, Pagination} from "@mui/material";
import { PaginationStyle } from '../style/style';
import { ProductPageResponse } from '../service/response/ProductPageResponse';

const PaginationView = (props: {
    currentPage: number,
    productPage: RequestState<ProductPageResponse>,
    handlePageChange: (newPage: number) => void,
}) => {
    console.log("Render PaginationView")

    if (props.productPage.status !== 'success') {
        return (
            <Box sx={PaginationStyle}>
                <Pagination count={1} page={1} 
                    onChange={(_, page) => props.handlePageChange(page)}/>
            </Box>
        )
    }

    return (
        <Box sx={PaginationStyle}>
            <Pagination count={props.productPage.data!.totalPages} page={props.currentPage} 
                onChange={(_, page) => props.handlePageChange(page)}/>
        </Box>
    )
}

export default PaginationView;
