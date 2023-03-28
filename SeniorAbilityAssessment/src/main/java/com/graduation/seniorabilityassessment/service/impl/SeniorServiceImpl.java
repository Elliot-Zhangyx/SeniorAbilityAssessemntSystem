package com.graduation.seniorabilityassessment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.seniorabilityassessment.entity.Senior;
import com.graduation.seniorabilityassessment.mapper.SeniorMapper;
import com.graduation.seniorabilityassessment.service.ISeniorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张雨轩
 * @since 2023-02-14
 */
@Service
public class SeniorServiceImpl extends ServiceImpl<SeniorMapper, Senior> implements ISeniorService {
    @Resource
    private SeniorMapper seniorMapper;
    @Override
    public String getAssessId() {
        Date time = new Date();
        int end = 9;
        int start = 0;
        int nums = 5;
        long timestamp = time.getTime();
        Random random = new Random();
        List<String> list = new ArrayList<>();
        while(list.size() != nums){
            int num = random.nextInt(end-start) + start;
            list.add(String.valueOf(num));
        }
        int i =0;
        int insertFlag = 13;
        StringBuilder sb = new StringBuilder(String.valueOf(timestamp));
        while(i<list.size()){
            int flag = random.nextInt(insertFlag);
            sb.insert(flag, list.get(i));
            i++;
            insertFlag++;
        }
        return "SAA"+sb.toString();
    }

    @Override
    public Senior getByUserId(Integer userId) {
        return seniorMapper.getByUserId(userId);
    }

    @Override
    public Page<Senior> findPage(Page<Senior> seniorPage, String assessedName, String assessedIdentityCard, String assessedGender) {
        return seniorMapper.findPage(seniorPage,assessedName,assessedIdentityCard,assessedGender);
    }

    @Override
    public Boolean updateIsAssess(Integer id) {
        return seniorMapper.updateIsAssess();
    }
}
