import $api from ".";
import { AuthResponse } from "../dto/AuthResponse";

export default class AuthApi {

    static async signIn(login: string, password: string) {
        return await $api.post<AuthResponse>('/auth/sign-in', {login, password})
            .then(response => response.data)
    }

    static async signUp(email: string, password: string, repeatedPassword: string) {
        return await $api.post<AuthResponse>('/auth/sign-up', {email, password, repeatedPassword})
            .then(response => response.data)
    }
}

