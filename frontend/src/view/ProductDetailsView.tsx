import React from 'react';
import '../style/style.css';
import {Box, Button, Typography} from "@mui/material";
import { ProductResponse } from '../service/response/ProductResponse';
import { AdditionalCharacteristicsBoxStyle, AdditionalCharacteristicsStyle, AddToCartButtonStyle, CharacteristicsStyle, DescriptionBoxStyle, MainCharacteristicsBoxStyle, ProductDetailsImageStyle, ProductDetailsMainStyle } from '../style/ProductDetailsStyle';

const ProductDetailsView = (props: {
    productDetails: RequestState<ProductResponse>,
    isAuth: boolean,
    onAddToCart: (productId: string) => void,
}) => {
    console.log("Render ProductDetailsView")

    if (props.productDetails.status === 'idle' || props.productDetails.status === 'loading') {
        return <div>Загрузка...</div>
    }

    if (props.productDetails.status === 'error') {
        return <div>Ошибка загрузки данных</div>
    }

    const product = props.productDetails.data!

    return <Box sx={ProductDetailsMainStyle}>
                <Box sx={MainCharacteristicsBoxStyle}>
                    <img src={product.imgRef} alt={product.name} style={ProductDetailsImageStyle} />
                    <Box sx={AdditionalCharacteristicsStyle}>
                        <Typography sx={{ fontSize: '22px',fontWeight: 'bold'}}>
                            {`${product.name}`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`Производитель: ${product.manufacturerName}`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`Цвет: ${product.color}`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`Цена: ${product.price} Р`}</Typography>
                        <Button
                            sx={AddToCartButtonStyle}
                            onClick={() => props.onAddToCart(product.id)}
                        >
                            В корзину
                        </Button>
                    </Box>
                </Box>
                <Box sx={AdditionalCharacteristicsBoxStyle}>
                    <Box sx={DescriptionBoxStyle}>
                        <Typography sx={{ fontSize: '24px',fontWeight: 'bold'}}>
                            {`Описание`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`${product.description}`}</Typography>
                    </Box>
                    <Box sx={CharacteristicsStyle}>
                        <Typography sx={{ fontSize: '24px',fontWeight: 'bold'}}>
                            {`Характеристики`}</Typography>
                        {Object.entries(product.characteristics).map(([key, value]) => (
                            <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                                {`${key}: ${value}`}</Typography>
                        ))}
                        
                    </Box>
                </Box>
            </Box>
}

export default ProductDetailsView;
