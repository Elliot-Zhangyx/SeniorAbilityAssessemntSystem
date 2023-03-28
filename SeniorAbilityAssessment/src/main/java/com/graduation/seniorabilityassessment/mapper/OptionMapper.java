package com.graduation.seniorabilityassessment.mapper;

import com.graduation.seniorabilityassessment.entity.Option;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-12
 */
public interface OptionMapper extends BaseMapper<Option> {
    @Delete("delete from sys_option where subject_id = #{optionId}")
    int deleteByOptionId(Integer optionId);

}
