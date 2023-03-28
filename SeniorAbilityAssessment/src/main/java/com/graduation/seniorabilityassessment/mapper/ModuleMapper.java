package com.graduation.seniorabilityassessment.mapper;

import com.graduation.seniorabilityassessment.entity.Module;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.seniorabilityassessment.entity.Subject;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-09
 */
public interface ModuleMapper extends BaseMapper<Module> {

    Module getByModuleId(Integer id);

    int saveOrUpdateModule(Module module);

    List<Module> findAll();
}
