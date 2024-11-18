import useProductPageStore from "@/store/ProductPageStore";
import useCategoryStore from '@/store/CategoryStore';
import PaginationView from '@/components/view/PaginationView';

const PaginationPresenter = () => {
    const productPage = useProductPageStore((state) => state.productPage)
    
    const selectedPage = useProductPageStore.getState().selectedPage

    const handlePageChange = (newPage: number) => {
        console.log("Set Page " + newPage)
        useProductPageStore.getState().setSelectedPage(newPage)
        const selectedCategoryId = useCategoryStore.getState().selectedCategoryId
        useProductPageStore.getState().fetchProductPage(selectedCategoryId)
    };

    return (
        <PaginationView currentPage={selectedPage} productPage={productPage} 
                handlePageChange={handlePageChange}></PaginationView>
    );
};

export default PaginationPresenter;

