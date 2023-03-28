<template>
 <div>
     <el-form :model="optionForm" ref="optionForm" label-width="100px">
       <el-form-item
           prop="subjectTitle"
           label="题目"  :rules="[
      {required: true, message: '请输入题目', trigger: 'blur' }]">
         <el-input v-model="optionForm.subjectTitle"></el-input>
       </el-form-item>
       <el-form-item
           prop="subjectAttention"
           label="注意事项">
         <el-input v-model="optionForm.subjectAttention"></el-input>
       </el-form-item>
       <el-form-item
           v-for="(option, index) in optionForm.optionList"
           :label="'选项' + index"
           :key="option.key"
           :prop="'optionList.' + index + '.subjectOption'"
           :rules="{
               required: true, message: '选项不能为空', trigger: 'blur'
            }"
       >
         <div style="display:flex">
           <el-input v-model="option.subjectOption" placeholder="请输入选项" style="flex:8" @input="splitGetScore(option)"></el-input>
           <el-input v-model="option.score" placeholder="请输入分数" style="flex:2"></el-input>
           <el-select v-model="option.rankChange" placeholder="请选择" style="flex:2">
             <el-option
                 v-for="item in rankChangeList"
                 :key="item.value"
                 :label="item.label"
                 :value="item.value">
             </el-option>
           </el-select>
           <el-button @click.prevent="removeDomain(option)"  style="flex:1">删除</el-button>
         </div>
       </el-form-item>
       <el-form-item>
         <el-button type="primary" @click="submitForm('optionForm')">提交</el-button>
         <el-button @click="addDomain">新增选项</el-button>
         <el-button @click="resetForm('optionForm')">重置</el-button>
       </el-form-item>
     </el-form>
 </div>
</template>

<script>
export default {
  name: "Option",
  props:{
    moduleId:{
      type:Number,
      default: 0
    },
    optionForm:{
      type:Object,
      default:{
        optionList: [{
          subjectOption: '',
          score:'',
          rankChange:'0'
        }],
        subjectTitle: '',
        subjectAttention:'',
        moduleId:0,
      }
    },
  },
  created() {
    console.log(this.optionForm.optionList[0].rankChange)
  },
  data(){
    return{
      rankChangeList: [{
        value: '0',
        label: '无'
      }, {
        value: '1',
        label: '提升1级'
      }, {
        value: '2',
        label: '提升2级'
      }, {
        value: 'MAX',
        label: '直接满级'
      }],
    }
  },
  methods:{
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.optionForm.moduleId = this.moduleId
          this.request.post("/subject",this.optionForm).then(res=>{
            if(res.code ==='200'){
              this.$message.success("保存成功")
              this.$emit('closeOptionEvent',false)
              this.$emit('load');
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
      var index = this.optionForm.optionList.indexOf(item)
      if (index !== -1) {
        this.optionForm.optionList.splice(index, 1)
      }
    },
    addDomain() {
      this.optionForm.optionList.push({
        subjectOption: '',
        score:'',
        rankChange:'0'
      });
    },
    splitGetScore(option){

      if(option.score==='') this.request.get("/option/split/score",{params:{content:option.subjectOption}}).then(res=>{
        if(res.code ==='200'){
          option.score = res.data
        }
      })
    }
  }
}
</script>

<style scoped>

</style>