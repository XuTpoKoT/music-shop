import MockAdapter from 'axios-mock-adapter';
import useProductPageStore from '@/store/ProductPageStore';
import { RequestStatus } from '@/dto/RequestState';
import { useErrorStore } from '@/store/ErrorStore';
import { describe, beforeEach, it, expect } from 'vitest';
import $api, { API_URL } from '@/api';

const axiosMock = new MockAdapter($api);
const mockProductResponse = {
  "id": "12345",
  "name": "Product Name",
  "price": 99.99,
  "description": "This is a description of the product.",
  "color": "Red",
  "manufacturerName": "Manufacturer Name",
  "imgRef": "https://example.com/product-image.jpg",
  "characteristics": {
    "weight": "1kg",
    "size": "Medium",
    "material": "Plastic"
  }
}

describe('useProductPageStore.fetchProductPage', () => {
    beforeEach(() => {
        useProductPageStore.setState({
            status: RequestStatus.Idle,
            productPage: null,
            selectedPage: 1,
            productPrefix: '',
        });

        useErrorStore.setState({
            errorMessage: null,
        });

        axiosMock.reset();
    });

    it('sets status to "loading" and successfully fetches product page', async () => {
        const mockResponse = { totalPages: 1, currentPage: 1, content: [mockProductResponse] };
        axiosMock.onGet(`${API_URL}/products`).reply(200, mockResponse);
        const fetchProductPage = useProductPageStore.getState().fetchProductPage;

        await fetchProductPage('categoryId1');

        const { status, productPage } = useProductPageStore.getState();
        expect(status).toBe('success');
        expect(productPage).toEqual(mockResponse);
        const errorStoreMessage = useErrorStore.getState().errorMessage;
        expect(errorStoreMessage).toBe(null);
    });

    it('sets error message in error store when API call fails', async () => {
        const errorMessage = 'Error fetching product page';
        axiosMock.onGet(`${API_URL}/products`).reply(500, { message: errorMessage });
        const fetchProductPage = useProductPageStore.getState().fetchProductPage;

        await fetchProductPage('categoryId1');

        const { status, productPage } = useProductPageStore.getState();
        expect(status).toBe('error');
        expect(productPage).toBeNull();
        const errorStoreMessage = useErrorStore.getState().errorMessage;
        expect(errorStoreMessage).toBe(`AxiosError: ${errorMessage}`);
    });

    it('sets a generic error message if no error response exists', async () => {
        axiosMock.onGet(`${API_URL}/products`).networkError();
        const fetchProductPage = useProductPageStore.getState().fetchProductPage;

        await fetchProductPage('categoryId1');

        const errorStoreMessage = useErrorStore.getState().errorMessage;
        expect(errorStoreMessage).toBe('AxiosError: connection failed');
    });
});
