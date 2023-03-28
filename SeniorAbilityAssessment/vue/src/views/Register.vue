<template>
  <div class="wrappers">
    <div style="margin: 100px auto; background-color: #fff;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); width: 60%; height: 70%; padding: 20px; border-radius: 10px;display:flex;overflow: hidden;justify-content: space-between">
      <div style="flex: 1;width: 100%;margin: 80px 50px">
        <el-tag type="danger" style="height: 50px;width: 100%;text-align: center"><span style="line-height: 50px">注意：一个账号仅支持一人评估，请确保姓名与身份证号属实</span></el-tag>
        <img src="../assets/register_left.png" alt="" style="display: block;width: 100%;margin-top: 60px">
      </div>
      <div style="flex:1;width: 100%;margin:10px 20px">
        <div style="margin: 20px 0; text-align: center; font-size: 24px;color: #606266;"><b>账 号 注 册</b></div>
        <el-form :model="user" :rules="rules" ref="userForm" style="flex:1;">
          <el-form-item prop="username">
            <el-input placeholder="请输入用户名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" autocomplete="false" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="nickname">
            <el-input placeholder="请输入你的真实姓名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user-solid" autocomplete="false"  v-model="user.nickname"></el-input>
          </el-form-item>
          <el-form-item prop="identityCard" style="margin-bottom: 20px">
            <el-input :maxlength="identityCardMaxLength" show-word-limit
                      size="medium" v-model="user.identityCard" placeholder="请输入本人的身份证号"  autocomplete="off" style="width: 100%">
              <template slot="prepend" style="width: 100%;">
                <el-select class="select_identityCard" v-model="selectIdentityCard" slot="prepend" placeholder="请选择" @change="identityCardNumChange">
                  <el-option label="18位" value="18" ></el-option>
                  <el-option label="15位" value="15"></el-option>
                </el-select>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input placeholder="请输入你的电话号码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-phone" autocomplete="false"  v-model="user.phone"></el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input placeholder="请输入你的邮箱" size="medium" style="margin: 5px 0" prefix-icon="el-icon-platform-eleme" autocomplete="false"  v-model="user.email"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" autocomplete="false" show-password v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" autocomplete="false" show-password v-model="user.confirmPassword"></el-input>
          </el-form-item>
          <template>
            <div>
              <VCode
                  :show="isShow"
                  @success="success"
                  @close="close"
              />
              <el-button type="primary" size="medium"  autocomplete="off" @click="register"  style="width:100%;">点击注册</el-button>
            </div>
          </template>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import VCode from "vue-puzzle-vcode";
import {setRoutes} from "@/router";

export default {
  name: "Register",
  components:{
    VCode
  },
  data() {
    var validateIdentityCard = (rule, value, callback)=>{
      let _IDRe18 = /^([1-6][1-9]|50)\d{4}(18|19|20)\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
      let _IDRe15 = /^([1-6][1-9]|50)\d{4}\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}$/
      let org_birthday = '';
      let birthday = '';
      if(!value) {
        return callback(new Error('身份证号不能为空'));
      }
      else{
        if (this.identityCardMaxLength === 18) {
          if(_IDRe18.test(value)) {
            org_birthday = value.substring(6, 14);
            birthday =
                org_birthday.substring(0, 4) +
                "-" +
                org_birthday.substring(4, 6) +
                "-" +
                org_birthday.substring(6, 8);
            if (this.checkDate(birthday) === false) {
              callback(new Error("身份证中的日期不正确！请输入正确的身份证号"));
            } else {
              callback();
            }
          }
          else{
            callback(new Error("请输入正确的身份证号"));
          }
        } else {
          if(_IDRe15.test(value)) {
            org_birthday = this.form.identityCard.substring(6, 12);
            birthday =
                '19' + org_birthday.substring(0, 2) +
                "-" +
                org_birthday.substring(2, 4) +
                "-" +
                org_birthday.substring(4, 6);
            if (this.checkDate(birthday) === false) {
              callback(new Error("身份证中的日期不正确！请输入正确的身份证号"));
            } else {
              callback();
            }
          }
          else{
            callback(new Error("请输入正确的身份证号"));
          }
        }
      }
    }
    var validateConfirmPWD= (rule, value, callback)=>{
      if(value !== this.user.password){
        callback(new Error("两次输入的密码不一致"))
      }
      else{
        callback();
      }
    }
    return {
      isShow: false, // 验证码模态框是否出现
      identityCardMaxLength:18,
      user: {},
      selectIdentityCard:'18',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' },
          { validator: validateConfirmPWD,trigger: 'blur'}
        ],
        identityCard:[
          { required: true, message: '身份证号不能为空', trigger: 'blur' },
          { validator: validateIdentityCard,trigger: 'blur'}
        ],
        phone:[

        ],
        email:[

        ],
      }
    }
  },
  methods: {
    register() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.isShow = true;
        }
      });
    },
    identityCardNumChange(vid){
      this.user.identityCard = ''
      if(vid === '15') this.identityCardMaxLength = 15
      else this.identityCardMaxLength = 18
    },
    checkDate(value) {
      let days = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
      let YMD = value.split("-");
      let y = parseInt(YMD[0], 10);
      let m = parseInt(YMD[1], 10);
      let d = parseInt(YMD[2], 10);
      //判断月份是否合法
      if (m > 12 || m < 1) {
        return false;
      }
      //判断是否是闰年
      if ((y % 4 === 0 && y % 100 !== 0) || (y % 400 === 0)) {
        days[2] = 29;
      }
      //判断当月天数是否合法
      return d <= days[m];
    },
    // 用户通过了验证
    success(msg){
      this.isShow = false; // 通过验证后，需要手动隐藏模态框
      this.finishRegister()
    },

    // 用户点击遮罩层，应该关闭模态框
    close(){
      this.isShow = false;
    },
    finishRegister(){
      this.request.post("/user/register", this.user).then(res => {
        if(res.code === '200') {
          this.$message.success("注册成功")
          this.skipLogin()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    skipLogin(){
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
    }
  }
}
</script>

<style>
.wrappers {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);
  /*background-image: url("../assets/register_bg.jpg");*/
  overflow: hidden;
}
.select_identityCard{
  width: 80px;
  text-align: center;
}
</style>