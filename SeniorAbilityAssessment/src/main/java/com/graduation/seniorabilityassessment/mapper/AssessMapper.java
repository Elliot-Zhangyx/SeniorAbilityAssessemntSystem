package com.graduation.seniorabilityassessment.mapper;

import com.graduation.seniorabilityassessment.entity.Assess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-16
 */
public interface AssessMapper extends BaseMapper<Assess> {

//    @Select("select a.*,u.nickname as assessor_name,u.phone as assessorPhone from sys_assess a left join sys_user u on u.id = a.assessor_id where assessed_id =  #{assessedId}")
    List<Assess> getByAssessedId(Integer assessedId);

    Assess getByAssessResultId(String assessId);

    List<Assess> getPrimaryRankEcharts();

    List<Assess> getUltimateRankEcharts();
}
