package com.graduation.seniorabilityassessment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.seniorabilityassessment.common.Result;
import com.graduation.seniorabilityassessment.controller.dto.UserDTO;
import com.graduation.seniorabilityassessment.controller.dto.UserPasswordDTO;
import com.graduation.seniorabilityassessment.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-01-08
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Boolean updateByAvatar(String avatarUrl,Integer id);
}
