<template>
  <div class="wrap">
    <template>
      <el-backtop target=".wrap">
        <i class="el-icon-caret-top"></i>
      </el-backtop>
    </template>
    <div style="width: 100%;margin: 0 auto;">
      <el-steps  :active="active" simple>
        <el-step title="信息填写" @click.native="active = 0" icon="el-icon-edit" ></el-step>
        <el-step title="能力评估" @click.native="active = 1" icon="el-icon-s-promotion"></el-step>
        <el-step title="生成报告" icon="el-icon-s-claim"></el-step>
      </el-steps>
    </div>

<!--    <el-button style="margin-top: 12px;" @click="next">下一步</el-button>-->


<!--    <el-radio-group v-model="tabPosition" style="margin-bottom: 30px;float: right">-->
<!--      <el-radio-button label="top">top</el-radio-button>-->
<!--      <el-radio-button label="right">right</el-radio-button>-->
<!--      <el-radio-button label="bottom">bottom</el-radio-button>-->
<!--      <el-radio-button label="left">left</el-radio-button>-->
<!--    </el-radio-group>-->
    <el-card style="width: 100%;position: relative;margin: 20px auto" shadow="never">
      <div slot="header" class="clearfix">
        <span style="font-size: 12px">姓名：<el-tag type="success">{{form.assessedName}}</el-tag>&emsp;&emsp;&emsp;身份证号：<el-tag type="success">{{form.assessedIdentityCard}}</el-tag></span>
        <el-button @click="beforePage(beforeFlag)" style="float: right;" size="medium" type="primary" v-show="showBefore">上一步&nbsp;<i class="el-icon-arrow-up"></i></el-button>
<!--        <el-button @click="beforePage2()" v-if="beforeFlag !== 0" style="float: right;" size="medium" type="primary" v-show="showBefore">上一步&nbsp;<i class="el-icon-arrow-up"></i></el-button>-->
      </div>
<!--      <div style="margin-left: 20px" v-show="false">-->
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
<!--&lt;!&ndash;          <el-button @click="nextTabs" style="float: right;" size="medium" type="primary">下一步<i class="el-icon-arrow-down"></i></el-button>&ndash;&gt;-->
<!--          <br>-->
<!--          <br>-->
<!--        </el-form>-->
<!--      </div>-->
      <div style="height: 100%;width: 100%;">
        <el-select v-model="form.assessReason" placeholder="请选择">
          <el-option
              v-for="item in assessReasonList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
        <el-steps :active="activeStep" finish-status="success">
          <el-step @click.native="activeStep = 0;dailyActive = true;dailyStatus = 'process'" :status="dailyStatus">
            <template slot="title">
              <span style="cursor: pointer;">日常生活活动评估</span>
            </template>
          </el-step>
          <el-step title="精神状态评估"  @click.native="activeStep = 1;spiritStatus = 'process'" :status="spiritStatus">
            <template slot="title">
              <span style="cursor: pointer;">精神状态评估</span>
            </template>
          </el-step>
          <el-step title="感知觉与沟通评估"  @click.native="activeStep = 2;sensoryStatus = 'process'" :status="sensoryStatus">
            <template slot="title">
              <span style="cursor: pointer;">感知觉与沟通评估</span>
            </template>
          </el-step>
          <el-step title="社会参与评估"  @click.native="activeStep = 3;socialAttendStatus = 'process'" :status="socialAttendStatus">
            <template slot="title">
              <span style="cursor: pointer;">社会参与评估</span>
            </template>
          </el-step>
        </el-steps>
        <div v-show="dailyActive">
          <el-form style="padding: 20px 20px" ref="seniorForm" :rules="rules" :model="daily">
            <el-table border style="width: 100%" :data="dailyLifeList">
              <el-table-column label="测试题目" width="1000">
                  <template slot-scope="scope">
                    <el-form-item :label="scope.row.label" style="width: 100%;padding: 0 10px">
                      <el-radio-group v-model="scope.row.score" style="width: 100%" @change="dailyLifeChange(scope.row.id,scope.row.score)">
                        <el-radio  v-for="item in scope.row.selectList" :key="item.value" :label="item.value" :value="item.value" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >{{item.label}}</el-radio>
                      </el-radio-group>
                    </el-form-item>
                  </template>
              </el-table-column>
              <el-table-column label="分数" style="width: 20%;"  align="center">
                <template slot-scope="scope">
                  {{scope.row.score}}
                </template>
              </el-table-column>
