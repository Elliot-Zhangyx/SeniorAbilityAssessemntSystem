package com.graduation.seniorabilityassessment.service.impl;

import com.graduation.seniorabilityassessment.entity.Option;
import com.graduation.seniorabilityassessment.entity.RoleMenu;
import com.graduation.seniorabilityassessment.mapper.OptionMapper;
import com.graduation.seniorabilityassessment.service.IOptionService;
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
 * @since 2023-03-12
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements IOptionService {

    @Resource
    private OptionMapper optionMapper;
    @Override
    public void setOption(Integer optionId, List<Option> optionList) {

        // 先删除当前角色id所有的绑定关系
        optionMapper.deleteByOptionId(optionId);

        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        for(Option option : optionList){
            option.setSubjectId(optionId);
            optionMapper.insert(option);
        }
    }
}
