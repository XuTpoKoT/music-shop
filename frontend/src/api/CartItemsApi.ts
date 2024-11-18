import $api from ".";

export default class CartItemsApi {

    static async addCartItem(productId: string) {
        const username = localStorage.getItem('username')
        return await $api.post(`/users/${username}/cartItems`, {productId})
            .then(response => response.data)
    }

}

