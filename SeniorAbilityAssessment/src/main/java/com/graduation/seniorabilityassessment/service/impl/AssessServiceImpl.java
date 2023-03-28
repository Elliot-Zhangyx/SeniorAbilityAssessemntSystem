package com.graduation.seniorabilityassessment.service.impl;

import com.graduation.seniorabilityassessment.entity.AllAssess;
import com.graduation.seniorabilityassessment.entity.Assess;
import com.graduation.seniorabilityassessment.mapper.AssessMapper;
import com.graduation.seniorabilityassessment.service.IAssessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-16
 */
@Service
public class AssessServiceImpl extends ServiceImpl<AssessMapper, Assess> implements IAssessService {

    @Resource
    private AssessMapper assessMapper;
    @Override
    public List<Assess> getByAssessedId(Integer assessedId) {
        return assessMapper.getByAssessedId(assessedId);
    }

    @Override
    public Assess getByAssessResultId(String assessId) {
        return assessMapper.getByAssessResultId(assessId);
    }

    @Override
    public List<Assess> getPrimaryRankEcharts() {
        return assessMapper.getPrimaryRankEcharts();
    }

    @Override
    public List<Assess> getUltimateRankEcharts() {
        return assessMapper.getUltimateRankEcharts();
    }
}
