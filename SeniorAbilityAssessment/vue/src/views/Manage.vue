<template>
  <div class="manage">
    <el-backtop target=".manage">
      <i class="el-icon-caret-top"></i>
    </el-backtop>
      <el-container style="min-height: 100vh;background: #ffffff;">
          <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);
          box-shadow:2px 0 6px rgba(0,21,41,0.15);min-height: 100%;overflow-x: hidden;">
            <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>
          </el-aside>

          <el-container>
              <el-header style="box-shadow:2px 0 6px rgba(0,21,41,0.15);border-bottom: 1px solid #ccc;">
                  <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>
              </el-header>
              <el-main class="app-main">
<!--                  表示当前页面子路由会在 <router-view />里面展示-->
                  <router-view />
              </el-main>
          </el-container>
      </el-container>
  </div>
</template>

<script>

// @ is an alias to /src
import Aside from "@/components/Aside"
import Header from "@/components/Header"
export default {
  components:{
      Aside,
      Header,
  },
  name: 'Home',
  data(){
      return {
          collapseBtnClass:'el-icon-s-fold',
          isCollapse:false,
          sideWidth:240,
          logoTextShow:true,
      }
  },
  methods:{
      collapse(){//点击收缩按钮触发
          this.isCollapse = !this.isCollapse
          if(this.isCollapse){//收缩
              this.sideWidth = 64
              this.collapseBtnClass = 'el-icon-s-unfold'
              this.logoTextShow = false
          }
          else{//展开
              this.sideWidth = 240
              this.collapseBtnClass = 'el-icon-s-fold'
              this.logoTextShow = true
          }
      },
  },
}
</script>
<style>
.manage{
  height:100vh ;/*height可以根据你要创建的页面进行修改*/
  overflow-x: hidden;
  overflow-y: scroll;
}
</style>
