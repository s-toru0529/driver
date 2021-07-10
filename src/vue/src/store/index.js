import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        userName: "",
        userToken: ""
    },
    mutations: {
        updateUser(state, user) {
            state.userName = user.userName;
            state.userToken = user.userToken;
        }
    },
    actions: {
        auth(context, user) {
            context.commit('updateUser', user);
        }
    },
    modules: {},
})

export default store