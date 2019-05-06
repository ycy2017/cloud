package com.cn.ycy.cache;

public class Test {

  public static void main(String[] args) {
    Test t = new Test();
    System.out.println(t.add(1, 2));// 在此行打上断点
    System.out.println(t.add(5, 7));
  }

  public int add(int a, int b) {
    return a + b;
  }

}
