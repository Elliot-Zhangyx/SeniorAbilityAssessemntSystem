package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
 * @since 2023-03-09
 */
@Getter
@Setter
  @TableName("sys_module")
@ApiModel(value = "Module对象", description = "")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("标题")
      private String title;

      @ApiModelProperty("备注")
      private String remark;

      private Boolean enable;

      private Integer testId;

      @ApiModelProperty("创建时间")
      private Date createTime;
      @TableField(exist = false)
      private List<Subject> subjectList;


}
