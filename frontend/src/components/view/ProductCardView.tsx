import React from 'react';
import {Box, Button, Link, Typography} from "@mui/material";
import { AddToCartButtonStyle, ProductCardStyle, ProductImageStyle, ProductNameStyle, ProductPriceStyle } from '@/style/style';
import { Link as RouterLink } from 'react-router-dom';
import { ProductResponse } from '@/dto/ProductResponse';

const ProductCardView = (props: {
    product: ProductResponse,
    isAuth: boolean,
    onAddToCart: (productId: string) => void,
}) => {
    console.log("Render ProductCardView")

    return (
        props.isAuth ? (<Box sx={ProductCardStyle}>
            <img src={props.product.imgRef} alt={props.product.name} style={ProductImageStyle} />
            <Typography sx={ProductPriceStyle}>{`${props.product.price} Р`}</Typography>
            <Typography sx={ProductNameStyle}>
                <RouterLink to={`/products/${props.product.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                    {props.product.name}
                </RouterLink>
            </Typography>
            <Button
                sx={AddToCartButtonStyle}
                onClick={() => props.onAddToCart(props.product.id)}
            >
                В корзину
            </Button>
        </Box>)
            : (<Box sx={ProductCardStyle}>
                <img src={props.product.imgRef} alt={props.product.name} style={ProductImageStyle} />
                <Typography sx={ProductPriceStyle}>{`${props.product.price} Р`}</Typography>
                <Typography sx={ProductNameStyle}>
                <RouterLink to={`/products/${props.product.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                    {props.product.name}
                </RouterLink>
            </Typography>
            </Box>)
        
    )
}

export default ProductCardView;
