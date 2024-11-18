import { AxiosError } from "axios";
import { create } from "zustand";
import AuthApi from "../api/AuthApi";
import { AuthResponse } from "../dto/AuthResponse";
import { ErrorResponse, RequestStatus } from "../dto/RequestState";
import { useErrorStore } from "./ErrorStore";


interface AuthState {
    status: RequestStatus;
    authInfo: AuthResponse | null;
    isAuth: boolean;
    setIsAuth: (isAuth: boolean) => void;
    signIn: (email: string, password: string, navigate: (path: string) => void) => void;
    signUp: (email: string, password: string, repeatedPassword: string, navigate: (path: string) => void) => void;
}

const useAuthStore = create<AuthState>(set => ({
    status: RequestStatus.Idle,
    authInfo: null,
    isAuth: localStorage.getItem("token") != null,
    setIsAuth: (isAuth) => set({ isAuth: isAuth }),
    signIn: async (email: string, password: string, navigate: (path: string) => void) => {
        set({status: RequestStatus.Loading})
        try {
            const response = await AuthApi.signIn(email, password)
            set({ status: RequestStatus.Success, authInfo: response, isAuth: true });
            localStorage.setItem('token', response.token)
            localStorage.setItem('username', response.username)
            console.log('Token saved:', localStorage.getItem('token'))
            navigate('/')
        } catch (e) {                    
            const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse
            const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed')
            console.log(errMsg)
            set({ status: RequestStatus.Error, authInfo: null });
            useErrorStore.setState({errorMessage: errMsg})
        }
    },
    signUp: async (email: string, password: string, repeatedPassword: string, navigate: (path: string) => void) => {
        set({status: RequestStatus.Loading})
        try {
            const response = await AuthApi.signUp(email, password, repeatedPassword)
            set({ status: RequestStatus.Success, authInfo: response, isAuth: true });
            localStorage.setItem('token', response.token)
            localStorage.setItem('username', response.username)
            console.log('Token saved:', localStorage.getItem('token'))
            navigate('/')
        } catch (e) {                    
            const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse
            const errMsg = 'Error: ' + (errorResponse?.message ?? 'connection failed')
            console.log(errMsg)
            set({ status: RequestStatus.Error, authInfo: null });
            useErrorStore.setState({errorMessage: errMsg})
        }
    }
}));

export default useAuthStore;