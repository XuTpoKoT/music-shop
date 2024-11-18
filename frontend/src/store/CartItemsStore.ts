import { AxiosError } from "axios";
import { create } from "zustand";
import { persist } from "zustand/middleware";
import CartItemsApi from "../api/CartItemsApi";
import { CartItemResponse } from "../dto/CartItemResponse ";
import { ErrorResponse, RequestStatus } from "../dto/RequestState";
import { useErrorStore } from "./ErrorStore";


interface CartItemsState {
    status: RequestStatus;
    cartItems: CartItemResponse | null;
    addCartItem: (productId: string) => void;
}

export const useCartItemsStore = create<CartItemsState>()(
    persist(
        (set, get) => ({
            status: RequestStatus.Idle,
            errorMessage: null,
            cartItems: null,
            addCartItem: async (productId) => {
                set({status: RequestStatus.Loading})
                try {
                    await CartItemsApi.addCartItem(productId);
                    set({ status: RequestStatus.Success });
                } catch (e) {                    
                    const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse;
                    const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed')
                    console.log(errMsg)
                    set({ status: RequestStatus.Error });
                    useErrorStore.setState({errorMessage: errMsg})
                }
            },
        }),
        {
            name: 'cartItems-storage',
            getStorage: () => sessionStorage,
        }
    )
);
