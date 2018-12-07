package com.digital.dance.commons.utils;

public class AlgorithmUtil
{
  public static boolean binaryTherein(int unit, int gather)
  {
    return (unit & gather) == unit;
  }

  public static boolean binaryIntersection(long unit, long gather)
  {
    return (unit & gather) != 0L;
  }

  public static int binaryMerge(int unit, int gather)
  {
    return unit | gather;
  }

  public static int binaryRid(int unit, int gather)
  {
    return gather - (unit & gather);
  }
}
