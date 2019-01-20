import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
// import HelloChina from '@/components/HelloChina'
import Note from '@C/Note'
import Login from '@C/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Login
    },
    {
      path: '/note',
      component: Note
    }
    // {
    //   path: '/',
    //   // name: 'HelloWorld',
    //   component: HelloWorld
    // },
    // {
    //   path: '/china',
    //   // name: 'HelloChina',
    //   component: HelloChina
    // }
  ]
})
