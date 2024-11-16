import $api, {ErrorResponse} from "../http";
import {AuthResponse} from "./response/AuthResponse";
import {AxiosError} from "axios";

export default class AuthService {

    static async signIn(login: string, password: string) {
        return await $api.post<AuthResponse>('/auth/sign-in', {login, password})
            .then(response => response.data)
    }

    static async signUp(email: string, password: string, repeatedPassword: string) {
        return await $api.post<AuthResponse>('/auth/sign-up', {email, password, repeatedPassword})
            .then(response => response.data)
    }
}

