<template>
  <div class="wrap">
    <template>
      <el-backtop target=".wrap">
        <i class="el-icon-caret-top"></i>
      </el-backtop>
    </template>
    <div style="width: 100%;margin: 0 auto;">
      <el-steps  :active="active" simple>
        <el-step title="信息填写" icon="el-icon-edit" ></el-step>
        <el-step title="能力评估" icon="el-icon-s-promotion"></el-step>
        <el-step title="生成报告" icon="el-icon-s-claim"></el-step>
      </el-steps>
    </div>
    <el-card style="width: 80%;position: relative;margin: 20px auto" shadow="never">
      <div slot="header" class="clearfix">
        <span style="font-size: 12px">姓名：<el-tag type="success">{{form.assessedName}}</el-tag>&emsp;&emsp;&emsp;身份证号：<el-tag type="success">{{form.assessedIdentityCard}}</el-tag></span>
        <el-button @click="beforePage(beforeFlag)" style="float: right;" size="medium" type="primary" v-show="showBefore">上一步&nbsp;<i class="el-icon-arrow-up"></i></el-button>
<!--        <el-button @click="beforePage2()" v-if="beforeFlag !== 0" style="float: right;" size="medium" type="primary" v-show="showBefore">上一步&nbsp;<i class="el-icon-arrow-up"></i></el-button>-->
      </div>
      <div style="margin-left: 20px">
        <el-form  ref="infoForm" :rules="rules" :model="form" style="padding: 20px 30px;"  label-width="200px" v-show="showInfo">
          <el-form-item prop="assessId" style="margin-bottom: 20px" label="A.1.1 评估编号">
            <el-input size="medium" v-model="form.assessId" placeholder="请输入内容" style="width: 80%" disabled></el-input>
          </el-form-item>
          <br>
          <el-form-item prop="assessDate" label="A.1.2 评估基准日期" style="margin-bottom: 20px">
            <el-col :span="4" >
              <el-date-picker type="date"
                              placeholder="选择日期"
                              v-model="form.assessDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="1">&emsp;&nbsp;-</el-col>
            <el-col :span="4">
              <el-time-picker type="date" placeholder="选择时间" v-model="form.assessDate" style="width: 100%;"></el-time-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="A.1.3 评估原因" prop="assessReason" style="margin-bottom: 80px">
            <el-checkbox-group v-model="assessReason" :max="1">
              <el-checkbox label="1 接受服务前初评" name="assess_reason" ></el-checkbox>
              <el-checkbox label="2 接受服务后的常规评估" name="assess_reason" ></el-checkbox>
              <el-checkbox label="3 状况发生变化后的即时评估" name="assess_reason" ></el-checkbox>
              <el-checkbox label="4 因评估结果有疑问进行的复评" name="assess_reason" ></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-button @click="nextTabs" style="float: right;" size="medium" type="primary">下一步<i class="el-icon-arrow-down"></i></el-button>
          <br>
          <br>
        </el-form>
      </div>
      <el-tabs :tab-position="tabPosition" style="height: 100%;width: 100%;" v-show="showTabs" v-model="tabName">
          <el-tab-pane  :name="tab.id.toString()" v-for="tab in subjectData">
            <template slot="label">
              {{tab.title}}
            </template>
            <el-form style="padding: 20px 20px" ref="seniorForm">
              <el-table :data="tab.subjectList" border style="width: 100%">
                <el-table-column label="测试题目" width="1000">
                  <template slot-scope="scope">
                      <el-form-item>
                        <template slot="label">
                          <div style="width: 100%">
                            <b>{{scope.row.subjectTitle}}</b>
                          </div>
                        </template>
                        <el-radio-group style="width: 100%" v-model="scope.row.subjectScore" @change="optionChange(tab.id.toString(),scope)">
                          <el-radio  v-for="options in scope.row.optionList" :key="options.score" :label="options.score" :value="options.score" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >{{options.subjectOption}}（<el-tag type="success">{{options.score}}分</el-tag>）</el-radio>
                        </el-radio-group>
                      </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column label="分数" style="width: 20%;"  align="center">
                  <template slot-scope="scope">
                    {{scope.row.subjectScore}}
                  </template>
                </el-table-column>
              </el-table>
            </el-form>
            <span>
              <b>总计得分：</b>{{total}}
            </span>
            <el-button @click="nextStep(tab.id.toString())" style="float: right;" size="medium" type="primary">下一步&nbsp;<i class="el-icon-arrow-down"></i></el-button>
          </el-tab-pane>
        </el-tabs>
      <div v-show="showResult">
        <span>总分：{{total}}</span>
        <el-timeline style="margin: 5% 10%">
          <el-timeline-item type="primary">
            <h4>综合评估初步等级判断</h4>
            <br>
            <el-card>
              <el-tag :type="form.primaryRank === item.rank?item.tag:'info'" v-for="item in rule" style="width: 100%;margin-bottom: 1px;height: 40px"><span style="font-size: 12px;line-height: 40px;">&emsp;<i class="el-icon-star-on"></i>&emsp;&emsp;{{item.description}}</span></el-tag>
            </el-card>
          </el-timeline-item>
          <el-timeline-item color="Gold">
            <h4>变更条款</h4>
            <br>
            <el-card>
              <el-tag :type="rankChangeReason.includes(item.value)?'warning':'info'" v-for="item in reasonList" style="width: 100%;margin-bottom: 1px;height: 40px;" ><span style="font-size: 12px;line-height: 40px">&emsp;{{item.label}}</span></el-tag>
            </el-card>
          </el-timeline-item>
          <el-timeline-item  color="purple">
            <h4>综合评估最终等级</h4>
            <br>
            <el-card>
              <el-tag :type="form.ultimateRank === item.rank?item.tag:'info'" v-if="form.ultimateRank === item.rank" v-for="item in rule" style="width: 100%;margin-bottom: 1px;height: 50px"><span style="font-size: 12px;line-height: 50px">&emsp;<i class="el-icon-star-on"></i>&emsp;&emsp;{{item.description}}</span></el-tag>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        <br>
        <div style="text-align: center;margin:auto;">
          <el-button-group>
            <el-button type="primary" @click="save" style="height: 40px;width: 100px;margin-right: 5px"><span style="font-size: 15px"><i class="el-icon-collection"></i>&nbsp;保存</span></el-button>
            <el-button type="success" @click="save" style="height: 40px;width: 100px;"><span style="font-size: 15px"><i class="el-icon-check"></i>&nbsp;提交</span></el-button>
          </el-button-group>
        </div>

        <br>
      </div>
      <Report v-show="showReport"
              :subjectData="subjectData"
              :scores="scores"
              :primaryIndex="primaryIndex"
              :primaryRank="form.primaryRank"
              :total="total"
              :rankChangeReason="rankChangeReason"
              :ultimateRank="form.ultimateRank"
              :info="info"
      ></Report>
    </el-card>
    <br>
  </div>
