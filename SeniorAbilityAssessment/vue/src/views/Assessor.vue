<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="assessedName"></el-input>
      <el-input style="width: 200px" placeholder="请输入身份证号" suffix-icon="el-icon-message" class="ml-5"  v-model="assessedIdentityCard"></el-input>
      <el-input style="width: 200px" placeholder="请输入性别" suffix-icon="el-icon-position" class="ml-5"  v-model="assessedGender"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatchSenior"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload :action="'http://'+serverIp+':9090/senior/import'"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange"
              :row-key="getRowKeys"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :row-class-name="tableRowClassName"
              :default-sort = "{prop: 'isAssess', order: 'descending'}"
    >
      <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
      <el-table-column prop="id" label="序号" width="40"></el-table-column>
      <el-table-column  label="最近一次评估日期" width="200">
        <template slot-scope="scope">
          {{scope.row.assessList.length ? scope.row.assessList[0].assessDate.toString().split("T")[0]: '---'}}
        </template>
      </el-table-column>
      <el-table-column prop="assessedName" label="姓名" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.assessedName }}</p>
<!--            <p>住址: {{ scope.row.address }}</p>-->
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{  scope.row.assessedName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="50"></el-table-column>
      <el-table-column prop="identityCard" label="身份证号" width="200">
        <template slot-scope="scope">
          {{bool?tableData[scope.$index].identityCard.replace(/^(.{6})(?:\w+)(.{4})$/, "\$1********\$2"):tableData[scope.$index].identityCard}}
          <i class="el-icon-view" @click="hideNumber"></i>
        </template>
      </el-table-column>
      <el-table-column prop="birthday" label="出生日期" width="200"></el-table-column>
      <el-table-column prop="nationality" label="民族" width="50"></el-table-column>
<!--      <el-table-column prop="address" label="家庭住址" width="200"></el-table-column>-->
<!--      <el-table-column prop="assessDate" label="评估日期"  sortable>-->
<!--        <template slot-scope="scope">-->
<!--          <i class="el-icon-time"></i>-->
<!--          <span class="ml-10">{{tableData[scope.$index].assessDate ? tableData[scope.$index].assessDate.replace("T", " ") : ""}}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="isAssess" label="评估状态"
                       :filters="[{ text: '正常', value: 0 }, { text: '常规评估', value: 1 },{text: '暂未评估',value: 2}]"
                       :filter-method="filterIsAssess"
                       filter-placement="bottom-end"
      >
        <template slot-scope="scope">
            <el-tag v-if="scope.row.isAssess === 0" type="success">{{scope.row.assessList.length >0? scope.row.assessList.length :'---'}}次</el-tag>
            <el-tag v-if="scope.row.isAssess === 1" type="primary">常规评估</el-tag>
            <el-tag v-if="scope.row.isAssess === 2" type="danger">暂未评估</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="300" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="viewDetails(scope.row.id)">查看详情 <i class="el-icon-s-opportunity"></i></el-button>
          <el-button type="success" class="ml-5" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="delSenior(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding:10px 0;display: flex">
      <div style="flex: 8;">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[2,5,10,20]"
            :page-size="pageSize"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total"
        >
        </el-pagination>
      </div>
      <div style="float: right;">
        <el-tag class="success"  effect="plain">&emsp;</el-tag>&nbsp;<span style="font-size: 13px;color: #606266">正常</span>&emsp;
        <el-tag class="warning">&emsp;</el-tag>&nbsp;<span style="font-size: 13px;color: #606266">距上一次评估已过12个月，需重新进行评估</span>&emsp;
        <el-tag class="danger">&emsp;</el-tag>&nbsp;<span style="font-size: 13px;color: #606266">暂未评估</span>
      </div>
    </div>

    <el-dialog title="评估详情" :visible.sync="assessedVisible" width="90%" >
      <el-button type="primary" style="margin-bottom: 10px" v-if="assessInfoList!=null" @click="beginAssess(assessedId)">开始评估 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatchAssess"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-button type="primary" style="margin-bottom: 10px;margin-left: 5px" @click="loadAssess()">刷新 <i class="el-icon-refresh"></i></el-button>
      <el-table :data="assessInfoList"
                :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}"
                border
                style="width: 100%;margin: 0 auto"
                @selection-change="handleSelectionChangeAssess"
                :row-key="getRowKeysAssess"
                :default-sort = "{prop: 'assessDate', order: 'descending'}"
      >
        <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
        <el-table-column property="id" label="Id" width="60"></el-table-column>
        <el-table-column prop="assessDate" label="评估日期" width="150"  sortable>
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span class="ml-10">{{scope.row.assessDate ? scope.row.assessDate.toString().split("T")[0] : ""}}</span>
          </template>
        </el-table-column>
        <el-table-column property="assessedName" label="姓名" width="80"></el-table-column>
        <el-table-column property="assessReason" label="评估原因" width="200"></el-table-column>
        <el-table-column property="totalScore" label="总分" width="100">
          <template slot-scope="scope">
            <span class="ml-10">{{scope.row.totalScore === ''|| scope.row.totalScore === null? '---':scope.row.totalScore}}</span>
          </template>
        </el-table-column>
        <el-table-column property="primaryRank" label="初步等级"
                         :filters="this.filterRank"
                         :filter-method="filterPrimary"
        >
          <template slot-scope="scope">
            <el-tag :type="item.tag" v-if="scope.row.primaryRank === item.rank" v-for="item in rule">{{scope.row.primaryRank}}级</el-tag>
          </template>
        </el-table-column>
        <el-table-column property="ultimateRank" label="最终等级"
                         :filters="this.filterRank"
                         :filter-method="filterUltimate"
        >
          <template slot="header">
            <span style="color: red">最终等级</span>
          </template>
          <template slot-scope="scope">
            <el-tag :type="item.tag" v-if="scope.row.ultimateRank === item.rank" v-for="item in rule">{{scope.row.ultimateRank}}级</el-tag>
          </template>
        </el-table-column>
        <el-table-column property="assessorName" label="评估员" >
          <template slot-scope="scope">
              <el-popover trigger="hover" placement="top" v-for="item in scope.row.userList" style="flex: 1">
                <p>姓名: {{ item.nickname }}</p>
                <p>电话号码: {{ item.phone }}</p>
                <p v-if="roleItem.flag === item.role" v-for="roleItem in role">身份: {{ roleItem.name }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium" :type="roleItem.tagColor" v-if="roleItem.flag === item.role" v-for="roleItem in role" style="margin-top:5px">{{item.nickname}}</el-tag>
                </div>
              </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="操作"  width="300" align="center">
          <template slot-scope="scope">
            <el-button type="primary" @click="lookReport(scope.row)" v-if="scope.row.userList.length>=2">查看报告</el-button>
            <el-button type="success" @click="handleEditAssess(scope.row.assessId)">编辑 <i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="delAssess(scope.row.id)"
            >
              <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <span style="float: left;font-size: 13px;margin-left:5px;font-weight: 400;color: #606266;">共&nbsp;{{assessInfoList.length}}&nbsp;条</span>
        <el-button type="primary" @click="assessedVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="seniorTitle" :visible.sync="dialogSeniorVisible" custom-class="customWidth" width="90%">
      <Senior @childEvent="changeSeniorVisible" @load="refresh"
              :form="seniorForm"
              :newMedicalPayments="medicalPayments"
              :newFinancialSource="financialSource"
              :newDisease="disease"
              :newWoundCondition = "woundCondition"
              :newSpecialMedicalCare ="specialMedicalCare"
              :newToothLoss = "toothLoss"
              :newDentureWear = "dentureWear"
              :newDysphagia = "dysphagia"
              :isEdit="isEdit"></Senior>
    </el-dialog>


    <el-dialog  :visible.sync="dialogReportVisible" custom-class="customWidth" width="90%">
      <Report
              :subjectData="subjectData"
              :scores="scores"
              :primaryIndex="primaryIndex"
              :primaryRank="assessResultForm.primaryRank"
              :total="assessResultForm.total"
              :rankChangeReason="rankChangeReason"
              :ultimateRank="assessResultForm.ultimateRank"
              :info="info"
      ></Report>
    </el-dialog>
  </div>
</template>

<script>
import Senior from "@/components/Senior";
import Report from "@/components/Report";
import {serverIp} from "../../public/config";
export default {
  components:{
    Senior,
    Report
  },
  name: "Assessor",
  data(){
    return{
      serverIp:serverIp,
      sortColor:0,
      rule:[],
      dialogReportVisible:false,
      seniorTitle:'信息填写',
      isEdit:0,
      id:0,
      assessedId:0,
      multipleSelection:[],
      multipleSelectionAssess:[],
      headerBg: 'headerBg',
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      form:{},
      assessResultForm:{},
      seniorForm:{
        medication: [
          {
            name: '',
            method:'',
            dosage:'',
            frequency:''
          }
        ]
      },
      bool:true,
      assessedName:'',
      assessedIdentityCard:'',
      assessedGender:'',
      assessedVisible:false,
      dialogSeniorVisible:false,
      assessInfoList:[],
      medicalPayments:[],
      financialSource:[],

      disease:[],
      woundCondition :[],
      specialMedicalCare :[],
      toothLoss :[],
      dentureWear :[],
      dysphagia :[],


      assessorList:[],
      filterRank:[],
      subjectData:[],
      scores:[],
      totalList:[],
      titleList:[],
      primaryIndex:[],
      rankChangeReason:[],
      result:'',
      info:{
        financialSource:'',
        medicalPayments:''
      },
      role:[]
    }
  },
  created() {
    this.load()
  },
  mounted() {
    this.request.get("/rule").then(res => {
      if (res.code === '200') {
        this.rule = res.data
        this.rule.forEach(item =>{
          this.filterRank.push({
            text:item.rank+"级   "+item.description,
            value:item.rank
          })
        })
      } else {
        return false;
      }
    })
    this.request.get("/role").then(res => {
      if (res.code === '200') {
        this.role = res.data
      } else {
        return false;
      }
    })
  },
  methods:{
    changeSeniorVisible(childValue){
      this.dialogSeniorVisible = childValue
    },
    refresh(){
      this.load()
    },
    handleAdd(){
      // this.$router.push("/senior",() => {})
      this.form = {}
      this.dialogSeniorVisible = true
      this.seniorTitle = '信息填写'
    },
    load(){
      //请求分页查询数据
      this.request.get("/senior/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          assessedName:this.assessedName,
          assessedIdentityCard:this.assessedIdentityCard,
          assessedGender:this.assessedGender
        }
      }).then(res=>{
       let data = res.data.records
        this.total = res.data.total
        for(let i=0;i<data.length;i++){
          if(data[i].assessList.length<=0){
            data.isAssess = 2
          }
          else if(data[i].assessList.length>0){
            if(this.compareDate(data[i].assessList[0].assessDate) > 365){
              data.isAssess = 1
            }
          }
        }
        this.tableData = data
      })

      // this.request.post("/assess/"+this.tableData).then(res => {
      //   if (res.code === '200') {
      //     console.log(JSON.stringify(res.data))
      //     this.assessInfoList = res.data
      //     this.assessedId = res.data.assessedId
      //   } else {
      //     this.$message.error("查看失败")
      //   }
      // })
    },
    loadAssess(){
      this.request.post("/assess/"+this.assessedId).then(res => {
        if (res.code === '200') {
          this.assessInfoList = res.data
          this.assessedId = res.data[0].assessedId
          console.log(this.assessInfoList)
        } else {
          this.$message.error("查看失败")
        }
      })
    },
    delSenior(id){
      this.request.delete("/senior/"+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }
        else{
          this.$message.error("删除失败")
        }
      })
    },
    delBatchSenior(){
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/senior/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    delAssess(id){
      this.request.delete("/assess/"+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.loadAssess()
        }
        else{
          this.$message.error("删除失败")
        }
      })
    },
    delBatchAssess(){
      let ids = this.multipleSelectionAssess.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/assess/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.loadAssess()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    getRowKeys(row) {
      return row.id
    },
    getRowKeysAssess(row) {
      return row.id
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    handleSelectionChangeAssess(val) {
      console.log(val)
      this.multipleSelectionAssess = val
    },
    reset(){
      // this.username=""
      // this.email=""
      // this.address=""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    exp(){
      window.open("http://"+this.serverIp+":9090/senior/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },
    //隐藏身份证
    hideNumber(){
      this.bool = ! this.bool;
    },
    beginAssess(id){
        let assess = this.$router.resolve({
          name:'AbilityAssess',
          params: {
            assessedId:id
          }
        })
      console.log(assess.href)
        window.open(assess.href, '_blank');
    },
    viewDetails(assessedId){
      this.assessedVisible = true
      this.request.post("/assess/"+assessedId).then(res => {
        if (res.code === '200') {
           console.log(JSON.stringify(res.data))
            this.assessInfoList = res.data
            this.assessedId = assessedId
        } else {
          this.$message.error("查看失败")
        }
      })
    },
    handleEditAssess(id){
      let assess = this.$router.resolve({
        name:'AssessEdit',
        params: {
          assessId:id
        }
      })
      window.open(assess.href, '_blank');
    },
    // handleEditAssess(id){
    //   let assess = this.$router.resolve({
    //     name:'AssessEdit',
    //     params: {
    //       id:id
    //     }
    //   })
    //   window.open(assess.href, '_blank');
    // },
    handleEdit(row){
      this.seniorTitle = '信息修改'
      this.isEdit = 1
      this.medicalPayments = row.medicalPayments.split(',')
      this.financialSource = row.financialSource.split(',')
      this.disease = row.disease.split(',')
      this.woundCondition = row.woundCondition.split(',')
      this.specialMedicalCare = row.specialMedicalCare.split(',')
      this.financialSource = row.financialSource.split(',')
      this.toothLoss = JSON.parse(row.toothLoss)
      this.dentureWear = row.dentureWear.split(',')
      this.dysphagia = row.dysphagia.split(',')
      this.seniorForm = row
      this.seniorForm.medication = row.medication ? JSON.parse(row.medication):[]
      //this.form = Object.assign({},row
      // )
      this.dialogSeniorVisible = true
    },
    filterUltimate(value, row) {
      return row.ultimateRank === value;
    },
    filterPrimary(value,row){
      return row.primaryRank === value;
    },
    tableRowClassName({row, rowIndex}) {
      if (row.assessList.length>0) {
          if( this.compareDate(row.assessList[0].assessDate) > 365){
            row.isAssess = 1;
            this.changeColorStatus(row);
            return 'warning-row';
          }
        return '';
      } else if (row.assessList.length <= 0) {
        row.isAssess = 2;
        this.changeColorStatus(row);
        return 'danger-row';
      }
      return '';
    },
    compareDate(date) {
      return (Math.abs(parseInt((new Date().getTime() - new Date(date).getTime()) / (1000 * 60 * 60 * 24))))
    },
    changeColorStatus(row){
      this.request.post("/senior",row).then(res => {
        if (res.code === '200') {
        } else {
          this.$message.error("查看失败")
        }
      })
    },
    filterIsAssess(value, row) {
      return row.isAssess === value;
    },
    async lookReport(row){
      let assessedId = 0
      await this.request.get("/assess/editAssess", {params: {assessId: row.assessId}}).then(res => {
        if (res.code === '200') {
          assessedId = res.data.assessedId
          this.assessResultForm = res.data
          this.rankChangeReason = res.data.rankChangeReason === ""? res.data.rankChangeReason.split(','):[]
          this.result = res.data.conclusion.result
          this.getTarget()
          this.loadInfo(row.assessedId)
        } else {
          this.$message.error("系统错误")
        }
      })

      await this.request.get("/senior/" + assessedId).then(res => {
        if (res.code === '200') {
          this.info = res.data
          // this.form.assessedName = res.data.assessedName;
          // this.form.assessedIdentityCard = res.data.identityCard;
        } else {
          this.$message.error("查看报告失败")
        }
      })
      this.dialogReportVisible = true
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
      this.getReport()
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
    loadInfo(assessedId){
      this.request.get("/senior/" +assessedId ).then(res => {
        if (res.code === '200') {
          this.info = res.data
        } else {
          this.$message.error("系统错误")
        }
      })
    }

},
}
</script>

<style>
.el-table .danger-row {
  background: #FFE5B4;
}
.el-table .warning-row {
  background: oldlace;
}
.el-table .success-row {
  background: #FFCBA4;
}
.success {
  background: #ffffff;
}
.warning {
  background: oldlace;
}
.danger {
  background: #FFE5B4;
}
</style>