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
  @TableName("sys_sport")
@ApiModel(value = "Sport对象", description = "")
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @TableId
      @ApiModelProperty("评估编号")
        private String assessId;

      @ApiModelProperty("床上体位转移")
      private String upAndDown;

      @ApiModelProperty("床椅转移")
      private String bedTransfer;

      @ApiModelProperty("平地行走")
      private String walk;

      @ApiModelProperty("上下楼梯")
      private String floor;

      @ApiModelProperty("总计得分")
      private String total;


}
