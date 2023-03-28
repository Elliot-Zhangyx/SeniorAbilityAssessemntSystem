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
import lombok.ToString;

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
@ToString
@TableName("sys_subject")
@ApiModel(value = "Subject对象", description = "")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer subjectUid;

        private Integer moduleId;

        private String subjectTitle;

        private String subjectAttention;

        private String subjectScore;
        @TableField(exist = false)
        private List<Option> optionList;

}