<!--              <el-table-column>-->
<!--                <template>-->
<!--                  <el-form-item style="width: 100%;padding: 0 10px">-->
<!--                    <template slot="label">-->
<!--                      <b>B.4.6  社会参与总分 :</b>-->
<!--                    </template>-->
<!--                    <span style="font-size: 15px;color: red">{{form.socialAttend.socialAttendTotal}}</span>&nbsp;分-->
<!--                  </el-form-item>-->
<!--                </template>-->
<!--              </el-table-column>-->
            </el-table>
            <div style="display: flex;margin-top: 20px">
              <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                <template slot="label">
                  <b>B.1.11  日常生活活动总分：</b>
                </template>
                <span style="font-size: 15px;color: red">{{daily.dailyLife}}</span>&nbsp;分
              </el-form-item>
              <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                <template slot="label">
                  <b>B.1 感知觉与沟通分级：</b>
                </template>
                <el-popover   placement="top-start" title="分级详情：" width="300" trigger="hover">
                  <div style="padding: 10px 20px;">
                    <span>&emsp;0级-能力完好：总分 100 分</span>
                    <hr>
                    <span>&emsp;1级-轻度受损：总分 65-95 分</span>
                    <hr>
                    <span>&emsp;2级-中度受损：总分 45-60 分</span>
                    <hr>
                    <span>&emsp;3级-重度受损：总分≤40 分</span>
                  </div>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium" type="success" v-if="daily.dailyRank === 0">{{daily.dailyRank}}级</el-tag>
                    <el-tag size="medium" type="warning" v-if="daily.dailyRank === 1">{{daily.dailyRank}}级</el-tag>
                    <el-tag size="medium" type="warning" v-if="daily.dailyRank === 2">{{daily.dailyRank}}级</el-tag>
                    <el-tag size="medium" type="danger" v-if="daily.dailyRank === 3">{{daily.dailyRank}}级</el-tag>
                  </div>
                </el-popover>
              </el-form-item>
            </div>
            <el-button @click="nextStep('spirit')" style="float: right;" size="medium" type="primary">下一步&nbsp;<i class="el-icon-arrow-down"></i></el-button>
          </el-form>
        </div>
        <div v-show="spiritActive">
          <el-form style="padding: 20px 20px" ref="seniorForm" :rules="rules" :model="spirit">
            <div>
              <el-form-item style="width: 100%;padding: 0 10px">
                <template slot="label">
                  <b>B.2.1 认知功能</b>
                </template>
                <br>
                <el-card shadow="hover" style="background: #cceac7;border: #f3fff1 solid 2px;padding: 0 20px;width: 80%;margin-bottom:10px;display: block;">
                    <span style="margin-bottom: 10px"><b>请进行以下测试:</b></span>
                    <p>“我说三样东西，请重复一遍，并记住，一会儿会问您”：苹果、手表、国旗</p>
                    <p>(1)画钟测验：“请您在这儿画一个圆形的时钟，在时钟上标出 10 点 45 分”</p>
                    <p>(2)回忆词语：“现在请您告诉我，刚才我要您记住的三样东西是什么？”答：_______、________、________（不必按顺序）</p>
                </el-card>
                <el-radio-group v-model="spirit.recognition" style="width: 100%" @change="spiritStatusChange">
                  <el-radio  name="attack" label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，画钟正确（画出一个闭锁圆，指针位置准确），且能回忆出 2-3 个词</el-radio>
                  <el-radio name="attack" label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，画钟错误（画的圆不闭锁，或指针位置不准确），或只回忆出 0-1 个词</el-radio>
                  <el-radio  name="attack" label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，已确诊为认知障碍，如老年痴呆</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
            <el-form-item style="width: 100%;padding: 0 10px">
              <template slot="label">
                <b>B.2.2 攻击行为</b>
              </template>
              <el-radio-group v-model="spirit.attack" style="width: 100%" @change="spiritStatusChange()">
                <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，无身体攻击行为（如打/踢/推/咬/抓/摔东西）和语言攻击行为（如骂人、语言威胁、尖叫）</el-radio>
                <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，每月有几次身体攻击行为，或每周有几次语言攻击行为</el-radio>
                <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，每周有几次身体攻击行为，或每日有语言攻击行为</el-radio>
              </el-radio-group>
            </el-form-item>
            <div>
              <el-form-item style="width: 100%;padding: 0 10px">
                <template slot="label">
                  <b>B.2.3 抑郁症状</b>
                </template>
                <el-radio-group v-model="spirit.depress" style="width: 100%" @change="spiritStatusChange">
                  <el-radio value="0" label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，无</el-radio>
                  <el-radio value="1" label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，情绪低落、不爱说话、不爱梳洗、不爱活动</el-radio>
                  <el-radio value="2" label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，有自杀念头或自杀行为</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
            <div style="display: flex;margin-top: 20px">
              <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                <template slot="label">
                  <b>B.2.4 精神状态总分：</b>
                </template>
                <span style="font-size: 15px;color: red">{{spirit.spiritTotal}}</span>&nbsp;分
              </el-form-item>
              <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                <template slot="label">
                  <b>B.2 精神状态分级：</b>
                </template>
                <el-popover   placement="top-start" title="分级详情：" width="300" trigger="hover">
                  <div style="padding: 10px 20px;">
                    <span>&emsp;0级-能力完好：总分 0 分</span>
                    <hr>
                    <span>&emsp;1级-轻度受损：总分 1 分</span>
                    <hr>
                    <span>&emsp;2级-中度受损：总分 2-3 分</span>
                    <hr>
                    <span>&emsp;3级-重度受损：总分 4-6 分</span>
                  </div>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium" type="success" v-if="spirit.spiritRank === 0">{{spirit.spiritRank}}级</el-tag>
                    <el-tag size="medium" type="warning" v-if="spirit.spiritRank === 1">{{spirit.spiritRank}}级</el-tag>
                    <el-tag size="medium" type="warning" v-if="spirit.spiritRank === 2">{{spirit.spiritRank}}级</el-tag>
                    <el-tag size="medium" type="danger" v-if="spirit.spiritRank === 3">{{spirit.spiritRank}}级</el-tag>
                  </div>
                </el-popover>
              </el-form-item>
            </div>
          </el-form>
          <el-button @click="nextStep('sensory')" style="float: right;" size="medium" type="primary">下一步&nbsp;<i class="el-icon-arrow-down"></i></el-button>
        </div>
        <div v-show="sensoryActive">
          <el-form style="padding: 20px 20px" ref="seniorForm" :rules="rules" :model="sensory">
            <el-form-item style="width: 100%;padding: 0 10px">
              <template slot="label">
                <b>B.3.1 意识水平</b>
              </template>
              <el-radio-group v-model="sensory.conscious" style="width: 100%" @change="sensoryChange()">
                <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，神志清醒，对周围环境警觉</el-radio>
                <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，嗜睡，表现为睡眠状态过度延长。当呼唤或推动其肢体时可唤醒，并能进行正确的交谈或执行指令，停止刺激后又继续入睡</el-radio>
                <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，昏睡，一般的外界刺激不能使其觉醒，给予较强烈的刺激时可有短时的意识清醒，醒后可简短回答提问，当刺激减弱后又很快进入睡眠状态</el-radio>
                <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，昏迷，处于浅昏迷时对疼痛刺激有回避和痛苦表情；处于深昏迷时对刺激无反应（若评定为昏迷，直接评定为重度失能，可不进行以下项目的评估）</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-divider></el-divider>
            <el-form-item style="width: 100%;padding: 0 10px;">
              <template slot="label">
                <b>B.3.2 视力：若平日带老花镜或近视镜，应在佩戴眼镜的情况下评估</b>
              </template>
              <el-radio-group v-model="sensory.sight" style="width: 100%" @change="sensoryChange()">
                <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，能看清书报上的标准字体</el-radio>
                <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，能看清楚大字体，但看不清书报上的标准字体</el-radio>
                <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，视力有限，看不清报纸大标题，但能辨认物体</el-radio>
                <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，辨认物体有困难，但眼睛能跟随物体移动，只能看到光、颜色和形状</el-radio>
                <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，没有视力，眼睛不能跟随物体移动</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-divider></el-divider>
            <el-form-item style="width: 100%;padding: 0 10px">
              <template slot="label">
                <b>B.3.3  听力：若平时佩戴助听器，应在佩戴助听器的情况下评估</b>
              </template>
              <el-radio-group v-model="sensory.listening" style="width: 100%" @change="sensoryChange()">
                <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，可正常交谈，能听到电视、电话、门铃的声音</el-radio>
                <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，在轻声说话或说话距离超过 2 米时听不清</el-radio>
                <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，正常交流有些困难，需在安静的环静或大声说话才能听到</el-radio>
                <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，讲话者大声说话或说话很慢，才能部分听见</el-radio>
                <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，完全听不见</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-divider></el-divider>
            <div style="margin:0 auto" class="shadow">
              <el-form-item style="width: 100%;padding: 0 10px">
                <template slot="label">
                  <b>B.3.4  沟通交流:包括非语言沟通若平时佩戴助听器，应在佩戴助听器的情况下评估</b>
                </template>
                <el-radio-group v-model="sensory.communicate" style="width: 100%" @change="sensoryChange()">
                  <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，无困难，能与他人正常沟通和交流</el-radio>
                  <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，能够表达自己的需要及理解别人的话，但需要增加时间或给予帮助</el-radio>
                  <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，表达需要或理解有困难，需频繁重复或简化口头表达</el-radio>
                  <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，不能表达需要或理解他人的话</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>


            <div style="margin:0 auto">
              <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                <template slot="label">
                  <b>B.3 感知觉与沟通分级：</b>
                </template>
                <el-popover   placement="top-start" title="分级详情：" width="700" trigger="hover">
                  <div style="padding: 10px 20px;">
                    <span>&emsp;0级-能力完好：意识清醒，且视力和听力评为 0 或 1，沟通评为 0</span>
                    <hr>
                    <span>&emsp;1级-轻度受损：意识清醒，但视力或听力中至少一项评为 2，或沟通评为 1</span>
                    <hr>
                    <span>&emsp;2级-中度受损：意识清醒，但视力或听力中至少一项评为 3，或沟通评为 2；或嗜睡，视力或听力<br>
                      &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;评定为 3 及以下，沟通评定为 2 及以下</span>
                    <hr>
                    <span>&emsp;3级-重度受损：意识清醒或嗜睡，但视力或听力中至少一项评为 4，或沟通评为 3；或昏睡/昏迷</span>
                  </div>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium" type="success" v-if="sensory.sensoryRank === 0">{{sensory.sensoryRank}}级</el-tag>
                    <el-tag size="medium" type="warning" v-if="sensory.sensoryRank === 1">{{sensory.sensoryRank}}级</el-tag>
                    <el-tag size="medium" type="warning" v-if="sensory.sensoryRank === 2">{{sensory.sensoryRank}}级</el-tag>
                    <el-tag size="medium" type="danger" v-if="sensory.sensoryRank === 3">{{sensory.sensoryRank}}级</el-tag>
                  </div>
                </el-popover>
              </el-form-item>
            </div>
          </el-form>
          <el-button @click="nextStep('socialAttend')" style="float: right;" size="medium" type="primary">下一步&nbsp;<i class="el-icon-arrow-down"></i></el-button>
        </div>
        <div v-show="socialAttendActive">
            <el-form style="padding: 20px 20px" ref="seniorForm" :rules="rules" :model="socialAttend">
              <el-descriptions title="社会参与评估"></el-descriptions>
                <div style="border: #8492a6 solid 1px">
                  <el-form-item style="width: 100%;padding: 0 10px">
                    <template slot="label">
                      <b>B.4.1  生活能力</b>
                    </template>
                    <el-radio-group v-model="socialAttend.lifeAbility" style="width: 100%" @change="socialAttendChange()">
                      <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，除个人生活自理外（如饮食、洗漱、穿戴、二便），能料理家务（如做饭、洗衣）或当家管理事务</el-radio>
                      <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，除个人生活自理外，能做家务，但欠好，家庭事务安排欠条理</el-radio>
                      <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，个人生活能自理；只有在他人帮助下才能做些家务，但质量不好</el-radio>
                      <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，个人基本生活事务能自理（如饮食、二便），在督促下可洗漱</el-radio>
                      <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，个人基本生活事务（如饮食、二便）需要部分帮助或完全依赖他人帮助</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </div>
                <el-form-item style="width: 100%;padding: 0 10px">
                  <template slot="label">
                    <b>B.4.2  工作能力</b>
                  </template>
                  <el-radio-group v-model="socialAttend.workAbility" style="width: 100%" @change="socialAttendChange()">
                    <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，原来熟练的脑力工作或体力技巧性工作可照常进行</el-radio>
                    <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，原来熟练的脑力工作或体力技巧性工作能力有所下降</el-radio>
                    <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，原来熟练的脑力工作或体力技巧性工作明显不如以往，部分遗忘</el-radio>
                    <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，对熟练工作只有一些片段保留，技能全部遗忘</el-radio>
                    <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，对以往的知识或技能全部磨灭</el-radio>
                  </el-radio-group>
                </el-form-item>

                <el-form-item style="width: 100%;padding: 0 10px">
                  <template slot="label">
                    <b>B.4.3  时间/空间定向</b>
                  </template>
                  <el-radio-group v-model="socialAttend.timeSpace" style="width: 100%" @change="socialAttendChange()">
                    <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，时间观念（年、月、日、时）清楚；可单独出远门，能很快掌握新环境的方位</el-radio>
                    <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，时间观念有些下降，年、月、日清楚，但有时相差几天；可单独来往于近街，知道现住地的名称和方位，但不知回家路线</el-radio>
                    <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，时间观念较差，年、月、日不清楚，可知上半年或下半年；只能单独在家附近行动，对现住地只知名称，不知道方位</el-radio>
                    <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，时间观念很差，年、月、日不清楚，可知上午或下午；只能在左邻右舍间串门，对现住地不知名称和方位</el-radio>
                    <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，无时间观念；不能单独外出</el-radio>
                  </el-radio-group>
                </el-form-item>

                <el-form-item style="width: 100%;padding: 0 10px">
                  <template slot="label">
                    <b>B.4.4  人物定向</b>
                  </template>
                  <el-radio-group v-model="socialAttend.personOrientation" style="width: 100%" @change="socialAttendChange()">
                    <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，知道周围人们的关系，知道祖孙、叔伯、姑姨、侄子侄女等称谓的意义；可分辨陌生人的大致年龄和身份，可用适当称呼</el-radio>
                    <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，只知家中亲密近亲的关系，不会分辨陌生人的大致年龄，不能称呼陌生人</el-radio>
                    <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，只能称呼家中人，或只能照样称呼，不知其关系，不辨辈分</el-radio>
                    <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，只认识常同住的亲人，可称呼子女或孙子女，可辨熟人和生人</el-radio>
                    <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，只认识保护人，不辨熟人和生人</el-radio>
                  </el-radio-group>
                </el-form-item>

                <el-form-item style="width: 100%;padding: 0 10px">
                  <template slot="label">
                    <b>B.4.5  社会交往能力</b>
                  </template>
                  <el-radio-group v-model="socialAttend.socialCommunication" style="width: 100%" @change="socialAttendChange()">
                    <el-radio  label="0" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >0 分，参与社会，在社会环境有一定的适应能力，待人接物恰当</el-radio>
                    <el-radio  label="1" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >1 分，能适应单纯环境，主动接触人，初见面时难让人发现智力问题，不能理解隐喻语</el-radio>
                    <el-radio  label="2" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >2 分，脱离社会，可被动接触，不会主动待人，谈话中很多不适词句，容易上当受骗</el-radio>
                    <el-radio  label="3" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >3 分，勉强可与人交往，谈吐内容不清楚，表情不恰当</el-radio>
                    <el-radio  label="4" class="ml-5" style="width: 100%; display: block;white-space: normal;margin-left: 0;line-height: 30px" >4 分，难以与人接触</el-radio>
                  </el-radio-group>
                </el-form-item>
              <div style="display: flex;margin-top: 20px">
                <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                  <template slot="label">
                    <b>B.4.6  社会参与总分：</b>
                  </template>
                  <span style="font-size: 15px;color: red">{{socialAttend.socialAttendTotal}}</span>&nbsp;分
                </el-form-item>
                <el-form-item style="width: 100%;padding: 0 10px;flex: 1">
                  <template slot="label">
                    <b>B.4.7  社会参与分级：</b>
                  </template>
                  <el-popover   placement="top-start" title="分级详情：" width="300" trigger="hover">
                    <div style="padding: 10px 20px;">
                      <span>&emsp;0级-能力完好：总分 0-2 分</span>
                      <hr>
                      <span>&emsp;1级-轻度受损：总分 3-7 分</span>
                      <hr>
                      <span>&emsp;2级-中度受损：总分 8-13 分</span>
                      <hr>
                      <span>&emsp;3级-重度受损：总分 14-20 分</span>
                    </div>
                    <div slot="reference" class="name-wrapper">
                      <el-tag size="medium" type="success" v-if="socialAttend.socialAttendRank === 0">{{socialAttend.socialAttendRank}}级</el-tag>
                      <el-tag size="medium" type="warning" v-if="socialAttend.socialAttendRank === 1">{{socialAttend.socialAttendRank}}级</el-tag>
                      <el-tag size="medium" type="warning" v-if="socialAttend.socialAttendRank === 2">{{socialAttend.socialAttendRank}}级</el-tag>
                      <el-tag size="medium" type="danger" v-if="socialAttend.socialAttendRank === 3">{{socialAttend.socialAttendRank}}级</el-tag>
                    </div>
                  </el-popover>
                </el-form-item>
              </div>
            </el-form>
            <div style="margin-left: 40%;float: right">
              <el-button @click="nextResult()" style="float: right;" size="medium" type="primary">下一步&nbsp;<i class="el-icon-arrow-down"></i></el-button>
            </div>
        </div>
      </div>
      <div v-show="showResult">
        <el-timeline style="margin: 5% 10%">
          <el-timeline-item type="primary">
            <h4>综合评估初步等级判断</h4>
            <br>
            <el-card>
              <el-tag :type="form.primaryRank === item.id?item.tag:'info'" v-for="item in rankList" style="width: 100%;margin-bottom: 1px;height: 40px"><span style="font-size: 12px;line-height: 40px;">&emsp;<i class="el-icon-star-on"></i>&emsp;&emsp;{{item.label}}</span></el-tag>
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
              <el-tag :type="form.ultimateRank === item.id?item.tag:'info'" v-if="form.ultimateRank === item.id" v-for="item in rankList" style="width: 100%;margin-bottom: 1px;height: 50px"><span style="font-size: 12px;line-height: 50px">&emsp;<i class="el-icon-star-on"></i>&emsp;&emsp;{{item.label}}</span></el-tag>
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
      <el-table v-show="showReport">
        <el-table-column></el-table-column>
      </el-table>
    </el-card>
    <br>
  </div>
