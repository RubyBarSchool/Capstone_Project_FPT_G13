import Vue from 'vue';
import Vuex from 'vuex';

import { auth } from './auth.module';

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        auth
    },
    state: {
        message: {
            type: "",
            message: "",
            description: "",
        },
        listUser: [],
        url: ""
    },
    getters: {
        url: state => state.url,
    },
    mutations: {
        changeMessage(state, message) {
            state.message = message;
        },
        removeMessage(state) {
            state.message.type = "";
            state.message.message = "";
            state.message.description = "";
        },
        changeListUser(state, listUser) {
            state.listUser = listUser;
        },
        changeUrlNotification(state, url) {
            state.url = url;
        },
    },
    actions: {
        handleChangeMessage(context, message) {
            context.commit('changeMessage', message)
        },
        remove(context) {
            context.commit('removeMessage')
        },
        handleChangeUserLogin(context, listUser) {
            context.commit('changeListUser', listUser)
        },
        urlNotification(context, url) {
            context.commit('changeUrlNotification', url)
        },
    }
});