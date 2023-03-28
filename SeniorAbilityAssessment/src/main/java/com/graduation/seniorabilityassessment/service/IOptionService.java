package com.graduation.seniorabilityassessment.service;

import com.graduation.seniorabilityassessment.entity.Option;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-12
 */
public interface IOptionService extends IService<Option> {
    void setOption(Integer optionId, List<Option> optionList);
}
