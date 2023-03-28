package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.Option;
import com.graduation.seniorabilityassessment.entity.Target;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IRuleService;
import com.graduation.seniorabilityassessment.entity.Rule;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/rule")
        public class RuleController {
    
        @Resource
        private IRuleService ruleService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Rule rule) {
            return Result.success(ruleService.saveOrUpdate(rule));
        }

        // 新增或者更新
        @PostMapping("/saveList")
        public Result saveList(@RequestBody List<Rule> ruleList) {
            ruleService.remove(new QueryWrapper<>());
            boolean flag = false;
            for(Rule rule:ruleList){
                System.out.println(rule);
                flag = ruleService.saveOrUpdate(rule);
            }
            return Result.success(flag);
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(ruleService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(ruleService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(ruleService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(ruleService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(ruleService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

