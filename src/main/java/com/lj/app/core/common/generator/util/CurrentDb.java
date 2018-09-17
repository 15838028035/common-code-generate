package com.lj.app.core.common.generator.util;

/**
 * 数据库类型工具类
 *
 */
public class CurrentDb {
  public static final String ORACLE = "oracle";
  public static final String SYBASE = "sybase";
  public static final String DB2 = "db2";
  public static final String SQLSERVER = "sqlserver";
  public static final String MYSQL = "mysql";

  private String driverClass;

  /**
   * 获取数据库类型
   * @return 数据库类型
   */
  public String getCurrentDb() {
    String db = "";
    if (this.driverClass.contains("oracle")) {
      db = "oracle";
    }
    if (this.driverClass.contains("sybase")) {
      db = "sybase";
    }
    if (this.driverClass.contains("db2")) {
      db = "db2";
    }
    if (this.driverClass.contains("sqlserver")) {
      db = "sqlserver";
    }
    if (this.driverClass.contains("mysql")) {
      db = "mysql";
    }

    return db;
  }

  public String getDriverClass() {
    return this.driverClass;
  }

  public void setDriverClass(String driverClass) {
    this.driverClass = driverClass;
  }
}