package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
 * @since 2023-02-26
 */
@Getter
@Setter
  @TableName("sys_assessor_assess")
@ApiModel(value = "AssessorAssess对象", description = "")
public class AssessorAssess implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String assessId;

    private String assessorId;

    @TableField(exist = false)
    private User user;


}
