import axios from "axios";
import authHeader from './auth-header';
import { router } from '../router';
import state from '../store/index'
const service = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-type": "application/json"
    }
});

service.interceptors.request.use(config => {
    // Do something before request is sent
    let user = JSON.parse(localStorage.getItem('user'))
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
            let message = {
                type: "error",
                message: "Hết hạn tài khoản",
                description: "Xin hãy đăng nhập lại"
            }
            state.dispatch('handleChangeMessage', message)

            if (router.currentRoute.path !== '/login') {
                router.push('/login');
            }
        } else if (error.response && error.response.status === 403) {
            if (router.currentRoute.path !== '/pageforbiden') {
                router.push('/pageforbiden');
            }
        }
        return Promise.reject(error)

    })

export default service