<template>
<!--  <el-dialog width="90%" title="信息填写" :visible.sync="dialogSeniorVisible">-->
    <div style="display:flex;overflow: auto;width: 100%">
       <el-card class="box-card">
         <el-form  ref="seniorForm" :rules="rules" :model="form" style="padding: 20px 30px;"  label-width="300px">
           <el-divider><b>A.2 评估对象基本信息表</b></el-divider>
           <el-form-item label="A.2.1 姓名" prop="assessedName">
             <el-input  maxlength="20" show-word-limit size="medium" v-model="form.assessedName" placeholder="请输入真实姓名" style="width: 90%"></el-input>
           </el-form-item>
           <br>
           <el-form-item label="A.2.2 身份证号" prop="identityCard" style="margin-bottom: 20px">
             <el-input :maxlength="identityCardMaxLength" show-word-limit @input="identityCardChange"
                       size="medium" v-model="form.identityCard" placeholder="请输入内容"  autocomplete="off" style="width: 90%">
                     <template slot="prepend" style="width: 100%;">
                       <el-select class="select_identityCard" v-model="select" slot="prepend" placeholder="请选择" @change="identityCardNumChange">
                         <el-option label="18位" value="18" ></el-option>
                         <el-option label="15位" value="15"></el-option>
                       </el-select>
                     </template>
             </el-input>
           </el-form-item>
           <el-form-item label="A.2.3 性别" prop="gender" style="margin-bottom: 20px">
             <el-radio-group v-model="form.gender">
               <el-radio label="男" :disabled="gender_disabled_1" border>男</el-radio>
               <el-radio label="女" :disabled="gender_disabled_2" border>女</el-radio>
             </el-radio-group>
           </el-form-item>
           <el-form-item label="A.2.4 出生日期" prop="birthday" style="margin-bottom: 20px">
             <el-input v-model="form.birthday" :disabled="true" placeholder="请输入出生日期" style="width: 90%"></el-input>
           </el-form-item>
           <el-form-item label="A.2.5 年龄" prop="age" style="margin-bottom: 20px">
             <el-input show-word-limit
                       size="medium" v-model="form.age" placeholder="请输入年龄"  autocomplete="off" style="width: 90%" :disabled="true" readonly ></el-input>
           </el-form-item>
           <div style="display: flex">
             <el-form-item label="A.2.6 身高" prop="height" style="margin-bottom: 20px;flex: 1">
               <el-input-number size="medium" v-model="form.height" :min="100" placeholder="请输入身高" :max="300" style="width: 50%"></el-input-number>&emsp;（单位：cm）
             </el-form-item>
             <el-form-item label="A.2.7 体重" prop="weight" style="margin-bottom: 20px;flex: 1">
               <el-input-number size="medium" v-model="form.weight" :min="1" :max="200" placeholder="请输入体重" style="width: 50%"></el-input-number>&emsp;（单位：Kg）
             </el-form-item>
           </div>
           <div style="display:flex;">
             <el-form-item label="A.2.7 民族" prop="nationality" style="margin-bottom: 20px;flex: 1">
               <el-select v-model="form.nationality" filterable placeholder="请选择民族" style="width: 75%;">
                 <el-option v-for="item in nationList" :key="item.value" :label="item.info" :value="item.value"></el-option>
               </el-select>
             </el-form-item>
             <el-form-item label="A.2.8 文化程度" prop="educationLevel" style="margin-bottom: 20px;flex: 1">
               <el-select v-model="form.educationLevel" filterable placeholder="请选择文化程度" style="width: 75%;">
                 <el-option v-for="item in educationList" :key="item.value" :label="item.label" :value="item.value"></el-option>
               </el-select>
             </el-form-item>
           </div>
           <div style="display:flex;">
             <el-form-item label="A.2.9 宗教信仰" prop="religion" style="margin-bottom: 20px;flex: 1">
               <el-select v-model="form.religion" filterable placeholder="请选择宗教信仰" style="width: 75%">
                 <el-option v-for="item in religionList" :key="item.value" :label="item.info" :value="item.value" >
                   <span style="float: left">{{ item.info }}</span>
                   <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                 </el-option>
               </el-select>
             </el-form-item>
             <el-form-item label="A.2.10 婚姻状况" prop="maritalStatus" style="margin-bottom: 20px;flex: 1">
               <el-select v-model="form.maritalStatus" filterable placeholder="请选择婚姻状况" style="width: 75%;">
                 <el-option v-for="item in maritalList" :key="item.value" :label="item.label" :value="item.value"></el-option>
               </el-select>
             </el-form-item>
           </div>
           <el-form-item label="A.2.11 居住情况" prop="residenceStatus" style="margin-bottom: 20px;flex: 1">
             <el-select v-model="form.residenceStatus" filterable placeholder="请选择居住情况" style="width: 75%;">
               <el-option v-for="item in residenceList" :key="item.value" :label="item.label" :value="item.value"></el-option>
             </el-select>
           </el-form-item>
           <el-form-item label="A.2.12 医疗费用支付方式"  prop="medicalPayments" style="margin-bottom: 20px;flex: 1">
             <el-select v-model="medicalPayments" @change="medicalPaymentsChange" multiple :multiple-limit="4" filterable allow-create default-first-option placeholder="请选择医疗费用支付方式(输入框输入可添加新支付方式)" style="width: 75%;">
               <el-option v-for="item in medicalPaymentsList" :key="item.value" :label="item.label" :value="item.value"></el-option>
             </el-select>
             <span style="color: red;text-align: center; opacity: 80%; font-size: 12px">    注:可添加新选项</span>
           </el-form-item>
           <el-form-item label="A.2.13 经济来源 "  prop="financialSource" style="margin-bottom: 20px;flex: 1">
             <el-select v-model="financialSource" @change="financialSourceChange" multiple :multiple-limit="4" filterable allow-create default-first-option placeholder="请选择经济来源(输入框输入可添加新经济来源)" style="width: 75%;">
               <el-option v-for="item in financialSourceList" :key="item.value" :label="item.label" :value="item.value"></el-option>
             </el-select>
             <span style="color: red;text-align: center; opacity: 80%; font-size: 12px">    注:可添加新选项</span>
           </el-form-item>
           <br>
           <el-divider><b>A.2.14 近 30 天内意外事件</b></el-divider>
           <br>
             <div style="display: flex">
               <el-form-item label="A.2.15.1 跌倒"  prop="fallDown" style="margin-bottom: 20px;flex: 1">
                 <el-select v-model="form.fallDown" filterable placeholder="请选择症状" style="width: 75%;">
                   <el-option v-for="item in accidentList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
               <el-form-item label="A.2.15.2 走失"  prop="beLost" style="margin-bottom: 20px;flex: 1">
                 <el-select v-model="form.beLost" filterable placeholder="请选择症状" style="width: 75%;">
                   <el-option v-for="item in accidentList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
             </div>
             <div style="display: flex">
               <el-form-item label="A.2.15.3 噎食"  prop="choke" style="margin-bottom: 20px;flex: 1">
                 <el-select v-model="form.choke" filterable placeholder="请选择症状" style="width: 75%;">
                   <el-option v-for="item in accidentList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
               <el-form-item label="A.2.15.4 自杀、自伤"  prop="suicide" style="margin-bottom: 20px;flex: 1">
                 <el-select v-model="form.suicide" filterable placeholder="请选择症状" style="width: 75%;">
                   <el-option v-for="item in accidentList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
             </div>
             <el-form-item label="A.2.15.4 其他"  prop="otherAccident" style="margin-bottom: 20px;flex: 1">
               <el-input size="medium" name="otherAccident" v-model="form.otherAccident" placeholder="请输入内容" style="width: 90%"></el-input>
             </el-form-item>
           <br>
           <el-divider><b>A.3 信息提供者及联系人信息表</b></el-divider>
           <br>
           <div style="display: flex">
             <el-form-item label="A.3.1 信息提供者的姓名"  prop="informationProviderName" style="margin-bottom: 20px;flex: 1">
               <el-input size="medium" name="informationProviderName" :maxlength="20" show-word-limit v-model="form.informationProviderName" placeholder="请输入信息提供者的姓名" style="width: 75%"></el-input>
             </el-form-item>
             <el-form-item label="A.3.2 信息提供者与老人的关系"  prop="informationProviderAndSeniorAssociation" style="margin-bottom: 20px;flex: 1">
               <el-select v-model="form.informationProviderAndSeniorAssociation" filterable allow-create default-first-option placeholder="请选择信息提供者与老人的关系(输入框输入可添加新关系)" style="width: 75%;">
                 <el-option v-for="item in informationProviderAndSeniorAssociationList" :key="item.value" :label="item.label" :value="item.value"></el-option>
               </el-select>
             </el-form-item>
           </div>
           <div style="display: flex">
             <el-form-item label="A.3.3 联系人姓名"  prop="contactName" style="margin-bottom: 20px;flex: 1">
               <el-input size="medium" name="contactName" :maxlength="20" show-word-limit  v-model="form.contactName" placeholder="请输入联系人姓名" style="width: 75%"></el-input>
             </el-form-item>
             <el-form-item label="A.3.4 联系人电话"  prop="contactPhone" style="margin-bottom: 20px;flex: 1;">
               <el-input size="medium" name="contactPhone" :maxlength="11" show-word-limit v-model="form.contactPhone" placeholder="请输入联系人电话" style="width: 75%"></el-input>
             </el-form-item>
           </div>

           <br>
           <el-divider><b>A.4 疾病诊断和用药情况表</b></el-divider>
           <br>
           <el-form-item label="A.4.1 疾病诊断"  prop="diseaseDiagnosis" style="margin-bottom: 20px;flex: 1">
             <el-select v-model="disease"  multiple filterable default-first-option placeholder="请选择疾病诊断" style="width: 75%;">
               <el-option v-for="item in diseaseList" :key="item.value" :label="item.label" :value="item.value">
                 <span style="float: left">{{ item.label }}</span>
                 <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
               </el-option>
             </el-select>
           </el-form-item>
