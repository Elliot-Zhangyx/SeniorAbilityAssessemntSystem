package com.graduation.seniorabilityassessment.service.impl;

import com.graduation.seniorabilityassessment.entity.Subject;
import com.graduation.seniorabilityassessment.mapper.SubjectMapper;
import com.graduation.seniorabilityassessment.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-12
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Resource
    private SubjectMapper subjectMapper;
    @Override
    public int saveOrUpdateSubject(Subject subject) {
        return subjectMapper.saveOrUpdateSubject(subject);
    }

    @Override
    public int deleteById(Integer subjectUid) {
        return  subjectMapper.deleteById(subjectUid);
    }
}
