package com.graduation.seniorabilityassessment.entity;

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
 * @since 2023-03-12
 */
@Getter
@Setter
  @TableName("sys_option")
@ApiModel(value = "Option对象", description = "")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer subjectId;

    private String subjectOption;

    private String score;

    private String rankChange;


}
