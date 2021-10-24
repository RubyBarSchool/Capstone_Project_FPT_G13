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
        }
    },
    getters: {

    },
    mutations: {
        changeMessage(state, message) {
            state.message = message
        },
        removeMessage(state) {
            state.message.type = "";
            state.message.message = "";
            state.message.description = "";
        }
    },
    actions: {
        handleChangeMessage(context, message) {
            context.commit('changeMessage', message)
        },
        remove(context) {
            context.commit('removeMessage')
        }
    }
});