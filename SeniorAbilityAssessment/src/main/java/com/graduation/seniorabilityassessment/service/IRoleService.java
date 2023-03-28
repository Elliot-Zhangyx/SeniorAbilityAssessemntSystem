package com.graduation.seniorabilityassessment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.seniorabilityassessment.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-01-24
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
