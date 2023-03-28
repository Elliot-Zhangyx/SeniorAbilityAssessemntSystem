<template>
  <div class="wrap">
    <template>
      <el-backtop target=".wrap">
        <i class="el-icon-caret-top"></i>
      </el-backtop>
    </template>
    <div style="width: 100%;margin: 0 auto;">
      <el-steps  :active="active" simple>
        <el-step title="信息修改" icon="el-icon-edit" ></el-step>
        <el-step title="评估详情" icon="el-icon-s-promotion"></el-step>
        <el-step title="报告详情" icon="el-icon-s-claim"></el-step>
      </el-steps>
    </div>

<!--    <el-button style="margin-top: 12px;" @click="next">下一步</el-button>-->


<!--    <el-radio-group v-model="tabPosition" style="margin-bottom: 30px;float: right">-->
<!--      <el-radio-button label="top">top</el-radio-button>-->
<!--      <el-radio-button label="right">right</el-radio-button>-->
<!--      <el-radio-button label="bottom">bottom</el-radio-button>-->
<!--      <el-radio-button label="left">left</el-radio-button>-->
<!--    </el-radio-group>-->
    <el-card style="width: 80%;position: relative;margin: 20px auto">
      <div slot="header" class="clearfix">
        <span style="font-size: 12px">姓名：<el-tag type="success">{{form.assessedName}}</el-tag>&emsp;&emsp;&emsp;身份证号：<el-tag type="success">{{form.assessedIdentityCard}}</el-tag></span>
        <el-button @click="beforePage(beforeFlag)" style="float: right;" size="medium" type="primary" v-show="showBefore">上一步&nbsp;<i class="el-icon-arrow-up"></i></el-button>
<!--        <el-button @click="beforePage2()" v-if="beforeFlag !== 0" style="float: right;" size="medium" type="primary" v-show="showBefore">上一步&nbsp;<i class="el-icon-arrow-up"></i></el-button>-->
      </div>
