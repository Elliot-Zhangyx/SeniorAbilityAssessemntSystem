package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IApplyService;
import com.graduation.seniorabilityassessment.entity.Apply;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-02
 */
@RestController
@RequestMapping("/apply")
        public class ApplyController {
    
        @Resource
        private IApplyService applyService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Apply apply) {
            return Result.success(applyService.saveOrUpdate(apply));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(applyService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(applyService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(applyService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(applyService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Apply> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(applyService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

