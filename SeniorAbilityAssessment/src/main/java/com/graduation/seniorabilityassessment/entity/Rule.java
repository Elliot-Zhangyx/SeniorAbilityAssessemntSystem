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
 * @since 2023-03-14
 */
@Getter
@Setter
@TableName("sys_rule")
@ApiModel(value = "Rule对象", description = "")
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String rank;

    private String description;

    private String rankLeft;

    private String rankRight;

    private String tag;


}
