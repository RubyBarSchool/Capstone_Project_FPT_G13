import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/';

class RoleService {
    getAllAccount(user) {
        return axios.post(API_URL + 'spadmin/account/allaccount', user, { headers: authHeader() });
    }

    searchAccount(name){
        return axios.post(API_URL + 'spadmin/account/searchaccount', name, { headers: authHeader() });
    }

}

export default new RoleService();