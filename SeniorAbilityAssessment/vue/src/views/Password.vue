  <template>
  <el-card style="width: 500px;">
    <el-form label-width="120px" size="small" :model="form" :rules="rules" ref="pass">
      <el-form-item label="原密码" prop="password">
        <el-input v-model="form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-card>
</template>

<script>
export default {
  name: "Password",
  data(){
    var validateSurePassword = (rule, value, callback) => {
      if (value !== '') {
        if (value !== this.form.newPassword) {
          callback(new Error('两次输入的新密码不一致'))
          return false
        } else {
          callback()
        }
      }
    }
    return{
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) :{},
      rules: {
        password: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
          { min: 3, message: '长度不少于3位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, message: '长度不少于3位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { min: 3,validator: validateSurePassword, trigger: 'blur' }
        ],
      },
    }
  },
  created() {
    this.form.username  = this.user.username
  },
  methods:{
    save() {

      this.$refs['pass'].validate((valid) => {
        if (valid) {
          // 表单校验合法
          this.request.post("/user/password", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("修改成功")
              this.$store.commit("logout")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  },

}
</script>

<style scoped>

</style>