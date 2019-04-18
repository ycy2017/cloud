package com.ycy.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;
import org.apache.lucene.util.AttributeSource;

import java.io.IOException;

public class AnalyzerDemo extends Analyzer {
  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    return null;
  }
}

class Tokennizer extends Tokenizer {

  @Override
  public boolean incrementToken() throws IOException {
    return false;
  }
}

class TokenFilter extends org.apache.lucene.analysis.TokenFilter {

  /**
   * Construct a token stream filtering the given input.
   *
   * @param input
   */
  protected TokenFilter(TokenStream input) {
    super(input);
  }

  @Override
  public boolean incrementToken() throws IOException {
    return false;
  }
}


class MyAttribute extends AttributeImpl {

  @Override
  public void clear() {

  }

  @Override
  public void reflectWith(AttributeReflector reflector) {

  }

  @Override
  public void copyTo(AttributeImpl target) {

  }
}