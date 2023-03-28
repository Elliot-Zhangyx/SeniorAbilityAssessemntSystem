package com.graduation.seniorabilityassessment.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.graduation.seniorabilityassessment.common.Result;
import com.graduation.seniorabilityassessment.entity.Assess;
import com.graduation.seniorabilityassessment.entity.Rule;
import com.graduation.seniorabilityassessment.entity.Senior;
import com.graduation.seniorabilityassessment.entity.User;
import com.graduation.seniorabilityassessment.service.IAssessService;
import com.graduation.seniorabilityassessment.service.IRuleService;
import com.graduation.seniorabilityassessment.service.ISeniorService;
import com.graduation.seniorabilityassessment.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private IUserService userService;

    @Resource
    private ISeniorService seniorService;

    @Resource
    private IAssessService assessService;

    @Resource
    private IRuleService ruleService;
    List<Senior> seniorList = null;
    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
    @GetMapping("/age")
    public Result getAge() {
       seniorList = seniorService.list();
        int age_20_50 = 0;
        int age_50_55 = 0;
        int age_55_60 = 0;
        int age_60_65 = 0;
        int age_65_70 = 0;
        int age_70_after = 0;
        for(Senior senior :seniorList){
            int age = Integer.parseInt(senior.getAge());
            if(age>=70) age_70_after++;
            else if(age>=65) age_65_70++;
            else if(age>=60) age_60_65++;
            else if(age>=55) age_65_70++;
            else if(age>=50) age_50_55++;
            else if(age>=20) age_20_50++;
        }
        return Result.success(CollUtil.newArrayList(age_20_50, age_50_55, age_55_60, age_60_65, age_65_70, age_70_after));
    }
    @GetMapping("/gender")
    public Result getGender() {
        seniorList = seniorService.list();
        int man = 0;
        int woman = 0;
        for(Senior senior :seniorList){
            if(senior.getGender().equals("男")) man++;
            else woman++;
        }
        return Result.success(CollUtil.newArrayList(man,woman));
    }

    @GetMapping("/rank")
    public Result getRank() {
        List<Rule> ruleList = ruleService.list();
        List<String> rank_description = new ArrayList<>();
        int woman = 0;
        for(Rule rule:ruleList){
            rank_description.add(rule.getRank()+"级："+rule.getDescription());
        }
        List<Integer> primaryRankList = new ArrayList<>();
        List<Integer> ultimateRankList = new ArrayList<>();
        List<Assess> primaryRankEcharts = assessService.getPrimaryRankEcharts();
        List<Assess> ultimateRankEcharts = assessService.getUltimateRankEcharts();
        for(Assess assess:primaryRankEcharts){
            for(Rule rule:ruleList){
                if(rule.getRank().equals(assess.getPrimaryRank())){
                    primaryRankList.add(assess.getNumber());
                }
            }
        }
        for(Assess assess:ultimateRankEcharts){
            for(Rule rule:ruleList){
                if(rule.getRank().equals(assess.getUltimateRank())){
                    ultimateRankList.add(assess.getNumber());
                }
            }
        }
        List<Integer> totalRankNumberList = new ArrayList<>();
        for(int i = 0;i<primaryRankList.size();i++){
            totalRankNumberList.add(primaryRankList.get(i)+ultimateRankList.get(i));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rules",rank_description);
        map.put("primaryRank", primaryRankList);
        map.put("ultimateRank", ultimateRankList);
        map.put("totalRank", totalRankNumberList);
        return Result.success(map);
    }
    @GetMapping("/addSenior")
    public Result addSenior() {
        seniorList = seniorService.list();
        int man = 0;
        int woman = 0;
        for(Senior senior :seniorList){
            if(senior.getGender().equals("男")) man++;
            else woman++;
        }
        return Result.success(CollUtil.newArrayList(man,woman));
    }

}