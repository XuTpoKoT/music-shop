import { ErrorResponse } from '@/dto/RequestState';
import axios, { AxiosError } from 'axios';
import { z } from 'zod';

export const API_URL = `http://localhost:8091/v2`

const $api = axios.create({
    withCredentials: true,
    baseURL: API_URL
})

$api.interceptors.request.use((config) => {
    if (localStorage.getItem('token') != null) {
        console.log("Token for request: " + localStorage.getItem('token'));
        config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
    }
    return config;
})

export function getServerErrorMessage(e: unknown): string {
    let errMsg = 'Error: unknown';
  
    if (e instanceof z.ZodError) {
      errMsg = `Error: wrong response format`;
    } else if (axios.isAxiosError(e)) {
      errMsg = 'AxiosError: ' + (e.response?.data?.message ?? 'connection failed');
    }
  
    return errMsg;
}

export default $api;
