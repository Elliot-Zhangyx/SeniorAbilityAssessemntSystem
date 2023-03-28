package com.graduation.seniorabilityassessment.controller.dto;

import com.graduation.seniorabilityassessment.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接受前端登录请求的参数
 */
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String identityCard;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