<!--      <div style="margin-left: 20px">-->
<!--        <el-form  ref="infoForm" :rules="rules" :model="form" style="padding: 20px 30px;"  label-width="200px" v-show="showInfo">-->
<!--          <el-form-item prop="assessId" style="margin-bottom: 20px" label="A.1.1 评估编号">-->
<!--            <el-input size="medium" v-model="form.assessId" placeholder="请输入内容" style="width: 80%" disabled></el-input>-->
<!--          </el-form-item>-->
<!--          <br>-->
<!--          <el-form-item prop="assessDate" label="A.1.2 评估基准日期" style="margin-bottom: 20px">-->
<!--            <el-col :span="4" >-->
<!--              <el-date-picker type="date"-->
<!--                              placeholder="选择日期"-->
<!--                              v-model="form.assessDate"-->
<!--                              style="width: 100%;"></el-date-picker>-->
<!--            </el-col>-->
<!--            <el-col class="line" :span="1">&emsp;&nbsp;-</el-col>-->
<!--            <el-col :span="4">-->
<!--              <el-time-picker type="date" placeholder="选择时间" v-model="form.assessDate" style="width: 100%;"></el-time-picker>-->
<!--            </el-col>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="A.1.3 评估原因" prop="assessReason" style="margin-bottom: 80px">-->
<!--            <el-checkbox-group v-model="assessReason" :max="1">-->
<!--              <el-checkbox label="1 接受服务前初评" name="assess_reason" ></el-checkbox>-->
<!--              <el-checkbox label="2 接受服务后的常规评估" name="assess_reason" ></el-checkbox>-->
<!--              <el-checkbox label="3 状况发生变化后的即时评估" name="assess_reason" ></el-checkbox>-->
<!--              <el-checkbox label="4 因评估结果有疑问进行的复评" name="assess_reason" ></el-checkbox>-->
<!--            </el-checkbox-group>-->
<!--          </el-form-item>-->
<!--          <el-button @click="nextTabs" style="float: right;" size="medium" type="primary">下一步<i class="el-icon-arrow-down"></i></el-button>-->
<!--          <br>-->
<!--          <br>-->
<!--        </el-form>-->
<!--      </div>-->
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
import Report from "@/components/Report";

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
      rule:[],
      total:0,
      scores:[],
      tab_id:[],
      target:[],
      subjectData:[],
      totalList:[],
      titleList:[],
      primaryIndex:[],
      result:'',
      active: 0,
      beforeFlag:0,
      tabPosition: 'left',
      tabName:'0',
      assessReason:[],
      rankChangeReason:[],
      showInfo:true,
      showBefore:false,
      showTabs:true,
      showReport:false,
      showResult:false,
      rankChangeFlag:'info',
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

      },
      conclusion:{
        assessId:'',
        result:{}
      },
      assessor:{
        assessId :"",
        assessorId: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')).id:0
      },
      single_score:'0',
      rules: {
        assessId: [
          {required: true, message: '评估编号不能为空', trigger: 'blur'},
        ],
        assessDate: [
          {required: true, message: '请输入日期', trigger: 'change'},
        ],
        assessReason: [{required: true,validator: validateAssessReason, trigger: 'change'}],
      },
      dailyLifeList:[
           {
            id: 1,
            flag:'eat',
            label: 'B.1.1 进食：指用餐具将食物由容器送到口中、咀嚼、吞咽等过程',
            selectList: [
              {value: '10', label: '10 分，可独立进食（在合理的时间内独立进食准备好的食物）'},
              {value: '5', label: '5 分， 需部分帮助（进食过程中需要一定帮助，如协助把持餐具）'},
              {value: '0', label: '0 分， 需极大帮助或完全依赖他人，或有留置营养管'},
            ],
             score:'10'
          },
          {
            id: 2,
            flag:'bath',
            label: 'B.1.2 洗澡',
            selectList: [
              {value: '5', label: '5 分， 准备好洗澡水后，可自己独立完成洗澡过程'},
              {value: '0', label: '0 分， 在洗澡过程中需他人帮助'},
            ],
            score:'5'
          },
          {
            id: 3,
            flag:'makeup',
            label: 'B.1.3 修饰：指洗脸、刷牙、梳头、刮脸等',
            selectList: [
              {value: '5', label: '5 分， 可自己独立完成'},
              {value: '0', label: '0 分， 需他人帮助'},
            ],
            score:'5'
          },
          {
            id: 4,
            flag:'wear',
            label: 'B.1.4 穿衣：指穿脱衣服、系扣、拉拉链、穿脱鞋袜、系鞋带',
            selectList: [
              {value: '10', label: '10 分，可独立完成'},
              {value: '5', label: '5 分， 需部分帮助（能自己穿脱，但需他人帮助整理衣物、系扣/鞋带、拉拉链）'},
              {value: '0', label: '0 分， 需极大帮助或完全依赖他人'},
            ],
            score:'10'
          },
          {
            id: 5,
            flag:'shit',
            label: 'B.1.5 大便控制',
            selectList: [
              {value: '10', label: '10 分，可控制大便'},
              {value: '5', label: '5 分， 偶尔失控（每周<1 次），或需要他人提示'},
              {value: '0', label: '0 分， 完全失控'},
            ],
            score:'10'
          },
        {
          id: 6,
          flag:'pee',
          label: 'B.1.6 小便控制',
          selectList: [
            {value: '10', label: '10 分，可控制小便'},
            {value: '5', label: '5 分， 偶尔失控（每天<1 次，但每周>1 次），或需要他人提示'},
            {value: '0', label: '0 分， 完全失控，或留置导尿管'},
          ],
          score:'10'
        },
        {
          id: 7,
          flag:'goToilet',
          label: 'B.1.7 如厕：包括去厕所、解开衣裤、擦净、整理衣裤、冲水',
          selectList: [
            {value: '10', label: '10 分，可独立完成'},
            {value: '5', label: '5 分， 需部分帮助（需他人搀扶去厕所、需他人帮忙冲水或整理衣裤等）'},
            {value: '0', label: '0 分， 需极大帮助或完全依赖他人'},
          ],
          score:'10'
        },
        {
          id: 8,
          flag:'bedTransfer',
          label: 'B.1.8 床椅转移',
          selectList: [
            {value: '15', label: '15 分，可独立完成'},
            {value: '10', label: '10 分，需部分帮助（需他人搀扶或使用拐杖）'},
            {value: '5', label: '5 分， 需极大帮助（较大程度上依赖他人搀扶和帮助）'},
            {value: '0', label: '0 分， 完全依赖他人'},
          ],
          score:'15'
        },
        {
          id: 9,
          flag:'walk',
          label: 'B.1.9 平地行走',
          selectList: [
            {value: '15', label: '15 分，可独立在平地上行走 45m'},
            {value: '10', label: '10 分，需部分帮助（因肢体残疾、平衡能力差、过度衰弱、视力等问题，在一定程度上需他人地搀扶或使用拐杖、助行器等辅助用具）'},
            {value: '5', label: '5 分， 需极大帮助（因肢体残疾、平衡能力差、过度衰弱、视力等问题，在较大程度上依赖他人搀扶，或坐在轮椅上自行移动）'},
            {value: '0', label: '0 分， 完全依赖他人'},
          ],
          score:'15'
        },
        {
          id: 10,
          flag:'floor',
          label: 'B.1.10 上下楼梯\n',
          selectList: [
            {value: '10', label: '10 分，可独立上下楼梯（连续上下 10-15 个台阶）'},
            {value: '5', label: '5 分， 需部分帮助（需他人搀扶，或扶着楼梯、使用拐杖等）'},
            {value: '0', label: '0 分， 需极大帮助或完全依赖他人'},
          ],
          score:'10'
        },
        // {
        //   id: 11,
        //   flag:'dailyLife',
        //   label: 'B.1.11  日常生活活动总分',
        //   selectList: [
        //   ],
        //   score:0
        // },
        // {
        //   id: 12,
        //   flag:'dailyRank',
        //   label: 'B.1 日常生活活动分级',
        //   selectList: [
        //     {value: '0', label: '0 能力完好：总分 100 分'},
        //     {value: '1', label: '1 轻度受损：总分 65-95 分'},
        //     {value: '2', label: '2 中度受损：总分 45-60 分'},
        //     {value: '3', label: '3 重度受损：总分≤40 分'},
        //   ],
        //   score:0
        // },


      ],
      reasonList:[
        {id:1,label:'①   有认知障碍/痴呆、精神疾病者，在原有能力级别上提高一个等级',value:'1'},
        {id:2,label:'②   近 30 天内发生过 2 次及以上跌倒、噎食、自杀、走失者，在原有能力级别上提高一个等级',value:'2'},
        {id:3,label:'③   处于昏迷状态者，直接评定为重度失能',value:'3'},
      ],
      rankList:[
        {id:0,label:'0级       能力完好',value:'0',tag:'success'},
        {id:1,label:'1级       轻度失能',value:'1',tag:'primary'},
        {id:2,label:'2级       中度失能',value:'2',tag:'warning'},
        {id:3,label:'3级       重度失能 ',value:'3',tag:'danger'},
      ],
      info:{}
    };
  },
  activated() {
    this.loadName()
  },
  created() {
  },
  mounted() {
    this.loadEdit()
    this.getTarget()
  },
  methods: {
    async loadEdit() {
        let id = this.$route.params.assessId
        console.log('id'+id)
        let assessedId = 0
        await this.request.get("/assess/editAssess", {params: {assessId: id}}).then(res => {
          if (res.code === '200') {
            console.log(res.data)
            assessedId = res.data.assessedId
            this.assessor.assessId = res.data.assessId
            this.form = res.data
            this.assessReason.push(res.data.assessReason)
            this.result = res.data.conclusion.result
          } else {
            this.$message.error("系统错误")
          }
        })

        this.request.get("/senior/" + assessedId).then(res => {
          if (res.code === '200') {
            this.info = res.data
            this.form.assessedName = res.data.assessedName;
            this.form.assessedIdentityCard = res.data.identityCard;
          } else {
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
          this.form.totalScore = this.total.toString()
            this.request.post("/assess",this.form).then(res=>{
              if(res.code === '200'){
                this.getReport()
                if(this.insertConclusion() && this.insertAssessor()) {
                  this.$message.success("提交成功")
                  //隐藏控件
                  this.showTabs = false
                  this.showReport = true
                  this.showResult = false
                  this.showBefore = false
                  //更换步骤条
                  if (this.active === 1) this.active = 2;
                }
              }
              else{
                this.$message.error("提交失败")
              }
            })
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



      let result = JSON.parse(this.result)
      for(let i =0;i<result.length;i++){
          for(let j=0;j<result[i].length;j++){
            for(let p=0;p<this.subjectData.length;p++){
              if(result[i][j].mouduleId === this.subjectData[p].id) {
                this.subjectData[p].subjectList[j].subjectScore = result[i][j].score
                if(this.subjectData[p].subjectList[j].subjectScore !== ''&&this.subjectData[p].subjectList[j].subjectScore !== null)
                  this.total +=parseInt(this.subjectData[p].subjectList[j].subjectScore)
              }
            }
        }
      }

      for (let i = 0; i < this.subjectData.length; i++) {
        this.scores.push([])
        for (let j = 0; j < this.subjectData[i].subjectList.length; j++) {
          this.scores[i].push({'subjectUid':this.subjectData[i].subjectList[j].subjectUid,'mouduleId':this.subjectData[i].subjectList[j].moduleId,'subjectTitle': this.subjectData[i].subjectList[j].subjectTitle,'score':this.subjectData[i].subjectList[j].subjectScore})
        }
      }
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
      }
    },
    async insertConclusion(){
      let bool = ''
      this.conclusion.assessId = this.form.assessId
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
      let primaryRank = this.form.primaryRank
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
          this.showBefore = true
          this.showInfo = false
          this.beforeFlag += 1
          this.insertAssessor()
        }
      })
    },
    nextResult(){
      // this.request.post("/assess/result",{
      //   params:{
      //     recognition:this.spirit.recognition,
      //     conscious:this.sensory.conscious,
      //     stupidStatus:this.info.stupidStatus,
      //     spiritDisease:this.info.spiritDisease,
      //     fallDown:this.info.fallDown,
      //     beLost:this.info.beLost,
      //     choke:this.info.choke,
      //     suicide:this.info.suicide,
      //   }
      // }).then(res=>{
      //   if(res.code === '200'){
      //     //隐藏控件
      //     this.showTabs = false
      //     this.showResult = true
      //   }
      // })
        this.showTabs = false
        this.showBefore = true
        this.showResult = true
        this.beforeFlag += 1
      // var state = ""
      // if(cognitions[1].checked||cognitions[2].checked){
      //   state="需进行专科评估"
      // }
      // else{
      //   state="无"
      // }


    },
    beforePage(){
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
      console.log("我不行了"+JSON.stringify(this.scores))
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
    async insertAssessor() {
      let bool = ''
      await this.request.post("/assessorAssess", this.assessor).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
  }
}
</script>

<style scoped>
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
</style>