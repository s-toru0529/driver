import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from '@/plugins/vuetify'
import '@mdi/font/css/materialdesignicons.css'

Vue.prototype.$axios = axios;

new Vue({
    store,
    router,
    vuetify,
    render: h => h(App)
}).$mount('#app')

export default new vuetify({
    icons: {
        iconfont: 'mdi', // アイコン利用のため
    },
})
