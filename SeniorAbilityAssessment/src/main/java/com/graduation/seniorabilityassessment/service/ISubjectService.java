package com.graduation.seniorabilityassessment.service;

import com.graduation.seniorabilityassessment.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-12
 */
public interface ISubjectService extends IService<Subject> {

    int saveOrUpdateSubject(Subject subject);

    int deleteById(Integer subjectUid);
}
