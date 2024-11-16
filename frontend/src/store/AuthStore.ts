import {create} from 'zustand';
import AuthService from "../service/AuthService";
import { AuthResponse } from '../service/response/AuthResponse';
import { AxiosError } from 'axios';
import { ErrorResponse } from '../http';

interface AuthState {
    authInfo: RequestState<AuthResponse>;
    isAuth: boolean;
    setIsAuth: (isAuth: boolean) => void;
    signIn: (email: string, password: string, navigate: (path: string) => void) => void;
    signUp: (email: string, password: string, repeatedPassword: string, navigate: (path: string) => void) => void;
}

const useAuthStore = create<AuthState>(set => ({
    authInfo: { status: 'idle', data: null, errorMessage: null },
    isAuth: localStorage.getItem("token") != null,
    setIsAuth: (isAuth) => set({ isAuth: isAuth }),
    signIn: async (email: string, password: string, navigate: (path: string) => void) => {
        set({authInfo: { status: 'loading', data: null, errorMessage: null }})
        try {
            const response = await AuthService.signIn(email, password)
            set({ authInfo: { status: 'success', data: response, errorMessage: null }, isAuth: true })
            localStorage.setItem('token', response.token)
            console.log('Token saved:', localStorage.getItem('token'))
            navigate('/')
        } catch (e) {                    
            const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse
            const errMsg = 'Error: ' + (errorResponse.message ?? 'connection failed')
            console.log(errMsg)
            set({ authInfo: { status: 'error', data: null, errorMessage: errMsg } })
        }
    },
    signUp: async (email: string, password: string, repeatedPassword: string, navigate: (path: string) => void) => {
        set({authInfo: { status: 'loading', data: null, errorMessage: null }})
        try {
            const response = await AuthService.signUp(email, password, repeatedPassword)
            set({ authInfo: { status: 'success', data: response, errorMessage: null }, isAuth: true });
            localStorage.setItem('token', response.token);
            console.log('Token saved:', localStorage.getItem('token'))
            navigate('/');
        } catch (e) {                    
            const errorResponse = (e as AxiosError)?.response?.data as ErrorResponse
            const errMsg = 'Error: ' + (errorResponse.message ?? 'connection failed')
            console.log(errMsg)
            set({ authInfo: { status: 'error', data: null, errorMessage: errMsg } })
        }
    }
}));

export default useAuthStore;