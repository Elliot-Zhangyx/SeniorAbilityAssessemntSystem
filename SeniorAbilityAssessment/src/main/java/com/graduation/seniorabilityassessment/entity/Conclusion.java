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
 * @since 2023-03-17
 */
@Getter
@Setter
  @TableName("sys_conclusion")
@ApiModel(value = "Conclusion对象", description = "")
public class Conclusion implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @TableId
      private String assessId;

      private String result;


}