<!--           <el-form-item label="A.4.2 用药情况（目前长期服药情况）"  prop="diseaseDiagnosis" style="margin-bottom: 20px;flex: 1">-->
<!--             <el-select v-model="form.medication"  filterable default-first-option placeholder="请选择用药情况" style="width: 75%;">-->
<!--               <el-option v-for="item in diseaseList" :key="item.value" :label="item.label" :value="item.value">-->
<!--                 <span style="float: left">{{ item.label }}</span>-->
<!--                 <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>-->
<!--               </el-option>-->
<!--             </el-select>-->
<!--           </el-form-item>-->

           <span style="margin-left: 15%;">A.4.2 用药情况（目前长期服药情况）</span>
           <el-form-item
               style="margin-top: 20px"
                               v-for="(medicine, index) in form.medication"
                               :label="'用药情况' + (index+1)"
                               :key="medicine.key"
                               :prop="'medication.' + index + '.name'"
                               :rules="{
                                    required: true, message: '域名不能为空', trigger: 'blur'
                                                        }"
           >
             <div style="display:flex;">
               <el-input v-model="medicine.name" placeholder="请输入药物名称" style="flex: 1"></el-input>
               <el-input v-model="medicine.method" placeholder="请输入服药方法" style="flex: 1"></el-input>
               <el-input v-model="medicine.dosage" placeholder="请输入用药剂量" style="flex: 1"></el-input>
               <el-input v-model="medicine.frequency" placeholder="请输入用药频率" style="flex: 1"></el-input>
               <el-button @click.prevent="removeDomain(medicine)">删除</el-button>
             </div>
           </el-form-item>
           <el-form-item style="margin-top: 20px">
             <el-button @click="addDomain">新增用药情况</el-button>
           </el-form-item>









           <br>
           <el-divider><b>A.5 健康相关问题</b></el-divider>
           <br>
           <el-form-item label="A.5.1 压力性损伤" >
             <el-select v-model="form.stressInjury"   filterable allow-create default-first-option placeholder="请选择压力性损伤" style="width: 75%;">
               <el-option v-for="item in stressInjuryList" :key="item.value" :label="item.label" :value="item.value">
                 {{ item.label }}
               </el-option>
             </el-select>
           </el-form-item>
           <div style="display: flex">
             <el-form-item label="A.5.2 关节活动度" style="flex: 1">
               <el-select v-model="form.jointActivity"  filterable default-first-option placeholder="请选择关节活动度" style="width: 75%;">
                 <el-option v-for="item in jointActivityList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>
             <el-form-item label="A.5.3 伤口情况（可多选）" style="flex: 1">
               <el-select v-model="woundCondition"  multiple filterable default-first-option placeholder="请选择伤口情况" style="width: 75%;">
                 <el-option v-for="item in woundConditionList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>
           </div>
           <div style="display: flex">
           <el-form-item label="A.5.3 特殊医疗照护情况（可多选）" style="flex:1;">
             <el-select v-model="specialMedicalCare"  multiple filterable default-first-option placeholder="请选择特殊医疗照护情况" style="width: 75%;">
               <el-option v-for="item in specialMedicalCareList" :key="item.value" :label="item.label" :value="item.value">
                 {{ item.label }}
               </el-option>
             </el-select>
           </el-form-item>

           <el-form-item label="A.5.5 疼痛感(注：通过表情反应和询问来判断)" style="flex:1;">
             <el-select v-model="form.painPerception" filterable default-first-option placeholder="请选择疼痛感" style="width: 75%;">
               <el-option v-for="item in painPerceptionList" :key="item.value" :label="item.label" :value="item.value">
                 {{ item.label }}
               </el-option>
             </el-select>
           </el-form-item>
           </div>
           <div style="display: flex">
             <el-form-item label="A.5.6 牙齿缺失情况（可多选）" style="flex: 1">
               <el-cascader
                   style="width: 292px"
                   v-model="toothLoss"
                   :options="toothLossList"
                   :props="{multiple:true,expandTrigger: 'hover'}"
                   @change="handleChange"></el-cascader>
             </el-form-item>
             <el-form-item label="A.5.7 义齿佩戴情况（可多选）" style="flex: 1">
               <el-select v-model="dentureWear"  multiple filterable default-first-option placeholder="请选择义齿佩戴情况" style="width: 75%;">
                 <el-option v-for="item in dentureWearingList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>
           </div>
           <div style="display: flex">
             <el-form-item label="A.5.8 吞咽困难的情形和症状（可多选）" style="flex: 1">
               <el-select v-model="dysphagia"  multiple filterable default-first-option placeholder="请选择吞咽困难的情形和症状" style="width: 75%;" @change="handleChange">
                 <el-option v-for="item in dysphagiaList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>

             <el-form-item label="A.5.9 营养不良：体重、BMI低于正常值" style="flex: 1">
               <el-select v-model="form.innutrition"  filterable default-first-option placeholder="请选择状况" style="width: 75%;">
                 <el-option v-for="item in innutritionList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>
           </div>
           <div style="display: flex">
             <el-form-item label="A.5.10 清理呼吸道无效" style="flex: 1">
               <el-select v-model="form.respiratory"  filterable default-first-option placeholder="请选择状况" style="width: 75%;">
                 <el-option v-for="item in respiratoryList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>

             <el-form-item label="A.5.11 昏迷" style="flex: 1">
               <el-select v-model="form.stupor" filterable default-first-option placeholder="请选择状况" style="width: 75%;">
                 <el-option v-for="item in stuporList" :key="item.value" :label="item.label" :value="item.value">
                   {{ item.label }}
                 </el-option>
               </el-select>
             </el-form-item>


           </div>

         </el-form>
         <div style="text-align: center;margin:auto;">
