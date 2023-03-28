import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import el from "element-ui/src/locale/lang/el";

Vue.use(VueRouter)

const routes = [
  {
    path: '/saa_home',
    name: 'saa_home',
    component: () => import('../views/front/Home.vue')
  },
  {
    path: '/freeassess',
    name: 'FreeAssess',
    component: () => import('../views/FreeAssess.vue')
  },
  {
    path: '/applydetails',
    name: '申请详情',
    component: () => import('../views/info/ApplyDetails.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  },

]

const router = new VueRouter({ //解决冗余导航
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export const resetRouter = ()=>{
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}
//刷新页面会重置路由
export const setRoutes = () =>{
  const storeMenus = localStorage.getItem("menus")
  if(storeMenus){
    const assess = {
      path: '/abilityassess/:assessedId',
      name: 'AbilityAssess',
      component: () => import('../views/AbilityAssess.vue')
    }
    const assessEdit = {
      path: '/assessedit/:assessId',
      name: 'AssessEdit',
      component: () => import('../views/AssessEdit.vue')
    }
    const currentRoutes = router.getRoutes()
    if(!currentRoutes.includes("Manage")){
      const menus = JSON.parse(storeMenus)
      //拼装动态路由
      const manageRoute = {path: '/', name: 'Manage', component: () => import('../views/Manage.vue'), redirect:"/home", children:[
          {path:"Person",name:"个人信息",component:()=>import('../views/Person')},
          {path:"Password",name:"修改密码",component:()=>import('../views/Password')}]}
      menus.forEach(item =>{
        if(item.path){ //当且仅当path不为空的时候才去设置路由
          let itemMenu = {path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+item.pagePath+'.vue')}
          manageRoute.children.push(itemMenu)
        }
        else if(item.children.length){
          item.children.forEach(item=>{
            if(item.path){
              let itemMenu = {path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+item.pagePath+'.vue')}
              manageRoute.children.push(itemMenu)
            }
          })

        }
      })
      //动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
      router.addRoute(assessEdit)
      router.addRoute(assess)
    }
  }
}

setRoutes()


// const originalPush = VueRouter.prototype.push
//
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err)
// }

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新

  // 未找到路由的情况
  if(!to.matched.length){
    const storeMenus = localStorage.getItem("menus")
    if(storeMenus){
      next("/404")  // 放行路由
    }
    else {
      //跳回登录
      next("/login")
    }
  }
  next()
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, resolve, reject) {
  if ( resolve || reject ) return originalPush.call(this, location, resolve, reject)
  return originalPush.call(this, location).catch((e)=>{})
}


export default router
