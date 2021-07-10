import Vue from 'vue'
import VueRouter from 'vue-router'

// compornent
import Signin from '../views/Signin.vue'
import Home from '../views/Home.vue'
import NotFound from '../views/NotFound.vue'

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
    },
    {
        name: 'NotFound',
        path: '*',
        component: NotFound,
    }

]

const router = new VueRouter({
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth) && !Store.state.userToken) {
        next({ path: '/signin' });
    } else {
        next();
    }
});

export default router