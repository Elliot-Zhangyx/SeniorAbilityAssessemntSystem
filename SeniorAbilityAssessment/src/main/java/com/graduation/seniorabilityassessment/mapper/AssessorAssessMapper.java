package com.graduation.seniorabilityassessment.mapper;

import com.graduation.seniorabilityassessment.entity.AssessorAssess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-26
 */
public interface AssessorAssessMapper extends BaseMapper<AssessorAssess> {

//    @Select("select *from sys_assessor_assess where assess_id = #{assessId}")
//    List<AssessorAssess> getByAssessId(String assessId);
}
