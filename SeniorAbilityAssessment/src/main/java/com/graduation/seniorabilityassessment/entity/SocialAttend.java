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
 * @since 2023-02-18
 */
@Getter
@Setter
  @TableName("sys_social_attend")
@ApiModel(value = "SocialAttend对象", description = "")
public class SocialAttend implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @TableId
      @ApiModelProperty("评估编号")
      private String assessId;

      @ApiModelProperty("生活能力")
      private String lifeAbility;

      @ApiModelProperty("工作能力")
      private String workAbility;

      @ApiModelProperty("时间/空间定向")
      private String timeSpace;

      @ApiModelProperty("人物定向")
      private String personOrientation;

      @ApiModelProperty("社会交往能力")
      private String socialCommunication;

      @ApiModelProperty("社会参与总分")
      private String socialAttendTotal;

      @ApiModelProperty("社会参与分级")
      private String socialAttendRank;


}
