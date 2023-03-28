package com.graduation.seniorabilityassessment.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.*;
import com.graduation.seniorabilityassessment.mapper.OptionMapper;
import com.graduation.seniorabilityassessment.service.ISubjectService;
import com.graduation.seniorabilityassessment.utils.TabulaUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.IModuleService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-03-09
 */
@RestController
@RequestMapping("/module")
        public class ModuleController {
    
        @Resource
        private IModuleService moduleService;
        @Resource
        private ISubjectService subjectService;
        @Resource
        private OptionMapper optionMapper;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Module module) {
            return Result.success(moduleService.saveOrUpdate(module));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(moduleService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(moduleService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(moduleService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(moduleService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
            QueryWrapper<Module> queryWrapper = new QueryWrapper<>();
//            queryWrapper.orderByDesc("id");
            return Result.success(moduleService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }

        @PostMapping("/update")
        public Result update(@RequestBody Module module) {
            return Result.success(moduleService.updateByModuleId(module));
        }

        @GetMapping("/subject/{id}")
        public Result findSubject(@PathVariable Integer id) {
            return Result.success(moduleService.getByModuleId(id));
        }

        @PostMapping("/parsePdf")
        public Result parsePdf(MultipartFile file) throws Exception {
            File file1 = TabulaUtil.convert(file);
            List<Module> moduleList = TabulaUtil.parsePdf(file1);
            int flag = 0;
            for(Module module:moduleList){
                module.setId(0);
                flag = moduleService.saveOrUpdateModule(module);
                for(Subject subject:module.getSubjectList()){
                    subject.setSubjectUid(0);
                    subject.setModuleId(module.getId());
                    flag = subjectService.saveOrUpdateSubject(subject);
                    for(Option option:subject.getOptionList()){
                        option.setSubjectId(subject.getSubjectUid());
                        optionMapper.insert(option);
                    }
                }
            }
            return Result.success(flag);
        }

    @GetMapping("/getAll")
    public Result findAllData() {
        return Result.success(moduleService.findAll());
    }


}

