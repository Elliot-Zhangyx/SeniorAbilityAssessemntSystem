package com.graduation.seniorabilityassessment.mapper;

import com.graduation.seniorabilityassessment.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-12
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    int saveOrUpdateSubject(Subject subject);


    int deleteById(Integer subjectUid);
}
