<template>
  <div>
    <div style="display: flex">
      <el-button type="success" @click="handleAdd">添加模块</el-button>
  <!--    <el-button type="success" @click="parsePdf">PDF导入</el-button>-->
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatchModule"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload :action="'http://'+serverIp+':9090/module/parsePdf'"
                 :show-file-list="false"
                 accept="pdf"
                 :on-success="parsePdf"
                 :on-progress="startProgress"
                 style="display: inline-block">
        <el-button type="primary" class="ml-5" >PDF 导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-progress v-show="pdfPercentageVisible" :text-inside="false"  :percentage="pdfPercentage" :format="format" status="success" style="width: 300px;margin-left: 10px;margin-top: 5px"></el-progress>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange"
              :row-key="getRowKeys"
    >
      <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
      <el-table-column prop="id" label="序号" width="80"></el-table-column>
      <el-table-column prop="title" label="标题" ></el-table-column>
      <el-table-column prop="remark" label="备注" ></el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable>
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span class="ml-10">{{scope.row.createTime ? scope.row.createTime.toString().split("T")[0] : ""}}</span>
        </template>
      </el-table-column>
      <el-table-column label="启用" prop="enable">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <!--                  <el-button type="primary" v-if="scope.row.role==='ROLE_USER'" @click="handleEdit(scope.row)">评估详情<i class="el-icon-edit"></i></el-button>-->
          <el-link type="primary" @click="setModule(scope.row)" :underline="false">编辑</el-link>
          &nbsp;&nbsp;|&nbsp;&nbsp;
          <el-link type="success" @click="setSubject(scope.row.id)" :underline="false">设置</el-link>
          &nbsp;&nbsp;|&nbsp;&nbsp;
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="delModule(scope.row.id)"
          >
            <el-link type="danger" slot="reference" :underline="false">删除</el-link>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding:10px 0">
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

    <el-dialog :visible.sync="dialogModuleVisible" :close-on-click-modal="false" :close-on-press-escape="false" v-dialogDrag>
      <template slot="title">
        <div >
          添加模块
        </div >
      </template>
      <el-form  v-model="module">
        <el-form-item label="名称"  label-width="80px">
          <el-input placeholder="请填写你要创建的模块名称" style="margin-right: 10%" v-model="module.title"></el-input>
        </el-form-item>
        <el-form-item label="备注"  label-width="80px">
          <el-input placeholder="请填写备注"  type="textarea" v-model="module.remark" style="margin-right: 10%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" >
        <el-button @click="dialogModuleVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveModule">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog :visible.sync="dialogSubjectVisible" :close-on-click-modal="false" :close-on-press-escape="false" v-dialogDrag  fullscreen>
      <template slot="title">
        <div>
          添加模块
        </div >
        <br>
        <el-button @click="handleAddOption">添加题目</el-button>
      </template>
      <el-table :data="subjectData.subjectList" border :header-cell-class-name="headerBg">
        <el-table-column  style="padding: 20px 100px" type="expand">
          <template slot-scope="scope">
            <el-form style="margin-left: 50px">
              <el-form-item>
                  <template slot="label">
                    <div style="width: 100%">
                      <b>{{scope.row.subjectTitle}}</b>
                    </div>
                  </template>
                  <el-radio-group style="width: 100%" v-model="scope.row.score">
                    <el-radio  v-for="options in scope.row.optionList" :key="options.score" :label="options.subjectOption" :value="scope.score" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >{{options.subjectOption}}（<el-tag type="success">{{options.score}}分</el-tag>）</el-radio>
                  </el-radio-group>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="题目详情" prop="subjectTitle">
          <template slot-scope="scope">
            {{scope.row.subjectTitle}}&nbsp;<el-tag type="warning" v-if="scope.row.subjectAttention !== '' && scope.row.subjectAttention !== null">{{scope.row.subjectAttention}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
              <el-button type="primary" plain @click="editSubject(scope.row)">编辑</el-button>
              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='确定'
                  cancel-button-text='我再想想'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定删除吗？"
                  @confirm="delSubject(scope.row)"
              >
                <el-button type="danger" plain slot="reference">删除</el-button>
              </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" >
        <el-button @click="dialogSubjectVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveModule">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog  :visible.sync="dialogOptionVisible"  :close-on-click-modal="false" :close-on-press-escape="false" v-dialogDrag>
      <template slot="title">
        题目详情
      </template>
      <Option :moduleId="moduleId" :optionForm="optionForm" @closeOptionEvent="closeOption" @load="loadSubject"></Option>
    </el-dialog>




  </div>
</template>
<script>
import '../utils/directive' // 拖拽弹窗，在需要用到拖拽功能的弹窗标签上加v-dialogDrag
import Option from '@/components/Option'
import {serverIp} from "../../public/config";
export default {
  components:{
    Option
  },
  name: "Module",
  data() {
    return {
      serverIp:serverIp,
      pdfPercentageVisible:false,
      pdfPercentage:0,
      startTimer: '',
      endTimer: '',
      module:{},
      total:0,
      pageNum:1,
      pageSize:10,
      dialogModuleVisible:false,
      dialogSubjectVisible:false,
      dialogOptionVisible:false,
      tableData: [],
      subjectData:[],
      headerBg: 'headerBg',
      subject:{},
      moduleId:0,
      optionForm:{
        optionList: [{
          subjectOption: '',
          score:'',
          rankChange:'0'
        }],
        subjectTitle: '',
        subjectAttention:'',
        moduleId:this.moduleId,
      },
      multipleSelection:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      //请求分页查询数据
      this.request.get("/module/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    saveModule(){
      this.request.post("/module",this.module).then(res=>{
        if(res.data){
          this.$message.success("保存成功")
          this.dialogModuleVisible = false
          this.load()
        }
        else{
          this.$message.error("保存失败")
        }
      })
    },
    closeOption(childValue){
      this.dialogOptionVisible=childValue
    },
    getRowKeys(row) {
      return row.id
    },
    handleSelectionChange(val) {
      console.log(val)

      this.multipleSelection = val
    },
    handleAdd(){
      this.module={}
      this.dialogModuleVisible = true
    },
    handleAddOption(){
      this.optionForm={
        optionList: [{
          subjectOption: '',
          score:'',
          rankChange: '0'
        }],
        subjectTitle: '',
        subjectAttention:'',
        moduleId:this.moduleId,
      }
      this.dialogOptionVisible = true
    },
    setSubject(id){
      this.moduleId = id
      this.dialogSubjectVisible = true

      //请求分页查询数据
      this.request.get("/module/subject/"+id).then(res=>{
        this.subjectData = res.data
      })
    },
    setModule(row){
      this.module = Object.assign({},row)
      this.dialogModuleVisible = true
    },
    loadSubject(){
      this.request.get("/module/subject/"+this.moduleId).then(res=>{
        this.subjectData = res.data
        //this.total = res.data.total
      })
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
    changeEnable(row) {
      this.request.post("/module/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
      })
    },
    editSubject(item){
      this.optionForm = Object.assign({},item)
      this.dialogOptionVisible = true
    },
    delSubject(item){
      this.request.delete("/subject/"+item.subjectUid).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.loadSubject()
        }
        else{
          this.$message.error("删除失败")
        }
      })
    },
    delModule(id){
      this.request.delete("/module/"+id).then(res=>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }
        else{
          this.$message.error("删除失败")
        }
      })
    },
    delBatchModule(){
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/module/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    parsePdf(){
      this.load()
      this.endProgress()
      this.finishProgress()
      var _this = this;
      setTimeout(function(){
        _this.$message.success("解析成功")
      },500)
      setTimeout(function(){
        _this.pdfPercentageVisible = false
      },3000)
    },
    format(percentage) {
      return percentage === 100 ? '满' : `${percentage}%`;
    },
    startProgress () {
      this.pdfPercentageVisible = true
      this.pdfPercentage = 0
      var _this = this;
      setTimeout(function(){
        _this.startTimer = setInterval(() => {
          _this.pdfPercentage ++
          if (_this.pdfPercentage > 85) {
            clearInterval(_this.startTimer)
          }
        }, 100);
      },1000)
    },
    endProgress () {
      clearInterval(this.startTimer)
      this.endTimer = setInterval(() => {
        this.pdfPercentage ++
        if (this.pdfPercentage > 99) {
          clearInterval(this.endTimer)
          this.finishProgress()
        }
      }, 10);
    },
    finishProgress (){
      this.pdfPercentage = 100
    }

  }
}
</script>

<style>
.headerBg{
  background: #eee!important;
}
</style>