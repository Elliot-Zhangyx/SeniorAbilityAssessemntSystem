package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2023-03-02
 */
@Getter
@Setter
  @TableName("sys_apply")
@ApiModel(value = "Apply对象", description = "")
public class Apply implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("评估编号")
      private String assessId;

      @ApiModelProperty("申请人id")
      private Integer applyId;

      @ApiModelProperty("评估原因")
      private String assessReason;

      @ApiModelProperty("申请日期")
      private LocalDateTime applyDate;

      @ApiModelProperty("申请状态")
      private Integer applyStatus;

      @ApiModelProperty("姓名")
      private String assessedName;

      @ApiModelProperty("身份证号")
      private String identityCard;



}
