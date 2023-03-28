package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2023-03-05
 */
@Getter
@Setter
  @TableName("sys_daily")
@ApiModel(value = "Daily对象", description = "")
public class Daily implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @TableId
      @ApiModelProperty("评估编号")
      private String assessId;

      @ApiModelProperty("进食")
      private String eat;

      @ApiModelProperty("洗澡")
      private String bath;

      @ApiModelProperty("修饰")
      private String makeup;

      @ApiModelProperty("穿上衣")
      private String wearCoat;

      @ApiModelProperty("穿裤子")
      private String wearPants;

      @ApiModelProperty("大便控制")
      private String shit;

      @ApiModelProperty("小便控制 ")
      private String bee;

      @ApiModelProperty("如厕")
      private String goToilet;

      @ApiModelProperty("床椅转移")
      private String total;


}
