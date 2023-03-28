package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IAssessorAssessService;
import com.graduation.seniorabilityassessment.entity.AssessorAssess;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-26
 */
@RestController
@RequestMapping("/assessorAssess")
        public class AssessorAssessController {
    
        @Resource
        private IAssessorAssessService assessorAssessService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody AssessorAssess assessorAssess) {
            return Result.success(assessorAssessService.saveOrUpdate(assessorAssess));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(assessorAssessService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(assessorAssessService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(assessorAssessService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(assessorAssessService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<AssessorAssess> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(assessorAssessService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

