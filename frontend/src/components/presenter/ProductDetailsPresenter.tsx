import useAuthStore from '@/store/AuthStore';
import { useParams } from 'react-router-dom';
import useProductDetailsStore from '@/store/ProductDetailsStore';
import ProductDetailsView from '@/components/view/ProductDetailsView';
import { useEffect } from 'react';

const ProductDetailsPresenter = () => {
    const { id } = useParams<{ id: string }>()
    const isAuth = useAuthStore((state) => state.isAuth);
    const status = useProductDetailsStore((state) => state.status)
    const productDetails = useProductDetailsStore((state) => state.productDetails)
    const fetchProductDetails = useProductDetailsStore((state) => state.fetchProductDetails)

    useEffect(() => {
        console.log(id)
        if (id) {
            fetchProductDetails(id);
        }
    }, [id]);

    const onAddToCart = (productId: string) => {
        console.log("Add " + productId)
    }

    return (
        <ProductDetailsView status={status} productDetails={productDetails} isAuth={isAuth} onAddToCart={onAddToCart}>
        </ProductDetailsView>
    );
};

export default ProductDetailsPresenter;

