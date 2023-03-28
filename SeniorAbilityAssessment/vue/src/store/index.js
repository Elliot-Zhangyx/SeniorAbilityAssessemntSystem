import Vue from 'vue'
import Vuex from 'vuex'
import router, {resetRouter} from "@/router"
Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        currentPathName: '',
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        menus:localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : {}
    },
    mutations: {
        setPath (state) {
            state.currentPathName = localStorage.getItem("currentPathName")
        },
        setUser(state,res){
            state.user = res
        },
        setMenus(state){
            state.menus = localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : {}
        },
        logout(){
            //清空缓存
            localStorage.removeItem("user")
            localStorage.removeItem("menus")
            router.push("/login")

            //重置路由
            resetRouter()
        }
    }
})
export default store