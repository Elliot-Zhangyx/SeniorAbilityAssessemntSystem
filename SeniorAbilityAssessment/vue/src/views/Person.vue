<template>
  <el-card style="width: 800px;">
    <el-form label-width="80px" size="small">
        <el-upload
            class="avatar-uploader"
            :action="'http://'+serverIp+':9090/file/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off" disabled></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input :maxlength="identityCardMaxLength" show-word-limit
                  size="medium" v-model="form.identityCard" placeholder="请输入本人的身份证号"  autocomplete="off" style="width: 90%" :disabled="disableIdCard">
          <template slot="prepend" style="width: 100%;">
            <el-select class="select_identityCard" v-model="identityCardMaxLength.toString()" slot="prepend" placeholder="请选择" @change="identityCardNumChange">
              <el-option label="18位" value="18" ></el-option>
              <el-option label="15位" value="15"></el-option>
            </el-select>
          </template>
        </el-input>
        <el-button type="success" class="ml-10" @click="disableIdCard = true" v-if="disableIdCard === false">保存</el-button>
        <el-button type="primary" class="ml-10" @click="disableIdCard = false" v-if="disableIdCard === true ">编辑</el-button>
      </el-form-item>
      <el-form-item label="地址">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-card>
</template>

<script>
import {serverIp} from "../../public/config";

export default {
  name: "Person",
  data() {
    return {
      serverIp:serverIp,
      form: {},
      disableIdCard:true,
      identityCardMaxLength:18,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      const username = this.user.username
      if(!username){
        this.$message.error("当前无法获取用户信息")
        return
      }
      this.getUser().then(res => {
        console.log(res)
        this.form = res
        this.identityCardMaxLength = res.identityCard.length
      })
    },
    async getUser() {
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          // 更新浏览器存储的用户信息
          this.getUser().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
            this.$store.commit('setUser',res)
          })
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    },
    identityCardNumChange(vid){
      if(vid === '15') this.identityCardMaxLength = 15
      else this.identityCardMaxLength = 18
    },
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 300px;
  height: 300px;
  display: block;
}
.select_identityCard{
  width: 80px;
  text-align: center;
}
</style>