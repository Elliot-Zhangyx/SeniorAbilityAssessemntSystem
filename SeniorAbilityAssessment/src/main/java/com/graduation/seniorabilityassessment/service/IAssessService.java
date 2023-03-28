package com.graduation.seniorabilityassessment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.AllAssess;
import com.graduation.seniorabilityassessment.entity.Assess;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.seniorabilityassessment.entity.Rule;
import com.graduation.seniorabilityassessment.entity.Senior;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-16
 */
public interface IAssessService extends IService<Assess> {
    List<Assess> getByAssessedId(Integer assessedId);

    Assess getByAssessResultId(String assessId);

    List<Assess> getPrimaryRankEcharts();
    List<Assess> getUltimateRankEcharts();
}
