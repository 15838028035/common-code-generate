package com.lj.app.core.common.generator.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class TestDataGenerator {
  public String getDBUnitTestData(String columnName, String javaType, int size) {
    int MAX_SIZE = 3;
    if (javaType.indexOf("Boolean") >= 0) {
      return "0";
    }
    if (javaType.indexOf("Timestamp") >= 0) {
      return new Timestamp(System.currentTimeMillis()).toString();
    }
    if (javaType.indexOf("java.sql.Date") >= 0) {
      return new Date(System.currentTimeMillis()).toString();
    }
    if (javaType.indexOf("java.sql.Time") >= 0) {
      return new Time(System.currentTimeMillis()).toString();
    }
    if (javaType.indexOf("String") >= 0) {
      if (size > columnName.length()) {
        int tempSize = Math.min(size - columnName.length(), MAX_SIZE);
        return columnName + StringHelper.randomNumeric(tempSize);
      }
      return StringHelper.randomNumeric(Math.min(size, MAX_SIZE));
    }
    if (isNumberType(javaType)) {
      return StringHelper.randomNumeric(Math.min(size, MAX_SIZE));
    }
    return "";
  }

  private boolean isNumberType(String javaType) {
    javaType = javaType.toLowerCase();

    return (javaType.indexOf("byte") >= 0) || (javaType.indexOf("short") >= 0) || (javaType.indexOf("integer") >= 0)
        || (javaType.indexOf("int") >= 0) || (javaType.indexOf("long") >= 0) || (javaType.indexOf("double") >= 0)
        || (javaType.indexOf("bigdecimal") >= 0) || (javaType.indexOf("float") >= 0);
  }
}