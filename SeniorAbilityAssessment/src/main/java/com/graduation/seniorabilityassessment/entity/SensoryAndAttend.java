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
  @TableName("sys_sensory_and_attend")
@ApiModel(value = "SensoryAndAttend对象", description = "")
public class SensoryAndAttend implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @TableId
      @ApiModelProperty("评估编号")
        private String assessId;

      @ApiModelProperty("视力")
      private String sight;

      @ApiModelProperty("听力")
      private String listening;

      @ApiModelProperty("执行日常事务")
      private String dailyAffairs;

      @ApiModelProperty("使用交通工具外出")
      private String vehicle;

      @ApiModelProperty("社会交往能力")
      private String socialCommunication;

      @ApiModelProperty("总得分")
      private String total;


}
