import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import request from "@/utils/request";
import store from './store'
import '@/iconfont/iconfont.css'
// import Fragment from 'vue-fragment'
// import pubsub from 'pubsub-js'


// Vue.use(Fragment.Plugin)
// Vue.use(pubsub)
Vue.config.productionTip = false
Vue.use(ElementUI,{size:"small"});
Vue.prototype.request=request
new Vue({
  router,
  store,
  request,
  render: h => h(App)
}).$mount('#app')
