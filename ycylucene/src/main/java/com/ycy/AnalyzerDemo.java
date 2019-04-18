package com.ycy;

import com.ycy.com.ycy.log.LoggerDemo;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class AnalyzerDemo {

  public static void main(String[] args) {
//    String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!";
//    System.out.println(ToAnalysis.parse(str));

    /*StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (StackTraceElement stackTraceElement : stackTrace) {
      System.out.println(stackTraceElement);
    }

    System.out.println("==============");
    LoggerDemo loggerDemo = new LoggerDemo();
    loggerDemo.doTest();
    System.out.println("==============");
    LoggerDemo.doTest1();
    System.out.println("==============");*/

    //lucene 自带分词器
    printAnlayzer(new StandardAnalyzer(),"hello word!!!");

    ToAnalysis toAnalysis  = new ToAnalysis();
//    toAnalysis.

  }

  public static void printAnlayzer(Analyzer analyzer,String text){
    TokenStream tokenStream = analyzer.tokenStream("", text);
    CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
    try {
      tokenStream.reset();
      while (tokenStream.incrementToken()) {
        System.out.print(attribute.toString() + "|");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
