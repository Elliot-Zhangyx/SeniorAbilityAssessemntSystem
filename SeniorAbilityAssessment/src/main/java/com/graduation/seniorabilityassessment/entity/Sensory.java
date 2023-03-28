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
  @TableName("sys_sensory")
@ApiModel(value = "Sensory对象", description = "")
public class Sensory implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @TableId
      @ApiModelProperty("评估编号")
        private String assessId;

      @ApiModelProperty("意识水平")
      private String conscious;

      @ApiModelProperty("视力")
      private String sight;

      @ApiModelProperty("听力")
      private String listening;

      @ApiModelProperty("沟通交流")
      private String communicate;

      @ApiModelProperty("感知觉与沟通分级 ")
      private String sensoryRank;


}
