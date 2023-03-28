package com.graduation.seniorabilityassessment.service.impl;

import com.graduation.seniorabilityassessment.entity.Module;
import com.graduation.seniorabilityassessment.entity.Subject;
import com.graduation.seniorabilityassessment.mapper.ModuleMapper;
import com.graduation.seniorabilityassessment.service.IModuleService;
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
 * @since 2023-03-09
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {

    @Resource
    private ModuleMapper moduleMapper;
    @Override
    public int updateByModuleId(Module module) {
        return moduleMapper.updateById(module);
    }

    @Override
    public Module getByModuleId(Integer id) {
        return moduleMapper.getByModuleId(id);
    }

    @Override
    public int saveOrUpdateModule(Module module) {

        return moduleMapper.saveOrUpdateModule(module);
    }

    @Override
    public List<Module> findAll() {
        return moduleMapper.findAll();
    }
}
