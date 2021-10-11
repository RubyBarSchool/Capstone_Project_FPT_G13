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
        throw "403"
    }
    if (user != null) {
        config.headers['Authorization'] = authHeader()
        return config
    }
}, error => {
    Promise.reject(error)
})

// respone interceptor
service.interceptors.response.use(
    response => {
        // loading.hide(response.config)
        // const res = response.data;
        // if (res.statusCode !== 200) {
        //     Notify.create({
        //         message: res.msg
        //     })
        //     return Promise.reject('error');
        // } else {
        return response;
        // }
    },
    error => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('user')
            setTimeout(() => {
                router.push('login');
            }, 1000)
        }
        //  else if (error.response && error.response.status === 500) {
        //     Notify.create({
        //         message: this.$t('System error') + '!',
        //         position: 'bottom-right'
        //     })
        // } else if (error.message.indexOf("timeout") > -1) {
        //     Notify.create({
        //         message: this.$t('Network timeout') + '!',
        //         position: 'bottom-right'
        //     })
        // } 
        else if (error.response && error.response.status === 403) {
            localStorage.removeItem('user')
            setTimeout(() => {
                router.push('login');
            }, 1000)
        }
        //  else {
        //     Notify.create({
        //         message: this.$t('Network error') + '!',
        //         position: 'bottom-right'
        //     })
        // }
        return Promise.reject(error)

    })

export default service