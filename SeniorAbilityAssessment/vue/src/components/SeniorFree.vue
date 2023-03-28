<template>
<!--  <el-dialog width="90%" title="信息填写" :visible.sync="dialogSeniorVisible">-->
    <div style="display:flex;overflow: auto;width: 100%">
       <el-card class="box-card">
         <el-form  ref="seniorForm" :rules="rules" :model="form" style="padding: 20px 30px;"  label-width="200px">
           <el-form-item label="A.2.1 姓名" prop="assessedName" style="margin-bottom: 20px">
             <el-input  maxlength="20" show-word-limit size="medium" v-model="form.assessedName" placeholder="请输入真实姓名" style="width: 90%"></el-input>
           </el-form-item>
<!--           <br>-->
<!--           <el-form-item label="A.2.2 身份证号" prop="identityCard" style="margin-bottom: 20px">-->
<!--             <el-input :maxlength="identityCardMaxLength" show-word-limit @input="identityCardChange"-->
<!--                       size="medium" v-model="form.identityCard" placeholder="请输入内容"  autocomplete="off" style="width: 90%">-->
<!--                     <template slot="prepend" style="width: 100%;">-->
<!--                       <el-select class="select_identityCard" v-model="select" slot="prepend" placeholder="请选择" @change="identityCardNumChange">-->
<!--                         <el-option label="18位" value="18" ></el-option>-->
<!--                         <el-option label="15位" value="15"></el-option>-->
<!--                       </el-select>-->
<!--                     </template>-->
<!--             </el-input>-->
<!--           </el-form-item>-->
<!--           <el-form-item label="A.2.3 性别" prop="gender" style="margin-bottom: 20px">-->
<!--             <el-radio-group v-model="form.gender">-->
<!--               <el-radio label="男" :disabled="gender_disabled_1" border>男</el-radio>-->
<!--               <el-radio label="女" :disabled="gender_disabled_2" border>女</el-radio>-->
<!--             </el-radio-group>-->
<!--           </el-form-item>-->
<!--           <el-form-item label="A.2.4 出生日期" prop="birthday" style="margin-bottom: 20px">-->
<!--             <el-input v-model="form.birthday" :disabled="true" placeholder="请输入出生日期" style="width: 90%"></el-input>-->
<!--           </el-form-item>-->
<!--           <el-form-item label="A.2.5 年龄" prop="age" style="margin-bottom: 20px">-->
<!--             <el-input show-word-limit-->
<!--                       size="medium" v-model="form.age" placeholder="请输入年龄"  autocomplete="off" style="width: 90%" :disabled="true" readonly ></el-input>-->
<!--           </el-form-item>-->
<!--           <el-form-item label="A.2.6 社保卡号" prop="socialCard" style="margin-bottom: 20px">-->
<!--             <el-input maxlength="18" show-word-limit-->
<!--                       size="medium" v-model="form.socialCard" placeholder="请输入社保卡号"  autocomplete="off" style="width: 90%"></el-input>-->
<!--           </el-form-item>-->
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
           <div style="border: darkgrey solid 1px">
             A.2.14 疾病诊断
             <div style="display: flex">
                 <el-form-item label="A.2.14.1 痴呆"  prop="stupidStatus" style="margin-bottom: 20px;flex: 1">
                   <el-select v-model="form.stupidStatus" filterable placeholder="请选择症状" style="width: 75%;">
                     <el-option v-for="item in stupidStatusList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                   </el-select>
                 </el-form-item>
                 <el-form-item label="A.2.14.2 精神疾病"  prop="spiritDisease" style="margin-bottom: 20px;flex: 1">
                   <el-select v-model="form.spiritDisease" filterable placeholder="请选择症状" style="width: 75%;">
                     <el-option v-for="item in spiritDiseaseList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                   </el-select>
                 </el-form-item>
             </div>
             <el-form-item label="A.2.14.3 慢性疾病"  prop="chronicIllness" style="margin-bottom: 20px;flex: 1">
                <el-input size="medium" v-model="form.chronicIllness" placeholder="请输入内容" style="width: 90%"></el-input>
             </el-form-item>
           </div>
           <div style="border-radius: 5px;border: #8492a6 solid 1px;padding: 10px 10px">
             <span>A.2.15 近 30 天内意外事件</span>
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
               <el-form-item label="A.2.15.4 自杀"  prop="suicide" style="margin-bottom: 20px;flex: 1">
                 <el-select v-model="form.suicide" filterable placeholder="请选择症状" style="width: 75%;">
                   <el-option v-for="item in accidentList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
             </div>
             <el-form-item label="A.2.15.4 其他"  prop="otherAccident" style="margin-bottom: 20px;flex: 1">
               <el-input size="medium" name="otherAccident" v-model="form.otherAccident" placeholder="请输入内容" style="width: 90%"></el-input>
             </el-form-item>
           </div>
           <br>
           <br>
           <br>
           <br>
           <br>
           <el-form-item label="A.3.1 信息提供者的姓名"  prop="informationProviderName" style="margin-bottom: 20px;flex: 1">
             <el-input size="medium" name="informationProviderName" :maxlength="20" show-word-limit v-model="form.informationProviderName" placeholder="请输入信息提供者的姓名" style="width: 90%"></el-input>
           </el-form-item>
           <el-form-item label="A.3.2 信息提供者与老人的关系"  prop="informationProviderAndSeniorAssociation" style="margin-bottom: 20px;flex: 1">
             <el-select v-model="form.informationProviderAndSeniorAssociation" filterable allow-create default-first-option placeholder="请选择信息提供者与老人的关系(输入框输入可添加新关系)" style="width: 75%;">
               <el-option v-for="item in informationProviderAndSeniorAssociationList" :key="item.value" :label="item.label" :value="item.value"></el-option>
             </el-select>
           </el-form-item>
           <el-form-item label="A.3.3 联系人姓名"  prop="contactName" style="margin-bottom: 20px;flex: 1">
             <el-input size="medium" name="contactName" :maxlength="20" show-word-limit  v-model="form.contactName" placeholder="请输入联系人姓名" style="width: 90%"></el-input>
           </el-form-item>
           <el-form-item label="A.3.4 联系人电话"  prop="contactPhone" style="margin-bottom: 20px;flex: 1">
             <el-input size="medium" name="contactPhone" :maxlength="11" show-word-limit v-model="form.contactPhone" placeholder="请输入联系人电话" style="width: 90%"></el-input>
           </el-form-item>
         </el-form>
         <div style="text-align: center;margin:auto;">
           <el-popconfirm
               confirm-button-text='确定'
               cancel-button-text='我再想想'
               icon="el-icon-info"
               icon-color="red"
               title="您确定重置吗？重置后表单将置为空"
               @confirm="reset"
           >
             <el-button type="success" style="height: 40px;width: 100px;" slot="reference"><span style="font-size: 15px"><i class="el-icon-refresh-right"></i> 重置</span></el-button>
           </el-popconfirm>
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
      default: {}
    },
    newMedicalPayments:{
      type:Array,
      default: []
    },
    newFinancialSource:{
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
    }
  },
  data(){
    var validateIdentityCard = (rule, value, callback)=>{
      console.log(value)
      let org_birthday = '';
      let birthday = '';
      if(!value) {
        return callback(new Error('身份证号不能为空'));
      }
      else{
        if (this.identityCardMaxLength === 18) {
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
        } else {
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
          { pattern:/(^([1-6][1-9]|50)\d{4}\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}$)|(^([1-6][1-9]|50)\d{4}(18|19|20)\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)/,message:'请输入正确的身份证号',trigger: 'blur'},
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
        {id: 3, label: "新型农村合作医疗", value: '3',},
        {id: 4, label: "贫困救助", value: '4',},
        {id: 5, label: "商业医疗保险", value: '5',},
        {id: 6, label: "全公费", value: '6',},
        {id: 7, label: "全自费", value: '7',},
        {id: 8, label: "其他", value: '8',},
      ],
      financialSourceList:[
        {id: 1, label: "退休金/养老金", value: '1',},
        {id: 2, label: "子女补贴", value: '2',},
        {id: 3, label: "亲友资助", value: '3',},
        {id: 4, label: "其他补贴", value: '4',},
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
        {id: 1, label: "配偶", value: '配偶',},
        {id: 2, label: "子女", value: '子女',},
        {id: 3, label: "其他亲属", value: '其他亲属',},
        {id: 4, label: "雇佣照顾者", value: '雇佣照顾者',},
      ]
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
          this.request.post("/senior",this.form).then(res=>{
            if(res.code === '200'){
              this.$message.success("保存成功")
            }
            else{
              this.$message.error("保存失败")
            }
          })
          this.$emit('childEvent',false)
          this.$emit('load');
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