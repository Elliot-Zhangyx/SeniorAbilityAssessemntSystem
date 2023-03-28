package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.ISpiritService;
import com.graduation.seniorabilityassessment.entity.Spirit;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-05
 */
@RestController
@RequestMapping("/spirit")
        public class SpiritController {
    
        @Resource
        private ISpiritService spiritService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Spirit spirit) {
            return Result.success(spiritService.saveOrUpdate(spirit));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(spiritService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(spiritService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(spiritService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(spiritService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Spirit> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(spiritService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

