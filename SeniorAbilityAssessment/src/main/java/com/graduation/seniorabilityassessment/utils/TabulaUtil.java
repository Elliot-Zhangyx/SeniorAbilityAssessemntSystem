package com.graduation.seniorabilityassessment.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.graduation.seniorabilityassessment.entity.Module;
import com.graduation.seniorabilityassessment.entity.Option;
import com.graduation.seniorabilityassessment.entity.Subject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.CommandLineApp;

import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class TabulaUtil {

    public static List<Module> parsePdf(File file) throws ParseException {
        //-f导出格式,默认CSV  (一定要大写)
        //-p 指导出哪页,all是所有
        //path　D:\\1xx.pdf
        //-l 强制使用点阵模式提取PDF　（关键在于这儿）
        String[] argsa = new String[]{"-f=JSON","-p=all","-l"};
        //CommandLineApp.main(argsa);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(CommandLineApp.buildOptions(), argsa);
        StringBuilder stringBuilder = new StringBuilder();
        new MyCommandLineApp(stringBuilder, cmd).extractTables(cmd,file);
//        System.out.println("============");
//        System.out.println(stringBuilder.toString());


        //解析tabula读取pdf表格,将返回的数据转成jsonArray
        JSONArray jsonArray = JSON.parseArray(stringBuilder.toString());
        List<Module> moduleList = new ArrayList<>();
        List<Subject> subjectList = new ArrayList<>();
        List<Option> optionList = new ArrayList<>();

        int moduleId = 0;
        int subjectId = 0;
        int flag =  0;
        int flag_shutdowmFor = 1;
        for (int i = 0; i <jsonArray.size() ; i++) {
            //获取每个页
            JSONObject jsonPage = jsonArray.getJSONObject(i);
            //获取每页中的data
            JSONArray dataArr = jsonPage.getJSONArray("data");
            //遍历data中的每个单元格
            for (int k = 0; k < dataArr.size() ; k++) {
                //遍历data中的每一条,也就是单元格中的每一行
                JSONArray dataD = dataArr.getJSONArray(k);
                Module module = new Module();
                Subject subject = new Subject();
                Option option = new Option();
                if(flag_shutdowmFor == 1){
                    for(int l = 0;l<dataD.size();l++){
                        if(dataD.getJSONObject(l).get("text").toString().contains("老年人能力评估表")){
                            flag_shutdowmFor = 0;
                            k++;
                            dataD= dataArr.getJSONArray(k);
                            moduleId++;
                            flag = 1;
                            break;
                        }
                    }
                }

                for(int l = 0;l<dataD.size();l++){
                    if(dataD.getJSONObject(l).get("text").toString().contains("老年人能力总得分") || dataD.getJSONObject(l).get("text").toString().contains("指标")){
                        flag = 0;
                    }
                }
                if(flag == 1) {
                    if (dataD.size() == 1) {
                        moduleId++;
                        module.setTitle(dataD.getJSONObject(0).get("text").toString().replace("表",""));
                        module.setId(moduleId);
                        moduleList.add(module);
                    } else if (dataD.size() == 2 && (!dataD.getJSONObject(0).get("text").toString().equals("")) && (dataD.getJSONObject(1).get("text").toString().equals(""))) {
                        if (Character.isDigit(dataD.getJSONObject(0).get("text").toString().charAt(0))) {
                            option.setSubjectOption(dataD.getJSONObject(0).get("text").toString());
                            option.setSubjectId(subjectId);
                            optionList.add(option);
                        } else {
                            subjectId++;
                            subject.setModuleId(moduleId);
                            subject.setSubjectUid(subjectId);
                            if (dataD.getJSONObject(0).get("text").toString().contains("注：")) {
                                String[] attentions = dataD.getJSONObject(dataD.size() - 1).get("text").toString().split("注：");
                                subject.setSubjectAttention(attentions[attentions.length - 1]);
                                subject.setSubjectTitle(attentions[0]);
                            } else {
                                subject.setSubjectTitle(dataD.getJSONObject(0).get("text").toString());
                            }
                            subjectList.add(subject);
                        }
                    } else if (dataD.size() == 2 && (dataD.getJSONObject(0).get("text").toString().equals("") || dataD.getJSONObject(0).get("text").toString().equals("□分")) && (!dataD.getJSONObject(1).get("text").toString().equals(""))) {
                        option.setSubjectOption(dataD.getJSONObject(1).get("text").toString());
                        option.setSubjectId(subjectId);
                        optionList.add(option);
                    }
                }
            }

        }
        for(Subject subject:subjectList){
            List<Option> options = new ArrayList<>();
            for(Option option:optionList){
                if(subject.getSubjectUid().equals(option.getSubjectId())){
                    options.add(option);
                }
            }
            subject.setOptionList(options);
        }
        for(Module module:moduleList){
            List<Subject> subjects = new ArrayList<>();
            for(Subject subject:subjectList) {
                if (subject.getModuleId().equals(module.getId())) {
                    subjects.add(subject);
                }
            }
            module.setSubjectList(subjects);
        }
        return moduleList;
    }
    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
