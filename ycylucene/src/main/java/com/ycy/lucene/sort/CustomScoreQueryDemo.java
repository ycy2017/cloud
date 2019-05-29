package com.ycy.lucene.sort;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.search.Query;

import java.io.IOException;

/**
 * 自定义评分
 * 对所有召回的文档打分
 */
public class CustomScoreQueryDemo extends CustomScoreQuery {
  public CustomScoreQueryDemo(Query subQuery) {
    super(subQuery);
  }

  public CustomScoreQueryDemo(Query subQuery, FunctionQuery scoringQuery) {
    super(subQuery, scoringQuery);
  }

  public CustomScoreQueryDemo(Query subQuery, FunctionQuery... scoringQueries) {
    super(subQuery, scoringQueries);
  }


  @Override
  protected CustomScoreProvider getCustomScoreProvider(LeafReaderContext context) throws IOException {
    return new CustomScoreProviderDemo(context);
  }


  class CustomScoreProviderDemo extends CustomScoreProvider {

    /**
     * Creates a new instance of the provider class for the given {@link }.
     *
     * @param context
     */
    public CustomScoreProviderDemo(LeafReaderContext context) {
      super(context);
    }


    @Override
    public float customScore(int doc, float subQueryScore, float valSrcScore) throws IOException {
      System.out.println(doc + " 评分 :" + subQueryScore);
      System.out.println(doc + " 评分  :" + valSrcScore);
      System.out.println(doc + " 评分  :" + valSrcScore);
      return super.customScore(doc, subQueryScore, valSrcScore);
    }
  }

}
