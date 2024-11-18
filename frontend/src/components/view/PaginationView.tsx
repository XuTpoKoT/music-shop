import React from 'react';
import {Box, Pagination} from "@mui/material";
import { ProductPageResponse } from '@/dto/ProductPageResponse';
import { PaginationStyle } from '@/style/style';

const PaginationView = (props: {
    currentPage: number,
    productPage: ProductPageResponse | null,
    handlePageChange: (newPage: number) => void,
}) => {
    console.log("Render PaginationView")

    if (props.productPage === null) {
        return (
            <Box sx={PaginationStyle}>
                <Pagination count={1} page={1} 
                    onChange={(_, page) => props.handlePageChange(page)}/>
            </Box>
        )
    }

    return (
        <Box sx={PaginationStyle}>
            <Pagination count={props.productPage.totalPages} page={props.currentPage} 
                onChange={(_, page) => props.handlePageChange(page)}/>
        </Box>
    )
}

export default PaginationView;
