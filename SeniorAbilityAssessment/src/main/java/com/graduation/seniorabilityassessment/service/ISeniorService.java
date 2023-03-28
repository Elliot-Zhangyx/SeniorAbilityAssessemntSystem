package com.graduation.seniorabilityassessment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.Senior;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-14
 */
public interface ISeniorService extends IService<Senior> {

    String getAssessId();

    Senior getByUserId(Integer userId);

    Page<Senior> findPage(Page<Senior> seniorPage, String assessedName, String assessedIdentityCard, String assessedGender);


    Boolean updateIsAssess(Integer id);
}
