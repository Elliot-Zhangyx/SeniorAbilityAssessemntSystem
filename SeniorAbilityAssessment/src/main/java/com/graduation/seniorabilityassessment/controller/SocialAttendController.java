package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.ISocialAttendService;
import com.graduation.seniorabilityassessment.entity.SocialAttend;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/socialAttend")
        public class SocialAttendController {
    
        @Resource
        private ISocialAttendService socialAttendService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody SocialAttend socialAttend) {
            return Result.success(socialAttendService.saveOrUpdate(socialAttend));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(socialAttendService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(socialAttendService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(socialAttendService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(socialAttendService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<SocialAttend> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(socialAttendService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

