package com.graduation.seniorabilityassessment.controller;

import com.graduation.seniorabilityassessment.common.Constants;
import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.Senior;
import com.graduation.seniorabilityassessment.entity.User;
import com.graduation.seniorabilityassessment.exception.ServiceException;
import com.graduation.seniorabilityassessment.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IAssessService;
import com.graduation.seniorabilityassessment.entity.Assess;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-16
 */
@RestController
@RequestMapping("/assess")
        public class AssessController {
    
        @Resource
        private IAssessService assessService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Assess assess) {
//            User currentUser = TokenUtils.getCurrentUser();
//            if(currentUser != null) {
//                Integer assessor_id = currentUser.getId();
//                assess.setAssessorId(assessor_id);
//            }
//            else{
//                throw new ServiceException(Constants.CODE_400,"用户信息失效,请重新登录");
//            }
            return Result.success(assessService.saveOrUpdate(assess));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(assessService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(assessService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(assessService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(assessService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Assess> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            return Result.success(assessService.page(new Page<>(pageNum, pageSize),queryWrapper));
        }

        @PostMapping("/{assessedId}")
        public Result findAssess(@PathVariable Integer assessedId) {
            return Result.success(assessService.getByAssessedId(assessedId));
        }
        @GetMapping("/editAssess")
        public Result getAssessResult(@RequestParam String assessId) {
            return Result.success(assessService.getByAssessResultId(assessId));
        }

}

