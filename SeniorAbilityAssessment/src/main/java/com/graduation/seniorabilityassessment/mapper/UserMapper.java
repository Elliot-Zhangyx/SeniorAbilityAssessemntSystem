package com.graduation.seniorabilityassessment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.seniorabilityassessment.controller.dto.UserPasswordDTO;
import com.graduation.seniorabilityassessment.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-01-08
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

    Boolean updateByAvatar(@Param("avatarUrl")  String avatarUrl, @Param("id") Integer id);
}
