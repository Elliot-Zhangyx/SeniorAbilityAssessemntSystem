<template>
    <div>
      <el-row :gutter="10" style="margin-bottom: 60px">
        <el-col :span="6">
          <el-card style="color:#409EFF;">
            <div><i class="el-icon-user-solid" /> 用户总数</div>
            <div style="padding: 10px 0;text-align: center;font-weight: bold">{{userNum}}</div>
          </el-card>
        </el-col>
        <el-col :span="6" >
          <el-card style="color: #F56C6C">
            <div><i class="el-icon-money"/> 评估总量</div>
            <div style="padding: 10px 0;text-align: center;font-weight: bold">{{assessNum}}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #67C23A">
            <div><i class="el-icon-bank-card"/> 收益总额</div>
            <div style="padding: 10px 0;text-align: center;font-weight: bold">Y 30000</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #E6A23A">
            <div><i class="el-icon-s-shop"/> 门店总数</div>
            <div style="padding: 10px 0;text-align: center;font-weight: bold">20</div>
          </el-card>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12"><div id="main" style="width: 500px; height: 450px"></div></el-col>
        <el-col :span="12"><div id="pie" style="width: 500px; height: 450px"></div></el-col>
      </el-row>
    </div>
</template>

<script>
    import * as echarts from 'echarts'
    export default {
        name: "home",
        data(){
          return{
            assessNum:'',
            userNum:'',
          }
        },
        mounted() {//页面渲染之后在触发
          var option = {
            title: {
              text: '各季度会员数量统计',
              subtext: '趋势图',
              left: 'center'
            },
            tooltip:{
              trigger: 'item'
            },
            legend: {
              orient: 'vertical',
              left: 'left'
            },
            xAxis: {
              type: 'category',
              data: ["第一季度", "第二季度", "第三季度", "第四季度"]
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                name:"123",
                data: [],
                type: 'line'
              },
              {
                name:"456",
                data: [],
                type: 'bar'
              }
            ]
          };

          var chartDom = document.getElementById('main');
          var myChart = echarts.init(chartDom);
          this.request.get("/echarts/members").then(res => {
            // 填空
            // option.xAxis.data = res.data.x
            option.series[0].data = res.data
            option.series[1].data = res.data
            // 数据准备完毕之后再set
            myChart.setOption(option);

          })

          // 饼图

          var pieOption = {
            title: {
              text: '各季度会员数量统计',
              subtext: '比例图',
              left: 'center'
            },
            tooltip: {
              trigger: 'item'
            },
            legend: {
              orient: 'vertical',
              left: 'left'
            },
            series: [
              {
                type: 'pie',
                radius: '60%',
                label:{            //饼图图形上的文本标签
                  normal:{
                    show:true,
                    position:'inner', //标签的位置
                    textStyle : {
                      fontWeight : 300 ,
                      fontSize : 14,    //文字的字体大小
                      color: "#fff"
                    },
                    formatter:'{d}%'
                  }
                },
                data: [],  // 填空
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };

          var pieDom = document.getElementById('pie');
          var pieChart = echarts.init(pieDom);

          this.request.get("/echarts/members").then(res => {

            pieOption.series[0].data = [
              {name: "第一季度", value: res.data[0]},
              {name: "第二季度", value: res.data[1]},
              {name: "第三季度", value: res.data[2]},
              {name: "第四季度", value: res.data[3]},
            ]
            pieChart.setOption(pieOption)
          })
      },
      method:{

      },
      created() {
        this.request.get("/senior").then(res => {
            if(res.code === '200'){
              this.assessNum = res.data.length
            }
            else{
              this.$message.error('系统错误')
            }
        })

        this.request.get("/user").then(res => {
          if(res.code === '200'){
            this.userNum = res.data.length
          }
          else{
            this.$message.error('系统错误')
          }
        })
      }
    }
</script>

<style scoped>

</style>