<template>
    <el-menu
            style="height: 100%;overflow-x: hidden"
            :default-openeds="opens"
            text-color="#000"
            :collapse="isCollapse"
            :collapse-transition="false"
            :default-active="defaultActive"
            router
>
    <!--                  :default-openeds="['1', '3']"-->
        <!--                       :collapse-transition="true"-->
        <!--                       background-color="rgb(48,65,86)"-->
        <!--                       text-color="#fff"-->
        <!--                       active-text-color="#ffd04b"-->
        <div style="height: 60px;box-shadow:2px 0 6px rgba(0,21,41,0.15);line-height: 60px;text-align: center;border-bottom: 1px solid #ccc;">
            <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top:5px;margin-right: 5px">
            <b v-show="logoTextShow">后台管理系统</b>
        </div>
        <div v-for="item in menus" :key="item.id">
          <div v-if="item.path">
            <el-menu-item :index="item.path" style="border-bottom: 1px solid #e3e3e3;">
              <i :class="item.icon" ></i>
              <span slot="title" class="ml-10">{{ item.name }}</span>
            </el-menu-item>
          </div>
          <div v-else>
            <el-submenu :index="String(item.id)" style="border-bottom: 1px solid #e3e3e3;">
              <template slot="title"><i :class="item.icon"></i>
                <span slot="title" class="ml-10">{{ item.name }}</span>
              </template>
              <div v-for="subItem in item.children" :key="subItem.id">
                <el-menu-item :index="subItem.path">
                  <template slot="title"><i :class="subItem.icon"></i>
                    <span slot="title" class="ml-10" >{{subItem.name}}</span></template>
                </el-menu-item>
              </div>
            </el-submenu>
          </div>
        </div>
    </el-menu>
</template>

<script>
    export default {
        name: "",
        props:{
            isCollapse:Boolean,
            logoTextShow: Boolean
        },
        watch: {
            $route () {
                this.setCurrentRoute()
            }
        },
        computed:{
          menus(){
            return this.$store.state.menus
          }
        },
        data(){
            return{
                defaultActive: '/home',
                // menus:this.$store.state.user.menus
                opens:this.$store.state.menus.map(v => v.id + '')
            }
        },
        created () {
            this.setCurrentRoute()
        },
        methods: {
            setCurrentRoute() {
                console.log(this.$route.path)
                this.defaultActive = this.$route.path  //关键  通过他就可以监听到当前路由状态并激活当前菜单
                console.log(this.defaultActive)
            }
        }
    }
</script>

<style>
/*由于 element-ui 的<el-menu>标签本身希望里面嵌套的是<el-menu-item>,<el-submenu>,<el-menu-item-group>之一，但是却嵌套了<div>,而导致收折就隐藏不了文字*/
/*隐藏文字*/
.el-menu--collapse  .el-submenu__title span{
  display: none;
}
/*隐藏 > */
.el-menu--collapse  .el-submenu__title .el-submenu__icon-arrow{
  display: none;
}
</style>