</template>

<script>
import Report from '@/components/Report'
export default {
  name: "AbilityAssess",
  components:{
    Report
  },
  data() {
    const validateAssessReason = (rule, value, callback) => {
      if (this.assessReason.length > 0) {
        callback();
      } else {
        return callback(new Error('请选择评估原因'));
      }
    };
    return {
      str:"",
      rule:[],
      total:0,
      scores:[],
      tab_id:[],
      target:[],
      subjectData:[],
      totalList:[],
      titleList:[],
      primaryIndex:[],
      headerBg:'headerBg',
      active: 0,
      beforeFlag:0,
      tabPosition: 'top',
      tabName:'0',
      assessReason:[],
      rankChangeReason:[],
      showInfo:true,
      showBefore:false,
      showTabs:false,
      showReport:false,
      showResult:false,
      rankChangeFlag:'info',
      activeStep:0,
      form: {
        assessId:'',
        assessDate:'',
        assessReason:'',
        assessedId:this.$route.params.assessedId,
        assessedName:'',
        assessedIdentityCard:'',
        totalScore:'',
        primaryRank:'',
        rankChangeReason:'',
        ultimateRank:'',
        assessSign:'',
        informationProviderSign:'',
        isFinish:0,
        total:''
      },
      assessor:{
        assessId :"",
        assessorId: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')).id:0
      },
      single_score:'0',
      rules: {
        assessId: [
          {required: true, message: '系统错误', trigger: 'blur'},
        ],
        assessDate: [
          {required: true, message: '系统错误', trigger: 'change'},
        ],
        assessReason: [{required: true,validator: validateAssessReason, trigger: 'change'}],
        // assessReason:[
        //   { required: true, message: '请选择评估原因', trigger: 'change'},
        // ],

      },
      assessReasonList:[
        {
          value: '1',
          label: '首次评估'
        },
        {
          value: '2',
          label: '常规评估'
        },
        {
          value: '3',
          label: '即时评估'
        },
        {
          value: '4',
          label: '因对评估结果有疑问进行的复评'
        },
        {
          value: '5',
          label: '其他'
        },
      ],
      reasonList:[
        {id:'1',label:'①   确诊为痴呆（F00-F03）；',value:'1'},
        {id:'2',label:'②   精神科专科医生诊断的有其他精神和行为障碍疾病（F04-F99）',value:'2'},
        {id:'3',label:'③   近30天内发生过2次及以上照护风险事件（如跌倒、噎食、自杀、自伤、走失等）',value:'3'},
      ],
      rankList:[
        {id:'0',label:'0级       能力完好',value:'0',tag:'success'},
        {id:'1',label:'1级       能力轻度受损（轻度失能）',value:'1',tag:'primary'},
        {id:'2',label:'2级       能力中度受损（中度失能）',value:'2',tag:'warning'},
        {id:'3',label:'3级       能力重度失能（重度失能）',value:'3',tag:'danger'},
        {id:'4',label:'4级       能力完全丧失（完全失能）',value:'4',tag:'danger'},
      ],
      info:{
        financialSource:'',
        medicalPayments:''
      },
      conclusion:{
        assessId:'',
        result:{}
      }
    };
  },
  activated() {
    this.loadName()
    // if (category && keyword) {
    //   this.getCodeResult(category, keyword)
    // }
  },
  created() {
  },
  mounted() {
    this.getTime()
    this.assessIdCreate()
    this.loadName()
    this.getTarget()
  },
  methods: {
    getTime() {
      this.form.assessDate = new Date()
    },
    assessIdCreate(){
      this.request.post("/senior/assessId").then(res=>{
        if(res.code === '200'){
          this.form.assessId = res.data;
          this.conclusion.assessId = res.data;
          this.assessor.assessId = res.data
        }
        else{
          this.$message.error("系统错误")
        }
      })
    },
    loadName(){
        this.request.get("/senior/" + this.$route.params.assessedId).then(res => {
          if (res.code === '200') {
            this.info = res.data
            this.form.assessedName = res.data.assessedName;
            this.form.assessedIdentityCard = res.data.identityCard;
          } else {
            this.$message.error("系统错误")
          }
        })
    },
    async getTarget() {
      let target = []
      let tabName = []
      await this.request.get("/target").then(res => {
        if (res.code === '200') {
          target = res.data
        } else {
          this.$message.error("系统错误")
        }
      })
      for (let i = 0; i < target.length; i++) {
        await this.request.get("/module/subject/" + target[i].targetId).then(res => {
          if (res.code === '200') {
            this.subjectData.push(res.data)
          } else {
            this.$message.error("系统错误")
          }
        })
      }
      this.subjectData.forEach(item => {
        tabName.push(item.id.toString())
      })
      this.tabName = tabName[0]
      this.tab_id = tabName


      console.log(JSON.stringify(this.subjectData))
      for (let i = 0; i < this.subjectData.length; i++) {
          this.scores.push([])
          for (let j = 0; j < this.subjectData[i].subjectList.length; j++) {
            this.scores[i].push({'subjectUid':this.subjectData[i].subjectList[j].subjectUid,'mouduleId':this.subjectData[i].subjectList[j].moduleId,'subjectTitle': this.subjectData[i].subjectList[j].subjectTitle,'score':''})
          }
      }
      console.log(this.scores)
    },
    save(){
      this.$confirm('此操作将提交数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let user = localStorage.getItem("user").token ? JSON.parse(localStorage.getItem("user")) : ''
        if(user === '') {
          //插入评估结果
          this.form.assessReason = this.assessReason[0]
          //if(this.insertSpirit() && this.insertDaily() && this.insertSport() && this.insertSensoryAndAttend() && this.insertAssessor()) {
            this.form.isFinish = 1
            this.form.totalScore = this.total.toString()
            this.request.post("/assess",this.form).then(res=>{
              if(res.code === '200'){
                  this.getReport()
                 if(this.insertConclusion()&&this.insertAssessor()) {
                   this.request.get("/senior/updateIsAssess/"+this.form.assessedId).then(res=>{
                        if(res.code==='200'){
                          this.$message.success("提交成功")
                          //隐藏控件
                          this.showTabs = false
                          this.showReport = true
                          this.showResult = false
                          this.showBefore = false
                          //更换步骤条
                          if (this.active === 1) this.active = 2;
                        }
                   })
                 }
              }
              else{
                this.$message.error("提交失败")
              }
            })
          // }
          // else{
          //   this.$message.error("提交失败")
          // }
        }else {
          this.$message.error("系统错误，请重新登录")
        }

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        });
      });
    },
    async insertAssessor() {
      let bool = ''
      await this.request.post("/assessorAssess", this.assessor).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
    nextStep(tab_id) {
      let next = 0
      for (let i = 0; i < this.tab_id.length; i++) {
        if (tab_id === this.tab_id[i]) {
          next = i
        }
      }
      if (next < this.tab_id.length - 1) {
        next = next + 1
        this.tabName = this.tab_id[next]
      } else {
        this.assessResult()
        this.showTabs = false
        this.showResult = true
        this.beforeFlag += 1
      }
    },
    async insertConclusion(){
      let bool = ''
      this.conclusion.result = JSON.stringify(this.scores)
      await this.request.post("/conclusion",this.conclusion).then(res=>{
        bool = res.code
      })
      console.log('bool'+bool)
      return bool === '200';
    },
    async assessResult() {
      await this.request.get("/rule").then(res => {
        if (res.code === '200') {
          this.rule = res.data
        } else {
          return false;
        }
      })
      let max = parseInt(this.rule[0].rank);
      this.rule.forEach(item => {
        if (this.total >= parseInt(item.rankLeft) && this.total <= parseInt(item.rankRight)) {
          this.form.primaryRank = item.rank
        }
        if (max < parseInt(item.rank)) {
          max = parseInt(item.rank)
        }
      })
      let primaryRank = parseInt(this.form.primaryRank)
      let stupidStatus = this.info.stupidStatus
      let spiritDisease = this.info.spiritDisease
      let fallDown = this.info.fallDown
      let beLost = this.info.beLost
      let choke = this.info.choke
      let suicide = this.info.suicide

      let finalRank = primaryRank
      let changeRemarks = []
      let changeRemark = ""
      if (primaryRank < max) {
        if (parseInt(stupidStatus) !== 0 || parseInt(spiritDisease) !== 0) {
          finalRank += 1
          changeRemarks.push("1")
        }
        if (parseInt(fallDown) >= 2 || parseInt(beLost) >= 2 || parseInt(choke) >= 2 || parseInt(suicide) >= 2) {
          finalRank += 1
          changeRemarks.push("2")
        }

        //获取受项影响的评估规则
        this.subjectData.forEach((item1,index1)=>{
          item1.subjectList.forEach((item2,index2)=>{
            item2.optionList.forEach((item3,index3)=>{
              if(item3.rankChange ==="1" && this.scores[index1][index2].score===item3.score){
                finalRank += 1
              }
              else if(item3.rankChange ==="2" && this.scores[index1][index2].score=== item3.score){
                finalRank += 2
              }
              else if(item3.rankChange ==="MAX" && this.scores[index1][index2].score=== item3.score){
                this.getSplitWord(item3.subjectOption)
                changeRemarks.push("4")
                finalRank = max
              }
            })
          })
        })


        if (finalRank > max) {
          finalRank = max
        }
        changeRemark = changeRemarks.join(',')
      }
      this.rankChangeReason = changeRemarks
      this.form.rankChangeReason = changeRemark
      this.form.ultimateRank = finalRank.toString()
    },
    getSplitWord(content){
        this.request.get("/option/split/getOption",{params:{content:content}}).then(res=>{
        if(res.code ==='200') {
            this.str =  res.data
            this.reasonList.push({id:'4',label:'④   '+this.str+',直接判定为完全失能；',value:'4'})
        }
      })
    },
    nextTabs(){
      let id = this.form.assessId;
      this.$refs['infoForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.active === 0) this.active = 1;
          this.showTabs = true
          this.showInfo = false
          this.beforeFlag += 1
            this.showBefore = true
          this.form.assessReason = this.assessReason[0]
          this.request.post("/assess",this.form);
          this.insertAssessor()
        }
      })
    },
    beforePage(){
      console.log("beforeFlag"+this.beforeFlag)
      if(this.beforeFlag === 1) {
        if (this.active === 1) {
          this.active = 0;
          this.showTabs = false
          this.showResult = false
          this.showInfo = true
          this.showBefore = false
        }
      }
      else if(this.beforeFlag === 2){
        this.showTabs = true
        this.showResult = false
      }
      this.beforeFlag -= 1
    },
    optionChange(tab_id,scope){
      this.total = 0
      for (let i = 0; i < this.tab_id.length; i++) {
        if (tab_id === this.tab_id[i]) {
          this.scores[i][scope.$index].score = scope.row.subjectScore
        }
      }
      for (let i = 0; i < this.scores.length; i++) {
        for (let j = 0; j < this.scores[i].length; j++) {
          if(this.scores[i][j].score !== '' && this.scores[i][j].score !== null) this.total += parseInt(this.scores[i][j].score)
        }
      }
    },
    getReport(){
      let scoreSum = 0
      for(let i = 0 ;i<this.scores.length;i++){
        for(let j = 0;j<this.scores[i];j++){
          scoreSum += parseInt(this.scores[i][j].score)
        }
        this.totalList.push(scoreSum)
      }
      for(let i = 0 ;i<this.subjectData.length;i++){
        this.titleList.push(this.subjectData[i].title)
      }
      for(let i = 0 ;i<this.subjectData.length;i++){
        this.primaryIndex.push({'title':this.titleList[i],'score':this.totalList[i]})
      }
    },
  }
}
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
.wrap {
  height:100vh;/*height可以根据你要创建的页面进行修改*/
  /*width: 100vw;*/
  overflow-x: hidden;
  overflow-y: scroll;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
hr{
  background-color: #e3e3e3;
  height: 1px;
  border: none;
  margin: 10px auto;
}
.shadow{
  transition: background-color .25s ease;
  transition-property: background-color;
  transition-duration: 0.25s;
  transition-timing-function: ease;
  transition-delay: 0s;
}
.shadow:hover{
  background-color:#F5F5F5;
  /*-webkit-box-shadow: #ccc 0px 10px 10px;*/
  /*-moz-box-shadow: #EBEEF5;*/
  /*box-shadow: #EBEEF5;*/
}
.headerBg{
  background: #eee!important;
}
.el-table ::v-deep tbody tr:hover > td {
  background-color: transparent
}

</style>