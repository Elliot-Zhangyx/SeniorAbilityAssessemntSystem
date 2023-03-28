<template>
  <div class="home">

      <el-backtop target=".home">
        <i class="el-icon-caret-top"></i>
      </el-backtop>
          <el-menu
              :default-active="activeIndex2"
              class="el-menu-demo"
              mode="horizontal"
              @select="handleSelect"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b"
          >
            <el-menu-item index="1">首页</el-menu-item>
            <el-menu-item index="3">消息中心</el-menu-item>
            <el-menu-item style="float: right"><a href="/login" target="_blank">登录/注册</a></el-menu-item>
            <el-menu-item style="float: right">
              <template slot="title"><a href="/" target="_blank">我的工作台</a></template>
            </el-menu-item>
          </el-menu>
          <div class="line"></div>
          <div >
            <el-carousel height="850px">
              <el-carousel-item>
                <h3><img src="../../assets/home_senior3.jpg"  alt="" style="width: 100%;"></h3>
              </el-carousel-item>
              <el-carousel-item>
                <h3><img src="../../assets/home_senior2.jpeg" alt="" style="width: 100%;"></h3>
              </el-carousel-item>
            </el-carousel>
          </div>

          <div style="display: flex;width: 60%;margin:10% auto">
            <el-card :body-style="{ padding: '0px'}" style="flex: 1;width: 20%;height: 410px;border: 1px solid #eaeefb;border-radius: 5px;"  shadow="hover">
              <img src="../../assets/apply.png" class="image" alt="" style="margin:23px 0;">
              <div style="text-align: center">
                <h3 style="display: block;">申请指南</h3>
                <p>了解申请指南，帮助老年人申请评估，快速了解评估流程。</p>
              </div>
              <div style="float: bottom;margin: 0;padding: 0;">
                <a href="/applydetails" class="buttonLook">查看详情</a>
              </div>
            </el-card>
            <el-card :body-style="{ padding: '0px'}" style="flex: 1;width: 20%;height: 410px;margin-left: 50px;border: 1px solid #eaeefb;border-radius: 5px;"  shadow="hover">
              <img src="../../assets/assess.png" class="image" alt="" style="margin:23px 0;">
              <div style="text-align: center">
                <h3 style="display: block;">免费试用</h3>
                <p>免费帮助老年人能力评估，快速了解当下身体状况。</p>
              </div>
              <div style="float: bottom;margin: 0;padding: 0;">
                <a href="/freeassess" target="_blank" class="buttonLook">查看详情</a>
              </div>
            </el-card>
            <el-card :body-style="{ padding: '0px'}" style="flex: 1;width: 20%;height: 410px;margin-left: 50px;border: 1px solid #eaeefb;border-radius: 5px;"  shadow="hover">
              <img src="../../assets/otherTest.png" class="image" alt="" style="margin:23px 0;">
              <div style="text-align: center">
                <h3 style="display: block;">其他测评</h3>
                <p>可进行其他测评，了解老年人其他能力，&emsp;如：老年抑郁量表(GDS-30)。</p>
              </div>
              <div style="float: bottom;margin: 0;padding: 0;">
                <a href="#" class="buttonLook">查看详情</a>
              </div>
            </el-card>
          </div>
          <br>
          <br>
    <el-footer style="height: 50px;background-color:#f7fbfd;text-align: center"><span style="font-size: small;color: #99a9bf;line-height: 50px">©CopyRight 2023-2045 All Rights Reserved. 张雨轩 版权所有</span></el-footer>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.image {
  width: 100%;
  display: block;
}
.buttonLook{
  height: 53px;
  line-height: 52px;
  font-size: 14px;
  color: #409eff;
  text-align: center;
  border: 0;
  border-top: 1px solid #eaeefb;
  padding: 0;
  cursor: pointer;
  width: 100%;
  bottom: 0;
  left: 0;
  background-color: #fff;
  border-radius: 0 0 5px 5px;
  transition: all .3s;
  text-decoration: none;
  display: block;
}
a{
  text-decoration: none;
}
.buttonLook:hover {
  background-color : #409eff ;
  color: #fff;
}
p{
  padding: 0 25px;margin:15px 0;font-size: small;color:#99a9bf
}
.home{
  height:100vh ;/*height可以根据你要创建的页面进行修改*/
  overflow-x: hidden;
  overflow-y: scroll;
}
</style>