package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.utils.WordAnalyzer;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IOptionService;
import com.graduation.seniorabilityassessment.entity.Option;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-12
 */
@RestController
@RequestMapping("/option")
        public class OptionController {
    
        @Resource
        private IOptionService optionService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Option option) {
            return Result.success(optionService.saveOrUpdate(option));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(optionService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(optionService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(optionService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(optionService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Option> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(optionService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }

        @GetMapping("/split/score")
        public Result splitGetScore(@RequestParam String content) {
            List<String> scores = WordAnalyzer.splitWordsByNumber(content);
            String score = "";
            if(scores.size()>0){
                score = scores.get(0);
            }
            return Result.success(score);
        }

        @GetMapping("/split/getOption")
        public Result splitGetOption(@RequestParam String content) {
            List<String> options = WordAnalyzer.splitWordsByChinese(content);
            String option = "";
            if(options.size()>0){
                option = options.get(1);
            }
            return Result.success(option);
        }
}