<!--           <el-popconfirm-->
<!--               confirm-button-text='确定'-->
<!--               cancel-button-text='我再想想'-->
<!--               icon="el-icon-info"-->
<!--               icon-color="red"-->
<!--               title="您确定重置吗？重置后表单将置为空"-->
<!--               @confirm="reset"-->
<!--           >-->
<!--             <el-button type="success" style="height: 40px;width: 100px;" slot="reference"><span style="font-size: 15px"><i class="el-icon-refresh-right"></i> 重置</span></el-button>-->
<!--           </el-popconfirm>-->
           <el-button style="height: 40px;width: 100px;margin-left: 10px" type="primary" v-if="isEdit === 1" @click="save"><span style="font-size: 15px"><i class="el-icon-edit"></i>&nbsp;修改</span></el-button>
           <el-button v-if="isEdit === 0" type="primary" @click="save" style="height: 40px;width: 100px;margin-left: 5px"><span style="font-size: 15px"><i class="el-icon-check"></i>&nbsp;提交</span></el-button>
         </div>
         <br>
         <br>
       </el-card>
    </div>
<!--  </el-dialog>-->
</template>

<script>
export default {
  name: "Senior",
  props: {
    form:{
      type:Object,
      default: {
        medication:[{
          name: '',
          method:'',
          dosage:'',
          frequency:''
        }]
      }
    },
    newMedicalPayments:{
      type:Array,
      default: []
    },
    newFinancialSource:{
      type:Array,
      default: []
    },
    newDisease:{
      type:Array,
      default: []
    },
    newWoundCondition:{
      type:Array,
      default: []
    },
    newSpecialMedicalCare :{
      type:Array,
      default: []
    },
    newToothLoss:{
      type:Array,
      default: []
    },
    newDentureWear:{
      type:Array,
      default: []
    },
    newDysphagia:{
      type:Array,
      default: []
    },
    isEdit:{
      type:Number,
      default:0
    }
  },
  watch:{
    newMedicalPayments:{
      handler(newVal,oldVal) {
        this.medicalPayments = newVal;
      },
      immediate: true,
      deep: true
    },
    newFinancialSource:{
      handler(newVal,oldVal) {
        this.financialSource = newVal;
      },
      immediate: true,
      deep: true
    },
    newDisease:{
      handler(newVal,oldVal) {
        this.disease = newVal;
      },
      immediate: true,
      deep: true
    },
    newWoundCondition:{
      handler(newVal,oldVal) {
        this.woundCondition = newVal;
      },
      immediate: true,
      deep: true
    },
    newSpecialMedicalCare :{
      handler(newVal,oldVal) {
        this.specialMedicalCare = newVal;
      },
      immediate: true,
      deep: true
    },
    newToothLoss:{
      handler(newVal,oldVal) {
        this.toothLoss = newVal;
      },
      immediate: true,
      deep: true
    },
    newDentureWear:{
      handler(newVal,oldVal) {
        this.dentureWear = newVal;
      },
      immediate: true,
      deep: true
    },
    newDysphagia:{
      handler(newVal,oldVal) {
        this.dysphagia = newVal;
      },
      immediate: true,
      deep: true
    },
  },
  data(){
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
          }else{
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
          }else{
            callback(new Error("请输入正确的身份证号"));
          }
        }
      }
    }
    var validateAssessReason = (rule, value, callback)=>{
        if (this.assessReason.length>0) {
          callback();
        } else {
          return callback(new Error('请选择评估原因'));
        }
    }
    var validateMedicalPayments = (rule, value, callback)=>{
      if (this.medicalPayments.length>0) {
        callback();
      } else {
        return callback(new Error('请选择医疗费用支付方式'));
      }
    }
    var validateFinancialSource = (rule, value, callback)=>{
      if (this.financialSource.length>0) {
        callback();
      } else {
        return callback(new Error('请选择经济来源'));
      }
    }
    return{
      time: "", //当前时间
      gender_disabled_1:true,
      gender_disabled_2:true,
      select:'18',
      identityCardMaxLength:18,
      showMinorityNationality:false,
      assessReason:[],
      medicalPayments:this.newMedicalPayments,
      financialSource:this.newFinancialSource,

      disease:this.newDisease,
      woundCondition :this.newWoundCondition,
      specialMedicalCare :this.newSpecialMedicalCare,
      toothLoss :this.newToothLoss,
      dentureWear :this.newDentureWear,
      dysphagia :this.newDysphagia,
      // form:{
      //   assessId:'',
      //   assessDate:'',
      //   assessReason: '',
      //   assessedName:'',
      //   identityCard:'',
      //   birthday:'',
      //   age:'',
      //   gender:'',
      //   socialCard:'',
      //   nationality:'',
      //   educationLevel:'',
      //   religion:'',
      //   maritalStatus:'',
      //   residenceStatus:'',
      //   medicalPayments:'',
      //   financialSource:'',
      //   stupidStatus:'',
      //   spiritDisease:'',
      //   chronicIllness:'',
      //   fallDown:'',
      //   beLost:'',
      //   choke:'',
      //   suicide:'',
      //   otherAccident:'',
      //   informationProviderName:'',
      //   informationProviderAndSeniorAssociation:'',
      //   contactName:'',
      //   contactPhone:''
      // },
      rules: {
        assessId: [
          { required: true, message: '评估编号不能为空', trigger: 'blur' },
        ],
        assessDate:[
          { required: true, message: '请输入日期', trigger: 'change'},
        ],
        assessReason: [{validator: validateAssessReason,trigger: 'change'}],
        assessedName:[
          { required: true, message: '姓名不能为空', trigger: 'blur' },
          { min:2, max:20,message: '姓名应不少于两个汉字',trigger: 'blur'},
          { pattern:/^[\u4E00-\u9FA5]+$/,message:'只能输入中文',trigger: 'blur'}
        ],
        identityCard:[
          { required: true, message: '身份证号不能为空', trigger: 'blur' },
          { validator: validateIdentityCard,trigger: 'blur'}
        ],
        socialCard:[
          { required: true, message: '请输入社保卡号', trigger: 'blur'},
          { min:15,max:18, message: '社保卡号字数不对',trigger: 'blur'},
        ],
        nationality:[
          { required: true, message: '请选择民族', trigger: 'change'},
        ],
        educationLevel:[
          {required: true,  message:'请选择文化程度', trigger: 'change'}
        ],
        religion:[
          { required: true, message: '请选择宗教信仰', trigger: 'change'},
        ],
        maritalStatus:[
          { required: true, message: '请选择婚姻状况', trigger: 'change'},
        ],
        residenceStatus:[
          { required: true, message: '请选择居住情况', trigger: 'change'},
        ],
        medicalPayments:[
          {validator: validateMedicalPayments, trigger: 'change'},
        ],
        financialSource:[
          {validator: validateFinancialSource,required: true, message: '请选择经济来源', trigger: 'change'},
        ],
        stupidStatus:[
          { required: true, message: '请选择症状', trigger: 'change'},
        ],
        spiritDisease:[
          { required: true, message: '请选择症状', trigger: 'change'},
        ],
        chronicIllness:[
          { required: true, message: '慢性疾病不能为空', trigger: 'blur'},
        ],
        fallDown:[
          { required: true, message: '请选择症状', trigger: 'change'},
        ],
        beLost:[
          { required: true, message: '请选择症状', trigger: 'change'},
        ],
        choke:[
          { required: true, message: '请选择症状', trigger: 'change'},
        ],
        suicide:[
          { required: true, message: '请选择症状', trigger: 'change'},
        ],
        informationProviderName:[
          { required: true, message: '姓名不能为空', trigger: 'blur' },
          { min:2, max:20,message: '姓名应不少于两个汉字',trigger: 'blur'},
          { pattern:/^[\u4E00-\u9FA5]+$/,message:'只能输入中文',trigger: 'blur'}
        ],
        informationProviderAndSeniorAssociation:[
          { required: true, message: '请选择关系', trigger: 'change'},
        ],
        contactName:[
          { required: true, message: '姓名不能为空', trigger: 'blur' },
          { min:2, max:20,message: '姓名应不少于两个汉字',trigger: 'blur'},
          { pattern:/^[\u4E00-\u9FA5]+$/,message:'只能输入中文',trigger: 'blur'}
        ],
        contactPhone:[
          { required: true, message: '联系人电话不能为空', trigger: 'blur' },
          { pattern:/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,message:'请输入正确的联系人电话',trigger: 'blur'}
        ]
      },
      educationList:[
        {id: 1, label: "文盲", value: '1',},
        {id: 2, label: "小学", value: '2',},
        {id: 3, label: "初中", value: '3',},
        {id: 4, label: "高中/技校/中专", value: '4',},
        {id: 5, label: "大学专科及以上", value: '5',},
        {id: 6, label: "不详", value: '6',},
      ],
      maritalList:[
        {id: 1, label: "未婚", value: '1',},
        {id: 2, label: "已婚", value: '2',},
        {id: 3, label: "丧偶", value: '3',},
        {id: 4, label: "离婚", value: '4',},
        {id: 5, label: "未说明的婚姻状况", value: '5',},
      ],
      residenceList:[
        {id: 1, label: "独居", value: '1',},
        {id: 2, label: "与配偶/伴侣居住", value: '2',},
        {id: 3, label: "与子女居住", value: '3',},
        {id: 4, label: "与父母居住", value: '4',},
        {id: 5, label: "与兄弟姐妹居住", value: '5',},
        {id: 6, label: "与其他亲属居住", value: '6',},
        {id: 7, label: "与非亲属关系的人居住", value: '7',},
        {id: 8, label: "养老机构", value: '8',},
      ],
      medicalPaymentsList:[
        {id: 1, label: "城镇职工基本医疗保险", value: '1',},
        {id: 2, label: "城镇居民基本医疗保险", value: '2',},
        {id: 3, label: "公务员补助", value: '3',},
        {id: 4, label: "企业补充保险", value: '4',},
        {id: 5, label: "自费", value: '5',},
        {id: 6, label: "公费医疗及医疗照顾对象", value: '6',},
        {id: 7, label: "医疗救助", value: '7',},
        {id: 8, label: "大病保险", value: '8',},
      ],
      financialSourceList:[
        {id: 1, label: "退休金/养老金", value: '1',},
        {id: 2, label: "子女补贴", value: '2',},
        {id: 3, label: "亲友资助", value: '3',},
        {id: 4, label: "国家普惠型补贴", value: '4',},
        {id: 5, label: "个人储蓄", value: '5',},
        {id: 6, label: "其他补贴", value: '6',},
      ],
      stupidStatusList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "轻度", value: '1',},
        {id: 2, label: "中度", value: '2',},
        {id: 3, label: "重度", value: '3',},
      ],
      spiritDiseaseList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "精神分裂症", value: '1',},
        {id: 2, label: "双相情感障碍", value: '2',},
        {id: 3, label: "偏执性精神障碍", value: '3',},
        {id: 4, label: "分裂情感性障碍", value: '4',},
        {id: 5, label: "癫痫所致精神障碍", value: '5',},
        {id: 6, label: "精神发育迟滞伴发精神障碍", value: '6',},
      ],
      accidentList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "发生过 1 次", value: '1',},
        {id: 2, label: "发生过 2 次", value: '2',},
        {id: 3, label: "发生过 3 次及以上", value: '3',},
      ],
      diseaseList:[
        {id: 1, label: "高血压", value: 'I10-I15',},
        {id: 2, label: "冠心病", value: 'I25',},
        {id: 3, label: "糖尿病", value: 'E10-E14',},
        {id: 4, label: "肺炎", value: 'J12-J18',},
        {id: 5, label: "慢性阻塞性肺疾病", value: 'J44',},
        {id: 6, label: "脑出血", value: 'I60-I62',},
        {id: 7, label: "脑梗塞", value: 'I63',},
        {id: 8, label: "尿路感染（30天内）", value: 'N34.252',},
        {id: 9, label: "帕金森综合征", value: 'G20-G22',},
        {id: 10, label: "慢性肾功能衰竭", value: 'N18-N19',},
        {id: 11, label: "消化道溃疡", value: 'K20-K31',},
        {id: 12, label: "肿瘤", value: 'C00-D48',},
        {id: 13, label: "截肢(6个月内)", value: '',},
        {id: 14, label: "骨折(3个月内)", value: 'M84',},
        {id: 15, label: "癫痫", value: 'G40',},
        {id: 16, label: "白内障", value: 'H25-H26',},
        {id: 17, label: "青光眼", value: 'H40-H42',},
        {id: 18, label: "骨质疏松症", value: 'M80-82',},
        {id: 19, label: "痴呆", value: 'F00-F03',},
        {id: 20, label: "其他精神和行为障碍", value: 'F04-F99',},
      ],
      nationList: [
        {
          id: 1,
          info: "汉族",
          value: "汉族",
        },
        {
          id: 2,
          info: "壮族",
          value: "壮族",
        },
        {
          id: 3,
          info: "满族",
          value: 3,
        },
        {
          id: 4,
          info: "回族",
          value: 4,
        },
        {
          id: 5,
          info: "苗族",
          value: 5,
        },
        {
          id: 6,
          info: "维吾尔族",
          value: 6,
        },
        {
          id: 7,
          info: "土家族",
          value: 7,
        },
        {
          id: 8,
          info: "彝族",
          value: 8,
        },
        {
          id: 9,
          info: "蒙古族",
          value: 9,
        },
        {
          id: 10,
          info: "藏族",
          value: 10,
        },
        {
          id: 11,
          info: "布依族",
          value: 11,
        },
        {
          id: 12,
          info: "侗族",
          value: 12,
        },
        {
          id: 13,
          info: "瑶族",
          value: 13,
        },
        {
          id: 14,
          info: "朝鲜族",
          value: 14,
        },
        {
          id: 15,
          info: "白族",
          value: 15,
        },
        {
          id: 16,
          info: "哈尼族",
          value: 16,
        },
        {
          id: 17,
          info: "哈萨克族",
          value: 17,
        },
        {
          id: 18,
          info: "黎族",
          value: 18,
        },
        {
          id: 19,
          info: "傣族",
          value: 19,
        },
        {
          id: 20,
          info: "畲族",
          value: 20,
        },
        {
          id: 21,
          info: "傈僳族",
          value: 21,
        },
        {
          id: 22,
          info: "仡佬族",
          value: 22,
        },
        {
          id: 23,
          info: "东乡族",
          value: 23,
        },
        {
          id: 24,
          info: "高山族",
          value: 24,
        },
        {
          id: 25,
          info: "拉祜族",
          value: 25,
        },
        {
          id: 26,
          info: "水族",
          value: 26,
        },
        {
          id: 27,
          info: "佤族",
          value: 27,
        },
        {
          id: 28,
          info: "纳西族",
          value: 28,
        },
        {
          id: 29,
          info: "羌族",
          value: 29,
        },
        {
          id: 30,
          info: "土族",
          value: 30,
        },
        {
          id: 31,
          info: "仫佬族",
          value: 31,
        },
        {
          id: 32,
          info: "锡伯族",
          value: 32,
        },
        {
          id: 33,
          info: "柯尔克孜族",
          value: 33,
        },
        {
          id: 34,
          info: "达斡尔族",
          value: 34,
        },
        {
          id: 35,
          info: "景颇族",
          value: 35,
        },
        {
          id: 36,
          info: "毛南族",
          value: 36,
        },
        {
          id: 37,
          info: "撒拉族",
          value: 37,
        },
        {
          id: 38,
          info: "布朗族",
          value: 38,
        },
        {
          id: 39,
          info: "塔吉克族",
          value: 39,
        },
        {
          id: 40,
          info: "阿昌族",
          value: 40,
        },
        {
          id: 41,
          info: "普米族",
          value: 41,
        },
        {
          id: 42,
          info: "鄂温克族",
          value: 42,
        },
        {
          id: 43,
          info: "怒族",
          value: 43,
        },
        {
          id: 44,
          info: "京族",
          value: 44,
        },
        {
          id: 45,
          info: "基诺族",
          value: 45,
        },
        {
          id: 46,
          info: "德昂族",
          value: 46,
        },
        {
          id: 47,
          info: "保安族",
          value: 47,
        },
        {
          id: 48,
          info: "俄罗斯族",
          value: 48,
        },
        {
          id: 49,
          info: "裕固族",
          value: 49,
        },
        {
          id: 50,
          info: "乌孜别克族",
          value: 50,
        },
        {
          id: 51,
          info: "门巴族",
          value: 51,
        },
        {
          id: 52,
          info: "鄂伦春族",
          value: 52,
        },
        {
          id: 53,
          info: "独龙族",
          value: 53,
        },
        {
          id: 54,
          info: "塔塔尔族",
          value: 54,
        },
        {
          id: 55,
          info: "赫哲族",
          value: 55,
        },
        {
          id: 56,
          info: "珞巴族",
          value: 56,
        },
      ],
      religionList:[
        {
          id: 1,
          info: "无",
          value: '---',
        },
        {
          id: 2,
          info: "基督教",
          value: 'Christianity',
        },
        {
          id: 3,
          info: "伊斯兰教",
          value: 'Islamism',
        },
        {
          id: 4,
          info: "佛教",
          value: 'Buddhism',
        },
        {
          id: 5,
          info: "道教",
          value: 'Taoism',
        },
        {
          id: 6,
          info: "儒教",
          value: 'Confucianism',
        },
        {
          id: 7,
          info: "印度教",
          value: 'Hinduism',
        },
        {
          id: 8,
          info: "犹太教",
          value: 'Judaism',
        },
        {
          id: 9,
          info: "神道教",
          value: 'Shintoism',
        },
        {
          id: 10,
          info: "其他",
          value: 'Other',
        },
      ],
      informationProviderAndSeniorAssociationList:[
        {id: 0, label: "本人", value: '本人',},
        {id: 1, label: "配偶", value: '配偶',},
        {id: 2, label: "子女", value: '子女',},
        {id: 3, label: "其他亲属", value: '其他亲属',},
        {id: 4, label: "雇佣照顾者", value: '雇佣照顾者',},
        {id: 5, label: "村（居）民委员会工作人员", value: '村（居）民委员会工作人员',},
        {id: 6, label: "其他", value: '其他',},
      ],
      stressInjuryList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "Ⅰ期：皮肤完好，出现指压不会变白的红印 ", value: '1',},
        {id: 2, label: "Ⅱ期：皮肤真皮层损失、暴露，出现水疱", value: '2',},
        {id: 3, label: "Ⅲ期：全层皮肤缺失，可见脂肪、肉芽组织以及边缘内卷", value: '3',},
        {id: 4, label: "Ⅳ期：全层皮肤、组织缺失，可见肌腱、肌肉、腱膜，以及边缘内卷，伴随隧道、潜行", value: '4',},
        {id: 5, label: "不可分期：全身皮肤、组织被腐肉、焦痂掩盖，无法确认组织缺失程度，去除腐肉、焦痂才可判断损伤程度", value: '5',},
      ],
      jointActivityList:[
        {id: 0, label: "无，没有影响日常生活功能", value: '0',},
        {id: 1, label: "是，影响日常生活功能", value: '1',},
        {id: 2, label: "无法判断", value: '2',},
      ],
      woundConditionList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "擦伤", value: '1',},
        {id: 2, label: "烧烫伤", value: '2',},
        {id: 3, label: "术后伤口", value: '3',},
        {id: 4, label: "糖尿病足溃疡", value: '4',},
        {id: 5, label: "血管性溃疡", value: '5',},
        {id: 6, label: "其它伤口", value: '6',},
      ],
      specialMedicalCareList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "胃管", value: '1',},
        {id: 2, label: "尿管", value: '2',},
        {id: 3, label: "气管切开", value: '3',},
        {id: 4, label: "胃/肠/膀胱造瘘", value: '4',},
        {id: 5, label: "无创呼吸机", value: '5',},
        {id: 6, label: "透析", value: '6',},
        {id: 7, label: "其他", value: '7',},
      ],
      painPerceptionList:[
        {id: 0, label: "无疼痛", value: '0',},
        {id: 1, label: "轻微的疼痛", value: '1',},
        {id: 2, label: "中度疼痛（尚可忍受的程度）", value: '2',},
        {id: 3, label: "重度疼痛（无法忍受的程度）", value: '3',},
        {id: 4, label: "不知道或无法判断", value: '4',},
      ],
      toothLossList:[
        {id: 0, label: "无缺损", value: '0',},
        {id: 1, label: "牙体缺损（如龋齿、楔状缺损）", value: '1',},
        {id: 2, label: "牙列缺损", value: '2',
          children: [{value: 'feiduiwei', label: '非对位牙缺失',},
            {value: 'danceduiwei', label: '单侧对位牙缺失',},
            {value: 'shuangceduiwei', label: '双侧对位牙缺失',},
          ]},
        {id: 3, label: "牙列缺失", value: '3',
          children: [{value: 'shangeya', label: '上颌牙缺失',},
            {value: 'xiaeya', label: '下颌牙缺失',},
            {value: 'quankouya', label: '全口牙缺失',},
          ]
        },
      ],
      dentureWearingList:[
        {id: 0, label: "无义齿", value: '0',},
        {id: 1, label: "固定义齿", value: '1',},
        {id: 2, label: "可摘局部义齿", value: '2',},
        {id: 3, label: "可摘全/半口义齿", value: '3',},
      ],
      dysphagiaList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "抱怨吞咽困难或吞咽时会疼痛", value: '1',},
        {id: 2, label: "吃东西或喝水的时出现咳嗽或呛咳", value: '2',},
        {id: 3, label: "用餐后嘴中仍含着食物或留有残余食物", value: '3',},
        {id: 4, label: "当喝或吃流质或固质的食物时，食物会从嘴角边流失", value: '4',},
        {id: 5, label: "有流口水的情况", value: '5',},
      ],
      innutritionList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "有", value: '1',},
      ],
      respiratoryList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "有", value: '1',},
      ],
      stuporList:[
        {id: 0, label: "无", value: '0',},
        {id: 1, label: "有", value: '1',},
      ],


    }
  },
  methods: {
    getTime() {
      this.form.assessDate = new Date()
    },
    save(){
      this.$refs['seniorForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.form.assessReason = this.assessReason.join(',')
          this.form.medicalPayments = this.medicalPayments.join(',')
          this.form.financialSource = this.financialSource.join(',')
          this.form.disease = this.disease.join(',')
          this.form.woundCondition = this.woundCondition.join(',')
          this.form.toothLoss = JSON.stringify(this.toothLoss)
          this.form.specialMedicalCare = this.specialMedicalCare.join(',')
          this.form.dentureWear = this.dentureWear.join(',')
          this.form.dysphagia = this.dysphagia.join(',')
          this.form.medication = JSON.stringify(this.form.medication)
          this.request.post("/senior",this.form).then(res=>{
            if(res.code === '200'){
              this.$message.success("保存成功")
              this.$emit('childEvent',false)
              this.$emit('load');
            }
            else{
              this.$message.error("保存失败")
            }
          })
          //this.$parent.load();
        }
      })
    },
    identityCardChange() {

        //正则表达式
        const reg18 =
            /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        const reg15 =
            /^([1-6][1-9]|50)\d{4}\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}$/;

        var org_birthday = '';
        var org_gender = '';
        var gender = '';
        var birthday = ''

        if(this.identityCardMaxLength === 18) {
          if (reg18.test(this.form.identityCard)) {
            org_birthday = this.form.identityCard.substring(6, 14);
            org_gender = this.form.identityCard.substring(16, 17);
            gender = org_gender % 2 === 1 ? "男" : "女";
            birthday =
                org_birthday.substring(0, 4) +
                "-" +
                org_birthday.substring(4, 6) +
                "-" +
                org_birthday.substring(6, 8);
            if (this.checkDate(birthday) === false) {
              this.blank_idCardAssociation()
              return false;
            }
          }
          else {
            this.blank_idCardAssociation()
            return false;
          }
        }
        else {
          if (reg15.test(this.form.identityCard)) {
            org_birthday = this.form.identityCard.substring(6, 12);
            org_gender = this.form.identityCard.substring(14, 15);
            gender = org_gender % 2 === 1 ? "男" : "女";
            birthday =
                '19' + org_birthday.substring(0, 2) +
                "-" +
                org_birthday.substring(2, 4) +
                "-" +
                org_birthday.substring(4, 6);
            if (this.checkDate(birthday) === false) {
              this.blank_idCardAssociation()
              return false;
            }
          } else {
            this.blank_idCardAssociation()
            return false;
          }
        }
        const birthdays = new Date(birthday.replace(/-/g, "/"));
        let d = new Date();
          let age =
              d.getFullYear() -
              birthdays.getFullYear() -
              (d.getMonth() < birthdays.getMonth() ||
              (d.getMonth() == birthdays.getMonth() &&
                  d.getDate() < birthdays.getDate())
                  ? 1
                  : 0);
        this.form.gender = gender;
        this.form.birthday = birthday;
        this.form.age = age;
        if (gender === "男") {
          this.gender_disabled_1 = false
        } else {
          this.gender_disabled_2 = false
        }
    },
    identityCardNumChange(vid){
      this.form.identityCard = ''
      this.blank_idCardAssociation()
      if(vid === '15') this.identityCardMaxLength = 15
      else this.identityCardMaxLength = 18
    },
    blank_idCardAssociation(){
      this.form.gender = '';
      this.form.birthday = '';
      this.form.age = '';
      this.gender_disabled_1 = true
      this.gender_disabled_2 = true
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
    medicalPaymentsChange(){
      if(this.medicalPayments.length === 4){
        this.$message.warning("最多选择四项")
      }
    },
    financialSourceChange(){
      if(this.financialSource.length === 4){
        this.$message.warning("最多选择四项")
      }
    },
    assessIdCreate(){
      this.request.post("/senior/assessId").then(res=>{
        if(res.code === '200'){
          this.form.assessId = res.data;
        }
        else{
          this.$message.error("系统错误")
        }
      })
    },
    reset(){
      this.medicalPayments=[]
      this.financialSource = []
      this.form = {}
    },
    handleChange(value) {
      console.log(typeof JSON.stringify(value));
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    removeDomain(item) {
      var index = this.form.medication.indexOf(item)
      if (index !== -1) {
        this.form.medication.splice(index, 1)
      }
    },
    addDomain() {
      this.form.medication.push({
        name: '',
        method:'',
        dosage:'',
        frequency:''
      });
    }
  },
  mounted() {
    // this.form.medication = this.form.medication ? JSON.parse(this.form.medication) :''
    this.getTime()
    this.assessIdCreate()
  },
}
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 100%;
  color: #606266;
  display: inline-block;
  /*width: 100%;*/
}
.line{
  text-align: center;
}
.select_identityCard{
  width: 80px;
  text-align: center;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>