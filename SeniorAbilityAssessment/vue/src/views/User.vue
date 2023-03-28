<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"  v-model="email"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"  v-model="address"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
          <el-button class="ml-5" type="warning" @click="wocao">wocao</el-button>
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
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
            <el-upload :action="'http://'+serverIp+':9090/user/import'"
                       :show-file-list="false"
                       accept="xlsx"
                       :on-success="handleExcelImportSuccess"
                       style="display: inline-block">
                <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                  @selection-change="handleSelectionChange"
                  :row-key="getRowKeys"
        >
            <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="140"></el-table-column>
            <el-table-column prop="role" label="角色" sortable
                             :filters="[ {text: '评估员', value: 'ROLE_ASSESSOR'},{text: '管理员', value: 'ROLE_ADMIN'}, {text: '普通用户', value: 'ROLE_USER'}]"
                             :filter-method="filterHandler"
            >
              <template slot-scope="scope">
                <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
                <el-tag type="warning" v-if="scope.row.role === 'ROLE_USER'">普通用户</el-tag>
                <el-tag type="success" v-if="scope.row.role === 'ROLE_ASSESSOR'">评估员</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template slot-scope="scope">
<!--                  <el-button type="primary" v-if="scope.row.role==='ROLE_USER'" @click="handleEdit(scope.row)">评估详情<i class="el-icon-edit"></i></el-button>-->
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
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
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

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
            <el-form label-width="80px" size="small">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                  <el-select clearable v-model="form.role" autocomplete="off" placeholder="请选择角色" style="width:100%">
                    <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="form.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {serverIp} from "../../public/config";

    export default {
        name: "User",
        data(){
            return{
              serverIp:serverIp,
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
                dialogFormVisible:false,
                multipleSelection:[],
                headerBg: 'headerBg',
                roles:[]
            }
        },
        created() {
            this.load()
        },
        methods:{
            load(){
                //请求分页查询数据
                this.request.get("/user/page",{
                    params:{
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        username:this.username,
                        email:this.email,
                        address:this.address
                    }
                }).then(res=>{
                    console.log(res)
                    this.tableData = res.data.records
                    this.total = res.data.total
                })

                this.request.get("/role").then(res=>{
                    this.roles = res.data
                })
            },
            handleAdd(){
                this.dialogFormVisible = true
                this.form = {}
            },
            handleEdit(row){
                this.form = Object.assign({},row)
                this.dialogFormVisible = true
            },
            del(id){
                this.request.delete("/user/"+id).then(res=>{
                    if(res.data){
                        this.$message.success("删除成功")
                        this.load()
                    }
                    else{
                        this.$message.error("删除失败")
                    }
                })
            },
            save(){
                this.request.post("/user",this.form).then(res=>{
                    if(res.data){
                        this.$message.success("保存成功")
                        this.dialogFormVisible = false
                        this.load()
                    }
                    else{
                        this.$message.error("保存失败")
                    }
                })
            },
            delBatch(){
                let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
                this.request.post("/user/del/batch", ids).then(res => {
                    if (res.data) {
                        this.$message.success("批量删除成功")
                        this.load()
                    } else {
                        this.$message.error("批量删除失败")
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
            reset(){
                this.username=""
                this.email=""
                this.address=""
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
                window.open("http://"+this.serverIp+":9090/user/export")
            },
            handleExcelImportSuccess() {
                this.$message.success("导入成功")
                this.load()
            },
            filterHandler(value, row, column) {
              const property = column['property'];
              return row[property] === value;
            },
            wocao(){
              this.request.get("/user/updateAvatar",{
                params:{
                  id:15,
                  avatarUrl:"mmm",
                }
              }).then(res=>{
                console.log("zheshienm"+res)
              })
            }
        }
    }
</script>

<style>
    .headerBg {
        background: #eee!important;
    }
</style>