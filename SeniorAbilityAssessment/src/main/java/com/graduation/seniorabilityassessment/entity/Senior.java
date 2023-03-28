package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-14
 */
@Getter
@Setter
  @TableName("sys_senior")
@ApiModel(value = "Senior对象", description = "")
public class Senior implements Serializable {

    private static final long serialVersionUID = 1L;
      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

//      @ApiModelProperty("评估编号")
//        private String assessId;
//
//      @ApiModelProperty("评估基准日期")
//      private LocalDateTime assessDate;
//
//      @ApiModelProperty("评估原因")
//      private String assessReason;

      @ApiModelProperty("姓名")
      private String assessedName;

      @ApiModelProperty("身份证号")
      private String identityCard;

      @ApiModelProperty("性别")
      private String gender;

      @ApiModelProperty("出生日期")
      private String birthday;

      @ApiModelProperty("年龄")
      private String age;

      @ApiModelProperty("身高")
      private Integer height;

      @ApiModelProperty("体重")
      private Integer weight;

      @ApiModelProperty("民族")
      private String nationality;

      @ApiModelProperty("文化程度")
      private String educationLevel;

      @ApiModelProperty("宗教信仰")
      private String religion;

      @ApiModelProperty("婚姻状况")
      private String maritalStatus;

      @ApiModelProperty("居住情况")
      private String residenceStatus;

      @ApiModelProperty("医疗费用支付方式")
      private String medicalPayments;

      @ApiModelProperty("经济来源")
      private String financialSource;

      @ApiModelProperty("跌倒")
      private String fallDown;

      @ApiModelProperty("走失")
      private String beLost;

      @ApiModelProperty("噎食")
      private String choke;

      @ApiModelProperty("自杀")
      private String suicide;

      @ApiModelProperty("其他近30天内意外事件")
      private String otherAccident;

      @ApiModelProperty("信息提供者的姓名")
      private String informationProviderName;

      @ApiModelProperty("信息提供者与老人的关系")
      private String informationProviderAndSeniorAssociation;

      @ApiModelProperty("联系人姓名")
      private String contactName;

      @ApiModelProperty(" 联系人电话")
      private String contactPhone;


      @ApiModelProperty("疾病诊断")
      private String disease;

      @ApiModelProperty("用药情况")
      private String medication;

      @ApiModelProperty("压力性损伤")
      private String stressInjury;


      @ApiModelProperty("关节活动度")
      private String jointActivity;

      @ApiModelProperty("伤口情况")
      private String woundCondition;

      @ApiModelProperty("特殊医疗照护情况")
      private String specialMedicalCare;

      @ApiModelProperty("疼痛感")
      private String painPerception;

      @ApiModelProperty("牙齿缺失情况")
      private String toothLoss;

      @ApiModelProperty("义齿佩戴情况")
      private String dentureWear;

      @ApiModelProperty("吞咽困难的情形和症状")
      private String dysphagia;

      @ApiModelProperty("营养不良")
      private String innutrition;

      @ApiModelProperty("清理呼吸道无效")
      private String respiratory;

      @ApiModelProperty("昏迷")
      private String stupor;

      @ApiModelProperty("是否评估")
      private Integer isAssess;

      @ApiModelProperty("用户id")
      private Integer userId;

      @ApiModelProperty("创建时间")
      private Date createTime;

      @TableField(exist = false)
      private List<Assess> assessList;


}
