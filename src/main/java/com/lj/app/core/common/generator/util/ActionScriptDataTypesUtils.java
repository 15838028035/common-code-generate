package com.lj.app.core.common.generator.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * JAVA类型转换工具类
 *
 */
public class ActionScriptDataTypesUtils {
  private static final Map<String, String> preferredAsTypeForJavaType = new HashMap();

  /**
   * 获得引用类型
   * @param javaType java类型
   * @return 引用类型
   */
  public static String getPreferredAsType(String javaType) {
    String result = (String) preferredAsTypeForJavaType.get(javaType);
    if (result == null) {
      result = javaType;
    }
    return result;
  }

  static {
    preferredAsTypeForJavaType.put("java.lang.Short", "Number");
    preferredAsTypeForJavaType.put("short", "Number");
    preferredAsTypeForJavaType.put("java.lang.Integer", "Number");
    preferredAsTypeForJavaType.put("int", "Number");
    preferredAsTypeForJavaType.put("java.lang.Long", "Number");
    preferredAsTypeForJavaType.put("long", "Number");
    preferredAsTypeForJavaType.put("java.lang.Float", "Number");
    preferredAsTypeForJavaType.put("float", "Number");
    preferredAsTypeForJavaType.put("java.lang.Double", "Number");
    preferredAsTypeForJavaType.put("float", "Number");
    preferredAsTypeForJavaType.put("java.math.BigDecimal", "Number");
    preferredAsTypeForJavaType.put("java.lang.Byte", "Number");
    preferredAsTypeForJavaType.put("byte", "Number");

    preferredAsTypeForJavaType.put("java.lang.Boolean", "Boolean");
    preferredAsTypeForJavaType.put("boolen", "Boolean");

    preferredAsTypeForJavaType.put("char", "String");
    preferredAsTypeForJavaType.put("char[]", "String");
    preferredAsTypeForJavaType.put("java.lang.String", "String");
    preferredAsTypeForJavaType.put("java.sql.Clob", "String");

    preferredAsTypeForJavaType.put("byte[]", "flash.utils.ByteArray");
    preferredAsTypeForJavaType.put("java.sql.Blob", "flash.utils.ByteArray");
    preferredAsTypeForJavaType.put("java.sql.Array", "Array");
    preferredAsTypeForJavaType.put("java.lang.reflect.Array", "Array");
    preferredAsTypeForJavaType.put("java.util.Collection", "mx.collections.ArrayCollection");
    preferredAsTypeForJavaType.put("java.util.List", "mx.collections.ArrayCollection");
    preferredAsTypeForJavaType.put("java.util.ArrayList", "mx.collections.ArrayCollection");

    preferredAsTypeForJavaType.put("java.util.Set", "Object");
    preferredAsTypeForJavaType.put("java.util.HashSet", "Object");
    preferredAsTypeForJavaType.put("java.util.Map", "Object");
    preferredAsTypeForJavaType.put("java.util.HashMap", "Object");

    preferredAsTypeForJavaType.put("java.sql.Date", "Date");
    preferredAsTypeForJavaType.put("java.sql.Time", "Date");
    preferredAsTypeForJavaType.put("java.util.Date", "Date");
    preferredAsTypeForJavaType.put("java.sql.Timestamp", "Date");
  }
}