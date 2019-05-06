package com.ycy.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.Version;

import java.util.Iterator;

public class standardAnalyzerDemo {


  public static void main(String[] args) {
    try {
      // 要处理的文本
      String text = "lucene分析器使用分词器和过滤器构成一个“管道”，文本在流经这个管道后成为可以进入索引的最小单位，因此，一个标准的分析器有两个部分组成，一个是分词器tokenizer,它用于将文本按照规则切分为一个个可以进入索引的最小单位。另外一个是TokenFilter，它主要作用是对切出来的词进行进一步的处理（如去掉敏感词、英文大小写转换、单复数处理）等。lucene中的Tokenstram方法首先创建一个tokenizer对象处理Reader对象中的流式文本，然后利用TokenFilter对输出流进行过滤处理";

      Analyzer standardAnalyzer = new StandardAnalyzer();


      TokenStream tokenStream = standardAnalyzer.tokenStream("", text);
      tokenStream.reset();
      CharTermAttribute ch = tokenStream.addAttribute(CharTermAttribute.class);
      while (tokenStream.incrementToken()) {
        System.out.println(ch);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
  }
}

}
