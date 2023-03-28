package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.Option;
import com.graduation.seniorabilityassessment.service.IOptionService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.ISubjectService;
import com.graduation.seniorabilityassessment.entity.Subject;

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
@RequestMapping("/subject")
        public class SubjectController {
    
        @Resource
        private ISubjectService subjectService;

        @Resource
        private IOptionService optionService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Subject subject) {
            int id = subjectService.saveOrUpdateSubject(subject);
            //if(subject.getOptionList().size() > 0){
                List<Option> options = subject.getOptionList();
                optionService.setOption(subject.getSubjectUid(),options);
           /// }
            return Result.success(id);
        }

        @DeleteMapping("/{subjectUid}")
        public Result delete(@PathVariable Integer subjectUid) {
            return Result.success(subjectService.deleteById(subjectUid));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(subjectService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(subjectService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(subjectService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(subjectService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

