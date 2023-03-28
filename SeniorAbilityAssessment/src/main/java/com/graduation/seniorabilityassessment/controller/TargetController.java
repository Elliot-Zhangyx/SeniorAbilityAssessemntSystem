package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.ITargetService;
import com.graduation.seniorabilityassessment.entity.Target;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/target")
        public class TargetController {
    
        @Resource
        private ITargetService targetService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody List<Target> targetList) {
            targetService.remove(new QueryWrapper<>());
            boolean flag = false;
            for(Target target :targetList){
                flag = targetService.saveOrUpdate(target);
            }
            return Result.success(flag);
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(targetService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(targetService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(targetService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(targetService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Target> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(targetService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

