package com.graduation.seniorabilityassessment.service;

import com.graduation.seniorabilityassessment.entity.Module;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.seniorabilityassessment.entity.Subject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-09
 */
public interface IModuleService extends IService<Module> {

    int updateByModuleId(Module module);

    Module getByModuleId(Integer id);

    int saveOrUpdateModule(Module module);

    List<Module> findAll();
}
