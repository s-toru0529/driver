import Vue from 'vue'
import VueRouter from 'vue-router'

// compornent
import Signin from '../views/Signin.vue'
import Home from '../views/Home.vue'

// store
import Store from '@/store/index.js'

Vue.use(VueRouter)

const routes = [
    {
        name: 'Signin',
        path: '/signin',
        component: Signin
    },
    {
        name: 'Home',
        path: '/',
        component: Home,
        meta: { requiresAuth: true }
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth) && !Store.state.userToken) {
        next({ path: '/signin', query: { redirect: to.fullPath } });
    } else {
        next();
    }
});

export default router