import useProductPageStore from "@/store/ProductPageStore";
import useCategoryStore from '@/store/CategoryStore';
import SearchBarView from '@/components/view/SearchBarView';

const SearchBarPresenter = () => {
    const setProductPrefix = useProductPageStore((state) => state.setProductPrefix)
    const fetchProductPage = useProductPageStore((state) => state.fetchProductPage)

    const handleChangeSearchQuery = (e: React.ChangeEvent<HTMLInputElement>) => {
        console.log("Set ProductPrefix " + e.target.value)
        setProductPrefix(e.target.value)
    };

    const handleSubmitSearch = (e: React.FormEvent) => {
        e.preventDefault()
        console.log("handleSubmitSearch")
        const selectedCategoryId = useCategoryStore.getState().selectedCategoryId
        fetchProductPage(selectedCategoryId)
    };

    return (
        <SearchBarView handleInputChange={handleChangeSearchQuery} handleSubmit={handleSubmitSearch}></SearchBarView>
    );
};

export default SearchBarPresenter;

