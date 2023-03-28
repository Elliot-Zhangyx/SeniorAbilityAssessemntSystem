package com.graduation.seniorabilityassessment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.seniorabilityassessment.entity.Role;
import com.graduation.seniorabilityassessment.entity.RoleMenu;
import com.graduation.seniorabilityassessment.mapper.RoleMapper;
import com.graduation.seniorabilityassessment.mapper.RoleMenuMapper;
import com.graduation.seniorabilityassessment.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-01-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id",roleId);
//        roleMenuMapper.delete(queryWrapper);

          // 先删除当前角色id所有的绑定关系
          roleMenuMapper.deleteByRoleId(roleId);

          // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
          for(Integer menuId : menuIds){
              RoleMenu roleMenu = new RoleMenu();
              roleMenu.setRoleId(roleId);
              roleMenu.setMenuId(menuId);
              roleMenuMapper.insert(roleMenu);
          }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
