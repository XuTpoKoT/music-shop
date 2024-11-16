import React, { useEffect } from 'react';
import '../style/style.css';
import useAuthStore from '../store/AuthStore';
import { useParams } from 'react-router-dom';
import useProductDetailsStore from '../store/ProductDetailsStore';
import ProductDetailsView from '../view/ProductDetailsView';

const ProductDetailsPresenter = () => {
    const { id } = useParams<{ id: string }>()
    const isAuth = useAuthStore((state) => state.isAuth);
    const productDetails = useProductDetailsStore((state) => state.productDetails)
    const fetchProductDetails = useProductDetailsStore((state) => state.fetchProductDetails)

    useEffect(() => {
        if (id) {
            fetchProductDetails(id);
        }
    }, [id, fetchProductDetails]);

    const onAddToCart = (productId: string) => {
        console.log("Add " + productId)
    }

    return (
        <ProductDetailsView productDetails={productDetails} isAuth={isAuth} onAddToCart={onAddToCart}>
        </ProductDetailsView>
    );
};

export default ProductDetailsPresenter;