</template>

<script>
export default {
  name: "AbilityAssess",
  data() {
    // const validateAssessReason = (rule, value, callback) => {
    //   if (this.assessReason.length > 0) {
    //     callback();
    //   } else {
    //     return callback(new Error('请选择评估原因'));
    //   }
    // };
    return {
      active: 0,
      beforeFlag:0,
      tabPosition: 'top',
      tabName:'daily',
      assessReason:[],
      rankChangeReason:[],
      dailyActive:true,
      dailyStatus:'process',
      spiritActive:false,
      spiritStatus:'wait',
      sensoryActive:false,
      sensoryStatus:'wait',
      socialAttendActive:false,
      socialAttendStatus:'wait',
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
        dailyRank: '',
        spiritRank:'',
        sensoryRank:'',
        socialAttendRank:'',
        primaryRank:'',
        rankChangeReason:'',
        ultimateRank:'',
        assessSign:'',
        informationProviderSign:'',
        isFinish:0
      },
      daily: {
        assessId:'',
        eat: '',
        bath: '',
        makeup: '',
        wear: '',
        shit: '',
        bee: '',
        goToilet: '',
        bedTransfer: '',
        walk: '',
        floor: '',
        dailyLife: 100,
        dailyRank: 0,
      },
      socialAttend:{
        assessId:'',
        lifeAbility:'',
        workAbility:'',
        timeSpace:'',
        personOrientation:'',
        socialCommunication:'',
        socialAttendTotal:0,
        socialAttendRank:0
      },
      sensory:{
        assessId:'',
        conscious:'',
        sight:'',
        listening:'',
        communicate:'',
        sensoryRank:0,
      },
      spirit:{
        assessId:'',
        recognition:'',
        attack:'',
        depress:'',
        spiritTotal:0,
        spiritRank:0
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
        // assessReason: [{required: true,validator: validateAssessReason, trigger: 'change'}],
        assessReason:[
          { required: true, message: '请选择评估原因', trigger: 'change'},
        ],
      },
      assessReasonList:[
        {
          value: '1',
          label: '接受服务前初评'
        },
        {
          value: '2',
          label: '接受服务后的常规评估'
        },
        {
          value: '3',
          label: '状况发生变化后的即时评估'
        },
        {
          value: '4',
          label: '因评估结果有疑问进行的复评'
        },
      ],
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
             score:''
          },
          {
            id: 2,
            flag:'bath',
            label: 'B.1.2 洗澡',
            selectList: [
              {value: '5', label: '5 分， 准备好洗澡水后，可自己独立完成洗澡过程'},
              {value: '0', label: '0 分， 在洗澡过程中需他人帮助'},
            ],
            score:''
          },
          {
            id: 3,
            flag:'makeup',
            label: 'B.1.3 修饰：指洗脸、刷牙、梳头、刮脸等',
            selectList: [
              {value: '5', label: '5 分， 可自己独立完成'},
              {value: '0', label: '0 分， 需他人帮助'},
            ],
            score:''
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
            score:''
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
            score:''
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
          score:''
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
          score:''
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
          score:''
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
          score:''
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
          score:''
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
        {id:'1',label:'①   有认知障碍/痴呆、精神疾病者，在原有能力级别上提高一个等级',value:'1'},
        {id:'2',label:'②   近 30 天内发生过 2 次及以上跌倒、噎食、自杀、走失者，在原有能力级别上提高一个等级',value:'2'},
        {id:'3',label:'③   处于昏迷状态者，直接评定为重度失能',value:'3'},
      ],
      rankList:[
        {id:'0',label:'0级       能力完好',value:'0',tag:'success'},
        {id:'1',label:'1级       轻度失能',value:'1',tag:'primary'},
        {id:'2',label:'2级       中度失能',value:'2',tag:'warning'},
        {id:'3',label:'3级       重度失能 ',value:'3',tag:'danger'},
      ],
      info:[]
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
  },
  methods: {
    getTime() {
      this.form.assessDate = new Date()
    },
    assessIdCreate(){
      this.request.post("/senior/assessId").then(res=>{
        if(res.code === '200'){
          this.form.assessId = res.data;
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
    save(){
      this.$confirm('此操作将提交数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        if(user) {
          //插入评估结果
          this.form.assessReason = this.assessReason[0]
          if(this.insertSpirit() && this.insertDaily() && this.insertSensory() && this.insertSocialAttend() && this.insertAssessor()) {
            this.form.isFinish = 1
            this.request.post("/assess",this.form).then(res=>{
              if(res.code === '200'){
                  this.$message.success("提交成功")
                  //隐藏控件
                  this.showTabs = false
                  this.showReport = true
                  this.showResult = false
                  this.showBefore = false
                  //更换步骤条
                  if (this.active === 1) this.active = 2;
              }
              else{
                this.$message.error("提交失败")
              }
            })
          }
          else{
            this.$message.error("提交失败")
          }
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
    async insertDaily() {
      let bool = ''
      await this.request.post("/daily", this.daily).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
    async insertSpirit() {
      let bool = ''
      await this.request.post("/spirit", this.spirit).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
    async insertSensory() {
      let bool = ''
      await this.request.post("/sensory", this.sensory).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
    async insertSocialAttend() {
      let bool = ''
      await this.request.post("/socialAttend", this.socialAttend).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
    async insertAssessor() {
      let bool = ''
      await this.request.post("/assessorAssess", this.assessor).then(res => {
        bool = res.code
      })
      return bool === '200'
    },
    nextStep(value){
      if(value === 'spirit') {
        this.insertDaily()
        this.spiritActive = true
        this.dailyActive = false
        this.dailyStatus = 'success'
        this.spiritStatus = 'process'

      }
      if(value === 'sensory') this.insertSpirit()
      if(value === 'socialAttend') this.insertSensory()
      this.tabName = value
    },
    nextTabs(){
      let id = this.form.assessId;
      this.$refs['infoForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.active === 0) this.active = 1;
          this.showTabs = true
          this.showInfo = false
          this.beforeFlag += 1
          this.daily.assessId = id;
          this.spirit.assessId = id;
          this.sensory.assessId = id;
          this.socialAttend.assessId = id;

          this.form.assessReason = this.assessReason[0]
          this.request.post("/assess",this.form);
          this.insertAssessor()
        }
      })
    },
    nextResult(){
      this.insertSocialAttend()
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
      if(this.info != null) {
        let dailyRank = this.daily.dailyRank
        let spiritRank = this.spirit.spiritRank
        let sensoryRank = this.sensory.sensoryRank
        let socialAttendRank = this.socialAttend.socialAttendRank
        let primaryRank = 0
        if (dailyRank === 0) {
          if (spiritRank === 0 && sensoryRank === 0 && (socialAttendRank === 0 || socialAttendRank === 1)) {
            primaryRank = 0
          } else if (spiritRank >= 1 || sensoryRank >= 1 || socialAttendRank >= 2) {
            primaryRank = 1
          }
        } else if (dailyRank === 1) {
          if (spiritRank === 3 || sensoryRank === 3 || socialAttendRank === 3) {
            primaryRank = 2
          } else if (spiritRank === 2 && sensoryRank === 2 && socialAttendRank === 2) {
            primaryRank = 2
          } else if ((spiritRank === 1 || spiritRank === 0) && (sensoryRank === 1 || sensoryRank === 0) && (socialAttendRank === 1 || sensoryRank === 0)) {
            primaryRank = 2
          }
        } else if (dailyRank === 2) {
          if (spiritRank === 2 && sensoryRank === 2 && socialAttendRank === 2) {
            primaryRank = 3
          } else if (spiritRank === 3 || sensoryRank === 3 || socialAttendRank === 3) {
            primaryRank = 3
          } else if (spiritRank === 2 || sensoryRank === 2 || socialAttendRank === 2) {
            primaryRank = 2
          } else if (spiritRank === 1 || sensoryRank === 1 || socialAttendRank === 1) {
            primaryRank = 2
          } else if (spiritRank === 0 || sensoryRank === 0 || socialAttendRank === 0) {
            primaryRank = 2
          }
        } else if (dailyRank === 3) {
          primaryRank = 3
        }
        this.form.primaryRank = primaryRank.toString()


        let stupidStatus = this.info.stupidStatus
        let spiritDisease = this.info.spiritDisease
        let fallDown = this.info.fallDown
        let beLost = this.info.beLost
        let choke = this.info.choke
        let suicide = this.info.suicide

        let finalRank = primaryRank
        let changeRemarks = []
        let changeRemark = ""
        if (primaryRank < 3) {
          if (parseInt(stupidStatus) !== 0 || parseInt(spiritDisease) !== 0) {
            finalRank += 1
            changeRemarks.push("1")
          }
          if (parseInt(fallDown) >= 2 || parseInt(beLost) >= 2 || parseInt(choke) >= 2 || parseInt(suicide) >= 2) {
            finalRank += 1
            changeRemarks.push("2")
          }
          if (this.sensory.conscious === '3') {
            finalRank = 3
            changeRemarks.push("3")
          }
          if (finalRank > 3) {
            finalRank = 3
          }
          changeRemark = changeRemarks.join(',')
          this.rankChangeReason = changeRemarks
          this.form.rankChangeReason = changeRemark
          this.form.ultimateRank = finalRank.toString()

        }



        this.showTabs = false
        this.showBefore = true
        this.showResult = true
        this.beforeFlag += 1
      }
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
    dailyLifeChange(id,score){
      switch (id) {
        case 1:this.daily.eat = score;break
        case 2:this.daily.bath = score;break
        case 3:this.daily.makeup = score;break
        case 4:this.daily.wear = score;break
        case 5:this.daily.shit = score;break
        case 6:this.daily.bee = score;break
        case 7:this.daily.goToilet = score;break
        case 8:this.daily.bedTransfer = score;break
        case 9:this.daily.walk = score;break
        case 10:this.daily.floor = score;break
        case 12:this.daily.dailyRank = score;break
      }
      let total = 0;
      let rank = 0;
      for(let i=0; i<this.dailyLifeList.length; i++){
        total = total + parseInt(this.dailyLifeList[i].score)
      }
      this.daily.dailyLife = total
      if(total>=100){
        rank = 0
      }
      else if(total<=95 && total>=65){
        rank = 1
      }
      else if(total<=60 && total>=45){
        rank = 2
      }
      else{
        rank = 3
      }
      this.form.dailyRank = rank.toString()
      console.log(typeof this.form.dailyRank)
      console.log(this.form.dailyRank)
      this.daily.dailyRank = rank
    },
    spiritStatusChange(){
      this.spirit.spiritTotal = parseInt(this.spirit.recognition)+parseInt(this.spirit.attack)+parseInt(this.spirit.depress)
      let total = this.spirit.spiritTotal
      let rank = 0
      if(total === 0){
        rank = 0
      }
      else if(total === 1){
        rank= 1
      }
      else if(total<=3 && total>=2){
        rank = 2
      }
      else{
        rank = 3
      }
      this.form.spiritRank =  rank.toString()
      this.spirit.spiritRank = rank
    },
    sensoryChange(){
      let conscious = parseInt(this.sensory.conscious);
      let sight = parseInt(this.sensory.sight);
      let listening = parseInt(this.sensory.listening);
      let communicate = parseInt(this.sensory.communicate);
      let rank = 0;
      if(conscious <= 0){
          if(sight === 4 || listening === 4 || communicate === 3){
            rank = 3;
          }
          else if(sight === 3 || listening === 3 || communicate === 2){
            rank = 2;
          }
          else if(sight === 2 || listening === 2 ){
            rank = 1;
          }
          else if(sight<=1&&listening<=1){
            if(communicate === 1){
              rank = 1;
            }
            else if(communicate === 0){
              rank = 0;
            }
          }
      }
      else if(conscious === 1){
        if(sight === 4 || listening === 4 || communicate === 3){
          rank = 3;
        }
        else if((sight <= 3 || listening <=3) && communicate <= 2){
          rank = 2;
        }
      }
      else if(conscious === 2){
        rank = 3;
      }
      else {
        rank = 3;
      }
      this.form.sensoryRank =  rank.toString()
      this.sensory.sensoryRank = rank
    },
    socialAttendChange(){
      this.socialAttend.socialAttendTotal = parseInt(this.socialAttend.lifeAbility)+parseInt(this.socialAttend.workAbility)+parseInt(this.socialAttend.timeSpace)+parseInt(this.socialAttend.personOrientation)+parseInt(this.socialAttend.socialCommunication)
      let total = this.socialAttend.socialAttendTotal
      let rank = 0
      if(total >= 0 && total <= 2){
        rank = 0
      }
      else if(total >= 3 && total <= 7){
        rank = 1
      }
      else if(total >= 8 && total <= 13){
        rank = 2
      }
      else{
        rank = 3
      }
      this.form.socialAttendRank = rank.toString()
      this.socialAttend.socialAttendRank = rank
    }

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
</style>