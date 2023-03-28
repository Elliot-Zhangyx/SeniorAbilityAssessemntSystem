package com.graduation.seniorabilityassessment.entity;

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
 * @since 2023-03-13
 */
@Getter
@Setter
  @TableName("sys_target")
@ApiModel(value = "Target对象", description = "")
public class Target implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableId
        private Integer targetId;


}
