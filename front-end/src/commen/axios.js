import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:8081/',
    timeout: 20000,
    headers: { 'Content-Type': 'application/json' }
});

instance.interceptors.request.use(
    (config) => {
        if (!config.url.includes('auth/login') && 
            !config.url.includes('auth/register') && 
            !config.url.includes('auth/verify-otps')) {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers['Authorization'] = `Bearer ${token}`;
            }
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default instance;


