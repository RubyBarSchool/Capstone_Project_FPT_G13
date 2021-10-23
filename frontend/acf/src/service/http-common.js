import axios from "axios";
import authHeader from './auth-header';
import { router } from '../router';
const service = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-type": "application/json"
    }
});

service.interceptors.request.use(config => {
    // Do something before request is sent
    let user = JSON.parse(localStorage.getItem('user'))
    if (user == null || user.roles.lenght === 0) {
        throw "401"
    }
    if (user != null) {
        config.headers['Authorization'] = authHeader()
        return config
    }
}, error => {
    Promise.reject(error)
})

service.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('user')
            setTimeout(() => {
                router.push('login');
            }, 1000)
        } else if (error.response && error.response.status === 403) {
            router.push('/pageforbiden').catch(() => {});
        }
        return Promise.reject(error)

    })

export default service