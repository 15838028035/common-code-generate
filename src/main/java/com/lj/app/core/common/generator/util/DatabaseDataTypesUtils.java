package com.lj.app.core.common.generator.util;

import java.util.HashMap;

/**
 * 
 * 数据库类型工具类
 *
 */
public class DatabaseDataTypesUtils {
  private static final IntStringMap PREFERREDJAVATYPEFORSQLTYPE = new IntStringMap();

  public static boolean isFloatNumber(int sqlType, int size, int decimalDigits) {
    return isFloatNumber(getPreferredJavaType(sqlType, size, decimalDigits));
  }

  /**
   * 是否是float类型
   * @param javaType java类型
   * @return 是否
   */
  public static boolean isFloatNumber(String javaType) {
    if (javaType == null) {
      return false;
    }
    return (javaType.endsWith("Float")) || (javaType.endsWith("Double")) || (javaType.endsWith("BigDecimal"));
  }

  public static boolean isIntegerNumber(int sqlType, int size, int decimalDigits) {
    return isIntegerNumber(getPreferredJavaType(sqlType, size, decimalDigits));
  }

  public static boolean isIntegerNumber(String javaType) {
    return (javaType.endsWith("Long")) || (javaType.endsWith("Integer")) || (javaType.endsWith("Short"))
        || (javaType.endsWith("Byte"));
  }

  public static boolean isDate(int sqlType, int size, int decimalDigits) {
    return isDate(getPreferredJavaType(sqlType, size, decimalDigits));

  }

  /**
   * 是否是日期类型
   * @param javaType java类型
   * @return 是否
   */
  public static boolean isDate(String javaType) {
    if (javaType == null) {
      return false;
    }
    return (javaType.endsWith("Date")) || (javaType.endsWith("Timestamp")) || (javaType.endsWith("Time"));
  }

  public static boolean isString(int sqlType, int size, int decimalDigits) {
    return isString(getPreferredJavaType(sqlType, size, decimalDigits));
  }

  /**
   * 是否是string
   * @param javaType java类型
   * @return 是否
   */
  public static boolean isString(String javaType) {
    if (javaType == null) {
      return false;
    }
    return javaType.endsWith("String");
  }

  /**
   * 获得java引用类型
   * @param sqlType SQL类型
   * @param size 大小
   * @param decimalDigits 大小
   * @return 字符串
   */
  public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
    if (((sqlType == 3) || (sqlType == 2)) && (decimalDigits == 0)) {
      if (size == 1) {
        return "java.lang.Boolean";
      }
      if (size < 3) {
        return "java.lang.Byte";
      }
      if (size < 5) {
        return "java.lang.Short";
      }
      if (size < 10)  {
        return "java.lang.Integer";
      }
      if (size < 19) {
        return "java.lang.Long";
      }
      return "java.math.BigDecimal";
    }

    String result = PREFERREDJAVATYPEFORSQLTYPE.getString(sqlType);
    if (result == null) {
      result = "java.lang.Object";
    }
    return result;
  }

  static {
    PREFERREDJAVATYPEFORSQLTYPE.put(-6, "java.lang.Byte");
    PREFERREDJAVATYPEFORSQLTYPE.put(5, "java.lang.Short");
    PREFERREDJAVATYPEFORSQLTYPE.put(4, "java.lang.Integer");
    PREFERREDJAVATYPEFORSQLTYPE.put(-5, "java.lang.Long");
    PREFERREDJAVATYPEFORSQLTYPE.put(7, "java.lang.Float");
    PREFERREDJAVATYPEFORSQLTYPE.put(6, "java.lang.Double");
    PREFERREDJAVATYPEFORSQLTYPE.put(8, "java.lang.Double");
    PREFERREDJAVATYPEFORSQLTYPE.put(3, "java.math.BigDecimal");
    PREFERREDJAVATYPEFORSQLTYPE.put(2, "java.math.BigDecimal");
    PREFERREDJAVATYPEFORSQLTYPE.put(-7, "java.lang.Boolean");
    PREFERREDJAVATYPEFORSQLTYPE.put(16, "java.lang.Boolean");
    PREFERREDJAVATYPEFORSQLTYPE.put(1, "String");
    PREFERREDJAVATYPEFORSQLTYPE.put(12, "String");

    PREFERREDJAVATYPEFORSQLTYPE.put(-1, "String");
    PREFERREDJAVATYPEFORSQLTYPE.put(-2, "byte[]");
    PREFERREDJAVATYPEFORSQLTYPE.put(-3, "byte[]");
    PREFERREDJAVATYPEFORSQLTYPE.put(-4, "byte[]");
    PREFERREDJAVATYPEFORSQLTYPE.put(91, "java.sql.Date");
    PREFERREDJAVATYPEFORSQLTYPE.put(92, "java.sql.Time");
    PREFERREDJAVATYPEFORSQLTYPE.put(93, "java.sql.Timestamp");
    PREFERREDJAVATYPEFORSQLTYPE.put(2005, "java.sql.Clob");
    PREFERREDJAVATYPEFORSQLTYPE.put(2004, "java.sql.Blob");
    PREFERREDJAVATYPEFORSQLTYPE.put(2003, "java.sql.Array");
    PREFERREDJAVATYPEFORSQLTYPE.put(2006, "java.sql.Ref");
    PREFERREDJAVATYPEFORSQLTYPE.put(2002, "java.lang.Object");
    PREFERREDJAVATYPEFORSQLTYPE.put(2000, "java.lang.Object");
  }

  private static class IntStringMap extends HashMap {

    public String getString(int i) {
      return (String) get(new Integer(i));
    }

    public String[] getStrings(int i) {
      return (String[]) (String[]) get(new Integer(i));
    }

    public void put(int i, String s) {
      put(new Integer(i), s);
    }

    public void put(int i, String[] sa) {
      put(new Integer(i), sa);
    }

  }
}