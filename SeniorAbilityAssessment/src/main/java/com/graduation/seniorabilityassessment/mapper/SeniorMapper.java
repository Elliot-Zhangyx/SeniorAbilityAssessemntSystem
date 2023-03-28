package com.graduation.seniorabilityassessment.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.Senior;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-14
 */
public interface SeniorMapper extends BaseMapper<Senior> {

    Senior getByUserId(Integer userId);

    Page<Senior> findPage(Page<Senior> seniorPage, @Param("assessedName") String assessedName,@Param("assessedIdentityCard") String assessedIdentityCard,@Param("assessedGender") String assessedGender);

    Boolean updateIsAssess();
}
