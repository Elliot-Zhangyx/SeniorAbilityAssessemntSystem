package com.graduation.seniorabilityassessment.utils;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ParsePdfContent {

    public static void main(String[] args) {
        ParsePdfContent.tqPdfDataToTxt("C:\\Users\\lenovo\\Desktop\\test.pdf", "C:\\Users\\lenovo\\Desktop\\cxkxj_xzls.txt");
    }
    /**
     * 测试解析pdf的文档内容，并将解析内容输出到Txt文档中
     * 正式使用时，无需将解析后的内容写入文件，测试时，写入文件是为了方便查看解析后的原始内容
     * @param sourcePdfPath 要解析的pdf源文件
     * @param outFilePath 解析后的文本内容输出路径
     */
    public static void tqPdfDataToTxt(String sourcePdfPath,String outFilePath) {
        File file = new File(sourcePdfPath);
        if(file.exists()) {
            try {
                PDDocument doc = PDDocument.load(file);
                //正式使用时，此处注释开始--------------
                FileOutputStream fos = new FileOutputStream(outFilePath);
                Writer writer = new OutputStreamWriter(fos, "UTF-8");
                //正式使用时，此处注释结束--------------
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);// 排序
                stripper.setEndPage(1);//要解析的结束页数，此处我只解析第一页
                stripper.setWordSeparator("##");//单元格内容的分隔符号
                stripper.setLineSeparator("\n");//行与行之间的分隔符号
                String text = stripper.getText(doc);
                String[] rows = text.split("\n");
                //======从这里开始需要根据自己的实际解析内容做不同的处理start=======
                JSONArray list=new JSONArray();//存储解析数据的集合
                /**
                 * startFlag
                 * 解析内容开始的标志（大白话就是记录从哪一行开始是咱们的有用数据）
                 * 这里需要先解析一遍并输出到文件，才能具体知道
                 */
                String startFlag="Balance##Transaction";
                String endFlag="1/35";//结束标志
                int k=0;//开始获取数据标志
                boolean bb_End=false;//报表获取数据结束标志
                /**
                 * keys
                 * 标识字段的key
                 * date:记账日期
                 * currency:货币
                 * amount:交易金额
                 * balance:联机余额
                 * transactionType:交易摘要
                 * counterParty:对手信息
                 */
                String[] keys= {"date","currency","amount","balance","transactionType","counterParty"};
                for (int r=0;r<rows.length;r++) {
                    /**
                     * 这里根据自己的解析内容做不同的业务处理
                     * 我这里总共6列数据
                     */
                    JSONObject rowJson=new JSONObject();
                    rows[r]=rows[r].replaceAll(" ", "").replaceAll("\r", "");//此处我把每一行的空格和\r去掉
                    String[] split = rows[r].split("##");
                    if(k==1&&!bb_End) {//增值税表
                        if(rows[r].startsWith("本期应补（退）税额##22")||rows[r].startsWith("期末未缴查补税额##38")) {
                            bb_End=true;
                        }
                        //这里我判断是否完全匹配，是防止有换行的，这个需要根据实际情况做不同处理
                        if(split.length==keys.length) {//完全匹配列数(6)
                            for (int i=0;i<keys.length;i++) {
                                rowJson.put(keys[i], split[i]);
                            }
                            list.add(rowJson);
                            continue;
                        }
                        if(bb_End) {
                            break;//结束解析
                        }
                    }else {
                        if(rows[r].startsWith(startFlag)) {
                            k++;
                            continue;
                        }
                    }
                }
                //======从这里开始需要根据自己的实际解析内容做不同的处理end=======
                //正式使用时，此处注释开始--------------
                stripper.writeText(doc, writer);//写入本地文件(测试用)
                writer.close();
                //正式使用时，此处注释结束--------------
                doc.close();
                System.out.println("解析后格式化的JSON数据如下：");
                System.out.println(JSON.toJSONString(list));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("======================解析完成============================");
        }else {
            System.err.println("找不到要解析的pdf文件");
        }
    }
}