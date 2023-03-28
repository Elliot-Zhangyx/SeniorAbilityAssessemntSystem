package com.graduation.seniorabilityassessment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.seniorabilityassessment.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-01-24
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where flag = #{flag}")
    Integer selectByFlag(String role);
}
