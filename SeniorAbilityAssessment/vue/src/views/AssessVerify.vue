<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane>
        <span slot="label"><i class="el-icon-date"></i>申请评估</span>
        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                  @selection-change="handleSelectionChange"
                  :row-key="getRowKeys"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
        >
          <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
          <el-table-column prop="id" label="序号" width="50"></el-table-column>
          <el-table-column prop="applyDate" label="申请日期" width="140">
            <template slot-scope="scope">
              {{scope.row.applyDate.toString().replace("T",' ')}}
            </template>
          </el-table-column>
          <el-table-column prop="assessedName" label="姓名" width="140"></el-table-column>
          <el-table-column prop="identityCard" label="身份证号" width="200">
            <template slot-scope="scope">
              {{bool?scope.row.identityCard.replace(/^(.{6})(?:\w+)(.{4})$/, "\$1********\$2"):scope.row.identityCard}}
              <i class="el-icon-view" @click="hideNumber"></i>
            </template>
          </el-table-column>
          <el-table-column prop="assessReason" label="申请评估原因">
            <template slot-scope="scope">
              <div v-for="item in assessReasonList">
                <span v-if="scope.row.assessReason === item.value">{{item.label}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="applyStatus" label="当前状态">
            <template slot-scope="scope">
              <div v-for="item in statusList">
                <el-tag v-if="scope.row.applyStatus === item.value">{{item.label}}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作"  width="300" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="lookProcess(scope.row)">查看进展<i class="el-icon-edit"></i></el-button>
              <el-button type="success" @click="assess(scope.row)">评估<i class="el-icon-edit"></i></el-button>
              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='确定'
                  cancel-button-text='我再想想'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定删除吗？"
                  @confirm="del(scope.row.id)"
              >
                <el-button type="danger" slot="reference">撤销 <i class="el-icon-remove-outline"></i></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="消息中心">消息中心</el-tab-pane>
      <el-tab-pane label="角色管理">角色管理</el-tab-pane>
      <el-tab-pane label="定时任务补偿">定时任务补偿</el-tab-pane>

      <el-dialog title="基本信息" :visible.sync="dialogProcessVisible" width="70%" >
        <SeniorApply :isEdit="1" :form="senior"></SeniorApply>
        <div style="margin: 20px auto;text-align: center">
          <el-button type="primary" style="margin:0 auto;width: 150px;height: 40px" @click="beginAssess"><span style="font-size: 15px">开始评估</span></el-button>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogProcessVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </el-tabs>
  </div>
</template>

<script>
import SeniorApply from "@/components/SeniorApply";
import {serverIp} from "../../public/config";

export default {
  components:{
    SeniorApply
  },
  name: "AssessVerify",
  data(){
    return{
      serverIp:serverIp,
      bool:true,
      assessInfoList:[],
      tableData:[],
      senior:{},
      dialogProcessVisible:false,
      assessReasonList:[
        {id:'1',label:'首次评估',value:'1'},
        {id:'2',label:'常规评估',value:'2'},
        {id:'3',label:'即时评估',value:'3'},
        {id:'4',label:'因对评估结果有疑问进行的复评 ',value: '4'},
        {id:'5',label:'其他 ',value: '5'},
      ],
      statusList:[
        {id:'0',label:'暂未审核',value:0},
        {id:'1',label:'评估中',value:1},
        {id:'2',label:'已完成',value:2},
        {id:'3',label:'即时评估',value:3},
      ]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/apply").then(res => {
        if (res.code === '200') {
          this.assessInfoList = res.data
          this.tableData = res.data
        } else {
          this.$message.error("系统错误")
        }
      })
    },
    getRowKeys(row) {
      return row.id
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    assess(row){
      this.request.get("/senior/"+row.applyId).then(res => {
        if (res.code === '200') {
          this.senior = res.data
          this.dialogProcessVisible = true
        } else {
          this.$message.error("系统错误")
        }
      })
    },
    beginAssess(){
      let assess = this.$router.resolve({
        name:'AbilityAssess',
        params: {
          assessedId:this.senior.id
        }
      })
      console.log(assess.href)
      window.open(assess.href, '_blank');
    },
    filterUltimate(value, row) {
      return row.ultimateRank === value;
    },
    filterPrimary(value,row){
      return row.primaryRank === value;
    },
    exp(){
      window.open("http://"+serverIp+":9090/user/export")
    },
    //隐藏身份证
    hideNumber(){
      this.bool = ! this.bool;
    },
  }
}
</script>

<style scoped>

</style>