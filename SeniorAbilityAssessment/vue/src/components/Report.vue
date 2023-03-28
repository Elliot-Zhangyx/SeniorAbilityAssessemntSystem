<template>
 <div>
<!--   <el-table :data="subjectData" border>-->
<!--     <el-table-column property="title">-->
<!--        <template slot-scope="scope">{{scope.row.title}}</template>-->
<!--     </el-table-column>-->
<!--   </el-table>-->

   <el-card style="margin:20px 50PX;padding: 20px">
     <div style="text-align: center">
       <span style="font-size: 18px;">老年人能力评估报告</span><br>
       <span style="font-size: 12px;color: #909399">(规范性)</span><br><br>
     </div>
     <div>
       <span style="font-size: 12px"><b>A.1 评估对象基本信息</b></span>
       <el-descriptions :column="3"  style="margin-top: 5px">
         <el-descriptions-item label="A.2.1 姓名">{{info.assessedName}}</el-descriptions-item>
         <el-descriptions-item label="A.2.2 身份证号">{{info.identityCard}}</el-descriptions-item>
         <el-descriptions-item label="A.2.3 性别">{{info.gender}}</el-descriptions-item>
         <el-descriptions-item label="A.2.4 出生日期">{{info.birthday}}</el-descriptions-item>
         <el-descriptions-item label="A.2.5 年龄">{{info.age}}</el-descriptions-item>
         <el-descriptions-item label="A.2.6 社保卡号">{{info.socialCard}}</el-descriptions-item>

         <el-descriptions-item label="A.2.7 民族">
           <div v-for="item in this.nationList">
             <span v-if="info.nationality === item.info.toString()">{{item.info}}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item label="A.2.8 文化程度">
           <div v-for="item in this.educationList">
             <span v-if="info.educationLevel === item.value">{{item.label}}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item label="A.2.9 宗教信仰">
           <div v-for="item in religionList">
             <span v-if="info.religion === item.value">{{ item.info }}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item label="A.2.10 婚姻状况">
           <div v-for="item in maritalList">
             <span v-if="info.maritalStatus === item.value">{{item.label}}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item label="A.2.11 居住情况">
           <div v-model="info.residenceStatus" v-for="item in residenceList">
             <span v-if="info.residenceStatus === item.value" >{{item.label}}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item label="A.2.12 经济来源">
           <div v-for="item in financialSourceList" style="display: flex">
             <span v-if="tip === item.value" v-for="tip in info.financialSource.split(',')">{{item.label}}</span>&nbsp;
           </div>
         </el-descriptions-item>
         <el-descriptions-item label="A.2.13 医疗费用支付方式">
           <div v-for="item in medicalPaymentsList" style="display: flex">
             <span v-if="tip === item.value" v-for="tip in info.medicalPayments.split(',')" >{{item.label}}</span>&nbsp;
           </div>
         </el-descriptions-item>
       </el-descriptions>
     </div>
     <div>
       <span style="font-size: 12px"><b>A.2.14 疾病诊断</b></span>
       <el-descriptions style="margin-top: 5px">
         <el-descriptions-item>
           <template slot="label">
             A.2.14.1 痴呆
           </template>
           <div v-model="info.stupidStatus" v-for="item in stupidStatusList">
             <span v-if="info.stupidStatus === item.value" >{{item.label}}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item>
           <template slot="label">
             A.2.14.2 精神疾病
           </template>
           <div v-model="info.spiritDisease" v-for="item in spiritDiseaseList">
             <span v-if="info.spiritDisease === item.value" >{{item.label}}</span>
           </div>
         </el-descriptions-item>
         <el-descriptions-item>
           <template slot="label">
             A.2.14.3 慢性疾病
           </template>
           {{info.chronicIllness}}
         </el-descriptions-item>
       </el-descriptions>
     </div>

     <el-divider></el-divider>

     <div  v-for="(subject,index) in scores">
       <span style="font-size: 10px;"><b>B.{{index+1}}&nbsp;{{primaryIndex[index].title}}</b></span>
       <el-descriptions :column="3" style="margin-top: 5px">
         <el-descriptions-item v-for="item in subject">
           <template slot="label">{{item.subjectTitle.split('：')[0]}}</template>
           {{item.score}}
         </el-descriptions-item>
       </el-descriptions>
     </div>

     <el-divider></el-divider>
     <img src="../assets/seal.png" alt="" width="200" height="200" style="position: absolute;margin-left: 60%">
     <div>
      <span style="font-size: 10px;"><b>C.1 一级指标分级</b></span>
      <el-descriptions :column="3" style="margin-top: 5px">
       <el-descriptions-item v-for="(item,index) in primaryIndex">
         <template slot="label">C.1.{{index+1}}&nbsp;{{item.title+'总分'}}</template>
         {{item.score}}
       </el-descriptions-item>
       <el-descriptions-item >
         <template slot="label">C.2&nbsp;初步等级得分</template>
         {{total}}
       </el-descriptions-item>
       <el-descriptions-item >
         <template slot="label">C.3&nbsp;老年人能力初步等级</template>
         <div v-for="item in rule">
            <span v-if="primaryRank === item.rank">{{item.description}}</span>
         </div>
       </el-descriptions-item>
       <el-descriptions-item >
         <template slot="label">C.4&nbsp;能力等级变更依据</template>
         <div v-for="item in reasonList">
           <span v-if="rankChangeReason.includes(item.value)">{{item.label}}</span>
         </div>
       </el-descriptions-item>
       <el-descriptions-item >
         <template slot="label">C.5&nbsp;老年人能力最终等级</template>
         <div v-for="item in rule">
           <span v-if="ultimateRank === item.rank">{{item.description}}</span>
         </div>
       </el-descriptions-item>
     </el-descriptions>
     </div>
     <el-divider></el-divider>
     <div>
       <span style="font-size: 12px;color: #606266;">评估地点__________________</span>
       <el-descriptions column="2" style="margin-top: 10px">
         <el-descriptions-item>
           <template slot="label">评估员签名</template>
           ______、_______
         </el-descriptions-item>
         <el-descriptions-item>
           <template slot="label">日期</template>
           _____年___月___日
         </el-descriptions-item>
         <el-descriptions-item>
           <template slot="label">信息提供者签名</template>
           ______
         </el-descriptions-item>
         <el-descriptions-item>
           <template slot="label">日期</template>
           _____年___月___日
         </el-descriptions-item>
       </el-descriptions>
     </div>
   </el-card>
 </div>
