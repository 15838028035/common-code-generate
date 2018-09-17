package com.lj.app.core.common.generator.util;

/**
 * 
 * JDBC类型
 *
 */
public enum JdbcType {
  BIT(-7), TINYINT(-6), SMALLINT(5), INTEGER(4), BIGINT(-5), FLOAT(6), REAL(7), DOUBLE(8), NUMERIC(2), DECIMAL(3), CHAR(
      1), VARCHAR(12), LONGVARCHAR(-1), DATE(91), TIME(92), TIMESTAMP(93), BINARY(-2), VARBINARY(-3), LONGVARBINARY(
          -4), NULL(0), OTHER(1111), BLOB(2004), CLOB(
              2005), BOOLEAN(16), CURSOR(-10), UNDEFINED(-2147482648), NVARCHAR(-9), NCHAR(-15), NCLOB(2011);

  public  final int typeCode;

  private JdbcType(int code) {
    this.typeCode = code;
  }

  /**
   * 根据类型获得jdbc类型
   * @param code key 
   * @return 值
   */
  public static String getJdbcSqlTypeName(int code) {
    for (JdbcType type : values()) {
      if (type.typeCode == code) {
        return type.name();
      }
    }
    return null;
  }
}