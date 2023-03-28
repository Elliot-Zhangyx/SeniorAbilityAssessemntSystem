package com.graduation.seniorabilityassessment.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
public class WordAnalyzer {

    public static void main(String[] args) throws IOException {
        splitWordsByChinese("0分：昏睡，一般的外界刺激不能使其觉醒，给予较强烈的刺激时可有短时的意识清醒，醒后可简短回答提问，当刺激减弱后又很快进入睡眠状态昏迷：意识丧失，随意运动丧失，呼之不应，对一般刺激全无反应。");
        splitWordsByNumber("4分：清楚理解");
    }
    public static List<String> splitWordsByChinese(String content){
        content = CharacterFilter.chineseFilter(content);
        List<String> stop_words = new ArrayList<>();
        try {
            String basePath = "C:\\Users\\lenovo\\Downloads\\";
            FileReader fr = new FileReader(basePath + "stopWords.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                stop_words.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> result = segmenter.sentenceProcess(content);
        System.out.println("没有过滤停用词======" + result);
        result = result.stream().map(String::trim).filter(o -> !stop_words.contains(o)).collect(Collectors.toList());
        System.out.println("过滤停用词=========" + result);
        return result;
    }
    public static List<String> splitWordsByNumber(String content){
        content = CharacterFilter.numberFilter(content);
        List<String> stop_words = new ArrayList<>();
        try {
            String basePath = "C:\\Users\\lenovo\\Downloads\\";
            FileReader fr = new FileReader(basePath + "stopWords.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                stop_words.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> result = segmenter.sentenceProcess(content);
        System.out.println("没有过滤停用词======" + result);
        return result;
    }
}