</template>

<script>
export default {
  name: "Report",
  props:{
    subjectData:{
      type:Array,
      default:[]
    },
    scores:{
      type:Array,
      default:[]
    },
    primaryIndex:{
      type:Array,
      default:[]
    },
    total:{
      type:Number,
      default:0
    },
    primaryRank:{
      type:String,
      default:''
    },
    rankChangeReason:{
      type:Array,
      default:[]
    },
    ultimateRank:{
      type:String,
      default:''
    },
    info:{
      type:Object,
      default:{
        financialSource:'',
        medicalPayments:''
      }
    }
  },
  watch: {
    "info": {
      handler(newVal, oldVal) {
        this.info.medicalPayments = newVal.medicalPayments
      }
    },
    "scores":{
      handler(newVal, oldVal) {
        console.log(JSON.stringify(newVal))
      }
    }
  },
  data(){
    return{
      rule:[],
      reasonList:[
        {id:'1',label:'①   确诊为痴呆（F00-F03）；',value:'1'},
        {id:'2',label:'②   精神科专科医生诊断的有其他精神和行为障碍疾病（F04-F99）',value:'2'},
        {id:'3',label:'③   近30天内发生过2次及以上照护风险事件（如跌倒、噎食、自杀、自伤、走失等）',value:'3'},
      ],
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
      nationList: [
        {
          id: 1,
          info: "汉族",
          value: 1,
        },
        {
          id: 2,
          info: "壮族",
          value: 2,
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
    }
  },
  mounted() {
    this.request.get("/rule").then(res => {
      if (res.code === '200') {
        this.rule = res.data
      } else {
        return false;
      }
    })
  }
}
</script>

<style scoped>

</style>