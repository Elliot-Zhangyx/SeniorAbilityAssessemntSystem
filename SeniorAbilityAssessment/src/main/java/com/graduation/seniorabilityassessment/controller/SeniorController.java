package com.graduation.seniorabilityassessment.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.graduation.seniorabilityassessment.common.Constants;
import com.graduation.seniorabilityassessment.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.User;
import com.graduation.seniorabilityassessment.exception.ServiceException;
import com.graduation.seniorabilityassessment.utils.TokenUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.graduation.seniorabilityassessment.service.ISeniorService;
import com.graduation.seniorabilityassessment.entity.Senior;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-14
 */
@RestController
@RequestMapping("/senior")
        public class SeniorController {
    
        @Resource
        private ISeniorService seniorService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Senior senior) {
            User currentUser = TokenUtils.getCurrentUser();
            if(currentUser != null) {
                senior.setUserId(currentUser.getId());
            }
            else{
                throw new ServiceException(Constants.CODE_401,"系统错误");
            }
            return Result.success(seniorService.saveOrUpdate(senior));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(seniorService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(seniorService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(seniorService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(seniorService.getById(id));
        }

        @GetMapping("/updateIsAssess/{id}")
        public Result updateIsAssess(@PathVariable Integer id) {
            return Result.success(seniorService.updateIsAssess(id));
        }

        @GetMapping("/person/{userId}")
        public Result findInfo(@PathVariable Integer userId) {
            return Result.success(seniorService.getByUserId(userId));
        }


        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String assessedName,
                               @RequestParam(defaultValue = "") String assessedIdentityCard,
                               @RequestParam(defaultValue = "") String assessedGender
                               ) {

            QueryWrapper<Senior> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(Strings.isNotEmpty(assessedName),"assessed_name",assessedName);
            queryWrapper.like(Strings.isNotEmpty(assessedIdentityCard),"identity_card",assessedIdentityCard);
            queryWrapper.like(Strings.isNotEmpty(assessedGender), "gender",assessedGender);
            queryWrapper.orderByDesc("id");
            return Result.success(seniorService.findPage(new Page<Senior>(pageNum, pageSize), assessedName,assessedIdentityCard,assessedGender));
        }

        @PostMapping("/assessId")
        public Result createAssessId() {
            return Result.success(seniorService.getAssessId());
        }

        /**
         * 导出接口
         */
        @GetMapping("/export")
        public void export(HttpServletResponse response) throws Exception {
            // 从数据库查询出所有的数据
            List<Senior> list = seniorService.list();
            // 通过工具类创建writer 写出到磁盘路径
    //        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
            // 在内存操作，写出到浏览器
            ExcelWriter writer = ExcelUtil.getWriter(true);
            //自定义标题别名
            //writer.addHeaderAlias("id", "id");
    //                writer.addHeaderAlias("username", "用户名");
    //                writer.addHeaderAlias("password", "密码");
    //                writer.addHeaderAlias("nickname", "昵称");
    //                writer.addHeaderAlias("email", "邮箱");
    //                writer.addHeaderAlias("phone", "电话");
    //                writer.addHeaderAlias("address", "地址");
    //                writer.addHeaderAlias("createTime", "创建时间");
    //                writer.addHeaderAlias("avatarUrl", "头像");

            // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
            writer.write(list, true);

            // 设置浏览器响应的格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            String fileName = URLEncoder.encode("评估信息", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            ServletOutputStream out = response.getOutputStream();
            writer.flush(out, true);
            out.close();
            writer.close();

        }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Senior> users = reader.readAll(Senior.class);
        // 方式2：忽略表头的中文，直接读取表的内容
//                List<List<Object>> list = reader.read(1);
//                List<User> users = CollUtil.newArrayList();
//                for (List<Object> row : list) {
//                        User user = new User();
//                        user.setUsername(row.get(0).toString());
//                        user.setPassword(row.get(1).toString());
//                        user.setNickname(row.get(2).toString());
//                        user.setEmail(row.get(3).toString());
//                        user.setPhone(row.get(4).toString());
//                        user.setAddress(row.get(5).toString());
//                        user.setAvatarUrl(row.get(6).toString());
//                        users.add(user);
//                }

        seniorService.saveBatch(users);
        return true;
    }
}

