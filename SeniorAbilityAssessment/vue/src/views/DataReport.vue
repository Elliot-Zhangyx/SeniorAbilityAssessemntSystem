<template>
<div>
  <div style="display: flex">
      <el-card style="flex: 1">
        <div slot="header" class="clearfix">
          <span>年龄统计</span>
        </div>
        <div id="age" style="width: 500px; height: 450px;margin: 0 auto"></div>
      </el-card>
      <el-card style="margin-left: 20px;flex: 1">
        <div slot="header" class="clearfix">
          <span>性别统计</span>
        </div>
        <div id="gender" style="width: 500px; height: 450px;margin: 0 auto"></div>
      </el-card>
  </div>
  <div style="margin-top: 20px;width: 100%" >
      <el-card>
        <div slot="header" class="clearfix">
          <span>等级统计</span>
        </div>
        <div id="rank" style="width: 100%; height: 450px;margin: 0 auto"></div>
      </el-card>
  </div>
</div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: "DataReport",
  data(){
    return{

    }
  },
  mounted() {
    //年龄统计
    var optionAge = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          data:["[20-50)","[50-55)","[55-60)","[60-65)","[65-70)","[70-...)"],
          axisTick: {
            alignWithLabel: true
          }
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: '数量',
          type: 'bar',
          barWidth: '60%',
        }
      ]
    };
    var chartDomAge = document.getElementById('age');
    var myChartAge = echarts.init(chartDomAge);
    this.request.get("/echarts/age").then(res => {
      // 填空
      //option.yAxis.data = res.data.y
      optionAge.series[0].data = res.data
      // option.series[1].data = res.data
      optionAge && myChartAge.setOption(optionAge);
    })


    //性别统计
    var optionGender = {
      title: {
        // text: '性别统计',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      color: ['#EE6666', '#FAC858'],
      series: [
        {
          name: '数量',
          type: 'pie',
          radius: '50%',
          data:[],
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
    var chartDomGender = document.getElementById('gender');
    var myChartGender = echarts.init(chartDomGender);
    this.request.get("/echarts/gender").then(res => {
      console.log(JSON.stringify(res.data))
      // 填空
      optionGender.series[0].data = [
        {name: "男", value: res.data[0]},
        {name: "女", value: res.data[1]},
      ]
      optionGender && myChartGender.setOption(optionGender);
    })





    //等级统计

    var optionRank = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          crossStyle: {
            color: '#999'
          }
        }
      },
      toolbox: {
        feature: {
          dataView: { show: true, readOnly: false },
          magicType: { show: true, type: ['line', 'bar'] },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      legend: {
        data: ['初级等级', '最终等级', '总数']
      },
      xAxis: [
        {
          type: 'category',
          data: ["0级：能力完好","1级：轻度失能）","2级：中度失能","3级：重度失能","4级：完全失能"],
          axisPointer: {
            type: 'shadow'
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '各等级人数',
          // min: 0,
          //  max: 70,
          // interval: 20,
          axisLabel: {
            formatter: '{value}'
          }
        },
        {
          type: 'value',
          name: '总量',
          // min: 0,
          //  max: 70,
          // interval: 20,
          axisLabel: {
            formatter: '{value}'
          }
        }
      ],
      series: [
        {
          name: '初级等级',
          type: 'bar',
          tooltip: {
            valueFormatter: function (value) {
              return value + ' 人';
            }
          },
          data: []
        },
        {
          name: '最终等级',
          type: 'bar',
          tooltip: {
            valueFormatter: function (value) {
              return value+' 人';
            }
          },
          data: []
        },
        {
          name: '总数',
          type: 'line',
          yAxisIndex: 1,
          tooltip: {
            valueFormatter: function (value) {
              return value+' 人';
            }
          },
          data: []
        }
      ]
    };
    var chartDomRank = document.getElementById('rank');
    var myChartRank = echarts.init(chartDomRank);
    this.request.get("/echarts/rank").then(res => {
      // 填空
      console.log(JSON.stringify(res.data.rules))
      optionRank.xAxis = [
        {
          type: 'category',
          data: res.data.rules,
          axisPointer: {
            type: 'shadow'
          }
        }
      ]
      optionRank.series[0].data = res.data.primaryRank
      optionRank.series[1].data = res.data.ultimateRank
      optionRank.series[2].data = res.data.totalRank

      optionRank.yAxis[0].max = Math.max.apply(null, res.data.ultimateRank)+ this.closeInteger(Math.max.apply(null, res.data.ultimateRank))
      optionRank.yAxis[1].max = Math.max.apply(null, res.data.totalRank)+this.closeInteger(Math.max.apply(null, res.data.totalRank))
      optionRank && myChartRank.setOption(optionRank);
    })

  },
  methods:{
    closeInteger(number){
      // var num=number;
      // var weishu = this.weiShu(num)
      // console.log('weishu'+weishu)
      // let chu = ''
      // for(let i =0;i<weishu-1;i++){
      //   chu += '0'
      // }
      // let chuNum = chu === '' ? 1:parseInt('1'+chu)
      let num = 0
      if(number%10!==0){
        // var n=num % chuNum;
        // var floor=Math.floor(num/chuNum);
        // var ceil=Math.ceil(num/chuNum);
        // console.log(floor,ceil)
        //
        // for(var i=0;i<10;i++){
        //   if(10*i<=n && n<10*i+5){
        //     number=floor*chuNum+10*i;
        //   }else {
        //     number=ceil*chuNum+10*i;
        //   }
        num = 10- number%10
        // }
      }
      return num;
    },
    weiShu(n){
      var num = n;
      var count = 0;
      while(num !== 0)
      {
        num =Math.floor(num / 10);
        ++count;
      }
      return count;
    }
  }
}
</script>

<style scoped>

</style>