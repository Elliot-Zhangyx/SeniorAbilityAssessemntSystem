<template>
    <div class="wrapper">
        <div style=" margin:10% 0  0 65%;background-color: #fff; width: 350px; height: 400px;
        padding: 30px; border-radius: 10px">
<!--            /*opacity: 80%;*/-->
            <div style="margin: 20px 0; text-align: center; font-size: 24px" ><b>登 录</b></div>
            <el-form :model="user" :rules="rules" ref="userForm" >
                <el-form-item prop="username" style="margin-bottom: 10px">
                    <el-input size="medium" style="margin: 10px 0" v-model="user.username"
                              placeholder="请输入账号"
                    >
<!--                        prefix-icon="el-icon-user"-->
                        <template slot="prepend"><i class="el-icon-user"></i></template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password" >
                    <el-input size="medium" style="margin: 10px 0" show-password v-model="user.password">
<!--                        prefix-icon="el-icon-lock"-->
                        <template slot="prepend"><i class="el-icon-lock"></i></template>
                    </el-input>
                </el-form-item>
                <el-form-item style="margin: 10px 0; text-align: right">
                    <template>
                      <div>
                        <VCode
                            :show="isShow"
                            @success="success"
                            @close="close"
                        />
                        <el-button type="primary" size="medium"  autocomplete="off" @click="login"  style="width:100%;">登&emsp;录</el-button>
                      </div>
                    </template>
                    <div style="margin-top: 5px">
                        <el-checkbox v-model="checked" style="float: left">记住密码</el-checkbox>
                        &nbsp;
                        <a href="https://element.eleme.io" target="_blank" style="color: red"><i class="el-icon-search"></i>&nbsp;忘记密码?</a>
                    </div>
                    <div style="text-align: center;height: 50px;margin-top: 10px">
                       <span style="color: #606266;margin-top: 30px" >还没有账号？</span>
                        <a href="/register" target="_blank" style="color:#4d83ff">点击创建</a>
                    </div>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {setRoutes} from "@/router";
    import VCode from "vue-puzzle-vcode";
    export default {
        name: "Login",
        components:{
          VCode
        },
        data() {
            return {
                isShow:false,
                user: {},
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
                    ],
                },
                checked: true
            }
        },
        methods: {
            login() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {  // 表单校验合法
                      this.isShow = true;
                    }
                });
            },
            // 用户通过了验证
            success(msg){
              this.isShow = false; // 通过验证后，需要手动隐藏模态框

              //登录
              this.request.post("/user/login", this.user).then(res => {
                if(res.code === '200') {
                  localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息到浏览器
                  localStorage.setItem("menus",JSON.stringify(res.data.menus)) //存储用户信息的目录
                  this.$store.commit("setUser",localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {})
                  this.$store.commit("setMenus")

                  //动态设置当前路由
                  setRoutes()
                  this.$router.push("/")
                  this.$message.success('登陆成功')
                } else {
                  this.$message.error(res.msg)
                }
              })
            },
            // 用户点击遮罩层，应该关闭模态框
            close(){
              this.isShow = false;
            }
        }
    }
</script>

<style>
    .wrapper {
        height: 100vh;
        /*background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);*/
        /*background: #f3f3f3;*/
        background-image: url("../../public/img/login2.jpg") ;
        background-repeat:no-repeat;
        background-size:100% 100%;
        -moz-background-size:100% 100%;
        display:flex;
        overflow: hidden;
    }
    .login{
        color: #fff;
        background-color: #4d83ff;
        border-color: #4d83ff;
    }
    .register{
        color: #fff;
        background-color: #ffb63c;
        border-color: #ffb63c;

    }
    a{ text-decoration: none; }
    a:visited{ text-decoration: none; }
    a:hover { text-decoration: underline; }
    a:active{ text-decoration:none; }
</style>