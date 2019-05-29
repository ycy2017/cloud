package com.ycy.lucene.search;

import com.ycy.lucene.index.IndexReadDemo;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RangeQuery {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

  public void search01() {
    TermRangeQuery termRangeQuery = TermRangeQuery.newStringRange("name",
            "a", "b", false, false);
    IndexReadDemo.search(termRangeQuery, 10, null);
  }

  public void search02() {
    try {
      Date start = simpleDateFormat.parse("20170101");
      Date end = simpleDateFormat.parse("20171201");
      System.out.println(start.getTime() + " --- " + end.getTime());
      Query query = NumericDocValuesField.newRangeQuery("opendate", start.getTime(), end.getTime());

//      String content  = "opendate:[-9223372036854775808 TO 9223372036854775807]";
//      QueryParser queryParser = new QueryParser("opendate",null);
//      Query parse = queryParser.parse(content);
      IndexReadDemo.search(query, 10, null);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }


  public void search03() {
    Date start = null;
    try {
      start = simpleDateFormat.parse("20180101");
      Date end = simpleDateFormat.parse("20181201");
      TermRangeQuery termRangeQuery = TermRangeQuery.newStringRange("name",
              String.valueOf(start.getTime()), String.valueOf(end.getTime()), false, false);
      IndexReadDemo.search(termRangeQuery, 10, null);
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }

}
