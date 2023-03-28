package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IConclusionService;
import com.graduation.seniorabilityassessment.entity.Conclusion;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-17
 */
@RestController
@RequestMapping("/conclusion")
        public class ConclusionController {
    
        @Resource
        private IConclusionService conclusionService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Conclusion conclusion) {
            return Result.success(conclusionService.saveOrUpdate(conclusion));
        }

        @PostMapping("/saveList")
        public Result saveList(@RequestBody List<Conclusion> conclusionList) {
            boolean flag = false;
            for(Conclusion conclusion:conclusionList){
                flag = conclusionService.saveOrUpdate(conclusion);
            }
            return Result.success(flag);
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(conclusionService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(conclusionService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(conclusionService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(conclusionService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Conclusion> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(conclusionService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
}

