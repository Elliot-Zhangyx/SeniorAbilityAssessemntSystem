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
  @TableName("sys_spirit")
@ApiModel(value = "Spirit对象", description = "")
public class Spirit implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @TableId
      @ApiModelProperty("评估编号")
        private String assessId;

      @ApiModelProperty("时间定向")
      private String timeAbility;

      @ApiModelProperty("空间定向")
      private String roomAbility;

      @ApiModelProperty("人物定向")
      private String personOrientation;

      @ApiModelProperty("记忆力")
      private String memory;

      @ApiModelProperty("理解能力")
      private String understandAbility;

      @ApiModelProperty("表达能力")
      private String expressAbility;

      @ApiModelProperty("攻击行为")
      private String attack;

      @ApiModelProperty("抑郁症状")
      private String depress;

      @ApiModelProperty("意识水平")
      private String conscious;

      @ApiModelProperty("总分")
      private String total;


}
