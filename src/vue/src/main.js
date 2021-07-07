import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from '@/plugins/vuetify' // path to vuetify export

Vue.prototype.$axios = axios

new Vue({
    store,
    router,
    vuetify,
    render: h => h(App)
}).$mount('#app')
