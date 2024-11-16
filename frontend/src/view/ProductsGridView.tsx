import React from 'react';
import '../style/style.css';
import Grid from '@mui/material/Grid';
import { ProductsGridStyle } from '../style/style';
import ProductCardView from './ProductCardView';
import { ProductPageResponse } from '../service/response/ProductPageResponse';

const ProductsGridView = (props: {
    productPage: RequestState<ProductPageResponse>,
    isAuth: boolean,
    onAddToCart: (productId: string) => void,
}) => {
    console.log("Render ProductsGridView")

    if (props.productPage.status === 'idle' || props.productPage.status === 'loading') {
        return <div>Загрузка...</div>
    }

    if (props.productPage.status === 'error') {
        return <div>Ошибка загрузки данных</div>
    }

    return (
        <Grid container sx={ProductsGridStyle} rowSpacing={'20px'} columnSpacing={'10px'}>
            {props.productPage.data!.content?.map(p => {
                return (
                    <Grid item xs={12} sm={6} md={4} lg={3}>
                        <ProductCardView product={p} isAuth={props.isAuth} onAddToCart={props.onAddToCart}></ProductCardView>
                    </Grid>
                )})}
        </Grid>
    )
}

export default ProductsGridView;
