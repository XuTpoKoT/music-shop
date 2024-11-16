import axios from 'axios';

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

export type ErrorResponse = {
    message: string;
    code?: number;
}

export default $api;
