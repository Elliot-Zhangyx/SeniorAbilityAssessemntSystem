package com.graduation.seniorabilityassessment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.seniorabilityassessment.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-01-24
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
