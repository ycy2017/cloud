package com.ycy;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;
import org.apache.lucene.search.LeafFieldComparator;

import java.io.IOException;

public class PlaceNameSortComparatorSource extends FieldComparatorSource {
  private String userLocation;

  public PlaceNameSortComparatorSource(String userLocation) {
    this.userLocation = userLocation;
  }

  @Override
  public FieldComparator newComparator(String fieldname, int numHits,
                                       int sortPos, boolean reversed) {
    // TODO Auto-generated method stub
    return new PlaceComparator(numHits, fieldname, userLocation);
  }


  class PlaceComparator extends FieldComparator {
    private final String[] values;
    private String[] currentReaderValues;
    private final String field;
    private String bottom;
    private String userLocation;


    PlaceComparator(int numHits, String field, String userLocation) {
      values = new String[numHits];
      this.field = field;
      this.userLocation = userLocation;
    }

    @Override
    public int compare(int slot1, int slot2) {

      int cmpValue = 0;
      final String v1 = values[slot1];
      final String v2 = values[slot2];

      if (v1.indexOf(userLocation) != -1) {
        cmpValue = -999999999;
      }
      if (v2.indexOf(userLocation) != -1) {
        cmpValue = -999999999;
      } else {
        cmpValue = 999999999;
      }

      System.out.println(slot1 + "-" + slot2);
      System.out.println(v1 + "-" + v2);
      System.out.println("cmpValue=" + cmpValue);
      return cmpValue;
    }

    @Override
    public void setTopValue(Object o) {

    }

    @Override
    public Comparable value(int slot) {
      return values[slot];
    }

    @Override
    public LeafFieldComparator getLeafComparator(LeafReaderContext leafReaderContext) throws IOException {
      return null;
    }

  }

}
