package com.ycy.lucene.search;

import com.ycy.lucene.index.IndexReadDemo;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.Sort;

public class MultiQuery {

  public static void main(String[] args) {
    MultiPhraseQuery.Builder builder = new MultiPhraseQuery.Builder();
    builder.add(new Term("name","密"));
//    builder.add(new Term[]{new Term("name","八"),new Term("name","白")});
    builder.add(new Term("name","店"));

    builder.setSlop(100);
    MultiPhraseQuery multiPhraseQuery = builder.build();
    IndexReadDemo.search(multiPhraseQuery,10, Sort.RELEVANCE);
  }

}
