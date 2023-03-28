<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"  v-model="email"></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"  v-model="address"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleApply">我要申请 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量撤销 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="我的申请" name="first">
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
              <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
      <el-tab-pane label="已完成" name="second">已申请</el-tab-pane>
<!--      <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>-->
<!--      <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>-->
    </el-tabs>

<!--申请信息-->
    <el-dialog title="申请信息" :visible.sync="dialogApplyVisible" width="30%" >
      <el-form label-width="80px" ref="applyInfo" size="small" :rules="rules" :model="form">
        <el-form-item label="姓名" property="assessedName">
          <el-input v-model="form.assessedName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="身份证号" property="identityCard">
          <el-input v-model="form.identityCard" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item property="assessReason" label="评估原因">
          <el-select v-model="form.assessReason" filterable allow-create placeholder="请选择">
            <el-option
                v-for="item in assessReasonList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogApplyVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

<!--    申请流程-->
    <el-dialog title="进度情况" :visible.sync="dialogProcessVisible" width="30%" >
      <el-timeline style="margin-left: 50px">
        <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :icon="activity.icon"
            :type="activity.type"
            :color="activity.color"
            :size="activity.size"
            :timestamp="activity.timestamp">
          {{activity.content}}
        </el-timeline-item>
      </el-timeline>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogProcessVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {serverIp} from "../../public/config";

export default {
  name: "Apply",
  created() {
    this.load()
  },
  data(){
    return{
      serverIp:serverIp,
      bool:true,
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      username:"",
      nickname:"",
      email:"",
      phone:"",
      address:"",
      form:{},
      dialogApplyVisible:false,
      dialogProcessVisible:false,
      multipleSelection:[],
      headerBg: 'headerBg',
      activities: [{
        content: '支持使用图标',
        timestamp: '2018-04-12 20:46',
        size: 'large',
        type: 'primary',
        icon: 'el-icon-more'
      }, {
        content: '支持自定义颜色',
        timestamp: '2018-04-03 20:46',
        color: '#0bbd87'
      }, {
        content: '支持自定义尺寸',
        timestamp: '2018-04-03 20:46',
        size: 'large'
      }, {
        content: '默认样式的节点',
        timestamp: '2018-04-03 20:46'
      }],
      roles:[],
      rules:{
        assessReason: [{required: true, message: '请选择评估原因', trigger: 'change'},],
      },
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
  methods:{
    load(){
      //请求分页查询数据
      this.request.get("/apply/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          // username:this.username,
          // email:this.email,
          // address:this.address
        }
      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

      // this.request.get("/role").then(res=>{
      //   this.roles = res.data
      // })
    },
    save(){
      this.$refs['applyInfo'].validate((valid) => {
        if (valid) {
          this.request.post("/apply",this.form).then(res=>{
            if(res.code === '200'){
                this.$message.success("申请成功")
                this.load()
                this.dialogApplyVisible = false
            }
            else{
              this.$message.error("申请失败")
            }
          })
        }
      })
    },
    handleApply(){
      let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{}
      if(Object.keys(user).length>0){
        this.request.get("/senior/person/"+user.id).then(res=>{
          if(res.code === '200'){
            if(res.data === null){
              this.$router.push('/infofilling',() => {})
              this.$message.success("请先完成信息填写再进行申请")
            }
            else {
              this.dialogApplyVisible = true
              this.form.applyId = res.data.id
              this.form.assessedName = res.data.assessedName;
              this.form.identityCard = res.data.identityCard;
            }
          }
          else{
            this.$message.error("系统错误")
          }
        })
      }
    },
    getRowKeys(row) {
      return row.id
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    exp(){
      window.open("http://"+serverIp+":9090/user/export")
    },
    //隐藏身份证
    hideNumber(){
      this.bool = ! this.bool;
    },
    handleEdit(row){
      this.form = Object.assign({},row)
      this.dialogApplyVisible = true
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/apply/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量撤销成功")
          this.load()
        } else {
          this.$message.error("批量撤销失败")
        }
      })
    },
    del(id){
      this.request.delete("/apply/"+id).then(res=>{
        if(res.data){
          this.$message.success("撤销成功")
          this.load()
        }
        else{
          this.$message.error("撤销失败")
        }
      })
    },
    reset(){
      this.username=""
      this.email=""
      this.address=""
      this.load()
    },
    lookProcess(row){
      this.dialogProcessVisible = true
    },
  }
}
</script>
<style scoped>

</style>