import useProductPageStore from "@/store/ProductPageStore";
import ProductsGridView from '@/components/view/ProductsGridView';
import useAuthStore from '@/store/AuthStore';
import { useCartItemsStore } from '@/store/CartItemsStore';

const ProductsGridPresenter = () => {
    const isAuth = useAuthStore((state) => state.isAuth)
    const productPage = useProductPageStore((state) => state.productPage)
    const addCartItem = useCartItemsStore((state) => state.addCartItem)
    
    const onAddToCart = (productId: string) => {
        console.log("Add " + productId)
        addCartItem(productId)
    }

    return (
        <ProductsGridView productPage={productPage} isAuth={isAuth} onAddToCart={onAddToCart}>
            </ProductsGridView>
    );
};

export default ProductsGridPresenter;

