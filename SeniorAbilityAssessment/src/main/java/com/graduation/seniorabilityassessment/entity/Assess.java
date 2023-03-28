package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sun.management.Sensor;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-16
 */
@Getter
@Setter
  @TableName("sys_assess")
@ApiModel(value = "Assess对象", description = "")
public class Assess implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      @ApiModelProperty("id")
      private Integer id;

      @ApiModelProperty("评估编号")
      private String assessId;

      @ApiModelProperty("评估基准日期")
      private Date assessDate;

      @ApiModelProperty("评估原因")
      private String assessReason;

      @ApiModelProperty("被评估者id")
      private Integer assessedId;

      @ApiModelProperty("申请id")
      private Integer applyId;

      @ApiModelProperty("被评估者姓名")
      private String assessedName;

      @ApiModelProperty("被评估者身份证号")
      private String assessedIdentityCard;


      @ApiModelProperty("评估总分")
      private String totalScore;

      @ApiModelProperty("老年人能力初步等级")
      private String primaryRank;

      @ApiModelProperty("等级变更条款")
      private String rankChangeReason;

      @ApiModelProperty("老年人能力最终等级 ")
      private String ultimateRank;

      @ApiModelProperty("评估员签名")
      private String assessSign;

      @ApiModelProperty("信息提供者签字")
      private String informationProviderSign;

      @ApiModelProperty("是否完成")
      private Integer isFinish;

      @TableField(exist = false)
      private List<User> userList;

      @TableField(exist = false)
      private Conclusion conclusion;

      @TableField(exist = false)
      private Integer number;
}
