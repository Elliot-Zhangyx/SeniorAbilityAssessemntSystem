<template>
<div>
  <el-tabs v-model="activeName" type="card">
    <el-tab-pane label="评估指标设置" name="first">
      <div class="edit_dev">
        <el-transfer
            filterable
            :titles="['模块列表', '已选模块']"
            filter-placeholder="请输入模块名称"
            @change="handleChange"
            v-model="value" :data="module"
        ></el-transfer>
      </div>
      <br><br><br>
      <el-button size="medium" type="warning" style="margin-left: 35%">重置</el-button>
      <el-button size="medium" type="primary" @click="saveTarget">确定</el-button>
    </el-tab-pane>
    <el-tab-pane label="评估规则设置" name="second">
          <el-tabs type="card" :tab-position="tabPosition" >
            <el-tab-pane label="分数判断">
              <el-form :model="rankForm" ref="rankForm">
                <el-form-item
                    v-for="(rank, index) in rankForm.rankList"
                    :label="'设置'+index"
                    :key="rank.key"
                    :prop="'rankList.' + index + '.description'"
                    :rules="{
                        required: true, message: '等级不能为空', trigger: 'blur'
                     }"
                >
                  <div style="display:flex">
                    <el-input-number v-model="rank.rank" placeholder="请输入等级" type="number" :min="0" :max="10" style="flex:2;width: 10%">{{rank.rank}}</el-input-number>
                    <el-input v-model="rank.description" placeholder="请输入等级描述" style="flex:6"></el-input>
                    &nbsp;&nbsp;
                    <el-input-number v-model="rank.rankLeft" placeholder="起始范围" type="number" :min="0" :max="100" style="flex:2;width: 10%">{{rank.rankLeft}}</el-input-number>
                    &nbsp;&nbsp;-&nbsp;&nbsp;
                    <el-input-number v-model="rank.rankRight" placeholder="终点范围" type="number" :min="0" :max="100" style="flex:2;width: 10%">{{rank.rankRight}}</el-input-number>
                    <el-select v-model="rank.tag" placeholder="请选择等级颜色" style="flex:2;width: 10%">
                      <el-option
                          v-for="item in tagColorList"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        <span style="float: left"><el-tag :type="item.value">&emsp;&emsp;</el-tag></span>
                        <span style="float: right; color: #8492a6; font-size: 13px">{{item.label}}</span>
                      </el-option>
                    </el-select>
                    <el-button @click.prevent="removeDomain(rank)"  style="flex:1">删除</el-button>
                  </div>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm('rankForm')">提交</el-button>
                  <el-button @click="addDomain">新增选项</el-button>
                  <el-button @click="resetForm('rankForm')">重置</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="特殊情况">特殊情况</el-tab-pane>
          </el-tabs>
    </el-tab-pane>

  </el-tabs>

</div>
</template>

<script>
export default {
  name: "Target",
  data() {
    return {
      rank:0,
      tabPosition:'left',
      module: [],
      value: [],
      target:[],
      activeName: 'first',
      // filterMethod(query, item) {
      //   return item.pinyin.indexOf(query) > -1;
      // }
      rankForm:
      {
        rankList: [{
          rank: '',
          description:'',
          rankLeft:'',
          rankRight:'',
          tag:''
        }],
        id:0,
      },
      tagColorList:[
        {id:1,label:'success',value:'success'},
        {id:2,label:'primary',value:'primary'},
        {id:3,label:'info',value:'info'},
        {id:3,label:'warning',value:'warning'},
        {id:3,label:'danger',value:'danger'},
      ]
    };
  },
  mounted() {
    this.setModule()
    this.getModule()
    this.setRule()
  },
  methods:{
     getModule(){
       this.request.get("/module").then(res=> {
        res.data.forEach(item => {
          this.module.push({
            label: item.title,
            key: item.id,
            disabled: item.enable === false
          });
        });
      })
    },
    setModule(){
      this.request.get("/target").then(res=> {
        if(res.code==='200'){
          this.target = res.data
          for(let i=0;i<this.target.length;i++){
            this.value.push(this.target[i].targetId)
          }
        }
      })
    },
    setRule(){
      this.request.get("/rule").then(res=> {
        if(res.code==='200'){
          this.rankForm.rankList = res.data
        }
      })
    },
    handleChange(value){
      this.value = value;
    },
    saveTarget(){
       this.target= []
       for(let i=0;i<this.value.length;i++){
         this.target.push({
           'targetId':this.value[i]
         })
       }
      this.request.post("/target",this.target).then(res=> {
        if(res.code==='200'){
          this.$message.success('保存成功')
        }
        else{
          this.$message.error('保存失败')
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.rankForm.rankList.forEach(item=>{
            item.rank = item.rank.toString()
            item.rankleft = item.rankLeft.toString()
            item.rankRight = item.rankRight.toString()
            item.tag = item.tag.toString()
          })
          this.request.post("/rule/saveList",this.rankForm.rankList).then(res=>{
            if(res.code ==='200'){
              this.$message.success("保存成功")
            }
            else{
              this.$message.error("保存失败")
            }
          })
        }
      });
    },
    resetForm(formName) {
      if (this.$refs[formName]!==undefined) {
        this.$refs[formName].resetFields();
      }
    },
    removeDomain(item) {
      var index = this.rankForm.rankList.indexOf(item)
      if (index !== -1) {
        this.rankForm.rankList.splice(index, 1)
      }
    },
    addDomain() {
      this.rankForm.rankList.push({
        rank: '',
        description:'',
        rankLeft: '',
        rankRight: '',
        tag:''
      });
    }
  }
}
</script>
<style scoped>
.edit_dev{
  width: 100%;
  margin-top: 20px;
  margin-left: 10%;
}
.edit_dev >>> .el-transfer-panel {
  width:350px;
  height: 500px;
}
.edit_dev >>> .el-transfer-panel__body {
  height: 500px;
}
.edit_dev >>> .el-transfer-panel__list {
  height: 500px;
}
</style>