package com.lj.app.core.common.generator.provider.db.model;

import com.lj.app.core.common.generator.GeneratorProperties;
import com.lj.app.core.common.generator.util.ActionScriptDataTypesUtils;
import com.lj.app.core.common.generator.util.DatabaseDataTypesUtils;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.JavaPrimitiveTypeMapping;
import com.lj.app.core.common.generator.util.JdbcType;
import com.lj.app.core.common.generator.util.StringHelper;
import com.lj.app.core.common.generator.util.TestDataGenerator;

public class Column {
  private final Table _table;
  private final int _sqlType;
  private final String _sqlTypeName;
  private final String _sqlName;
  private boolean _isPk;
  private boolean _isFk;
  private String fkTableName;
  private final int _size;
  private final int _decimalDigits;
  private final boolean _isNullable;
  private final boolean _isIndexed;
  private final boolean _isUnique;
  private final String _defaultValue;
  private final String _remarks;

  private String javaType;

  public Column(Table table, int sqlType, String sqlTypeName, String sqlName, int size, int decimalDigits, boolean isPk,
      boolean isFk, String fkTableName, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue,
      String remarks) {
    this._table = table;
    this._sqlType = sqlType;
    this._sqlName = sqlName;
    this._sqlTypeName = sqlTypeName;
    this._size = size;
    this._decimalDigits = decimalDigits;
    this._isPk = isPk;
    this._isNullable = isNullable;
    this._isIndexed = isIndexed;
    this._isUnique = isUnique;
    this._defaultValue = defaultValue;
    this._remarks = remarks;
    this._isFk = isFk;
    this.fkTableName = fkTableName;

    GLogger.debug(sqlName + " isPk -> " + this._isPk);
    initOtherProperties();
  }

  public int getSqlType() {
    return this._sqlType;
  }

  public Table getTable() {
    return this._table;
  }

  public int getSize() {
    return this._size;
  }

  public int getDecimalDigits() {
    return this._decimalDigits;
  }

  public String getSqlTypeName() {
    return this._sqlTypeName;
  }

  public String getSqlName() {
    return this._sqlName;
  }

  public String getUnderscoreName() {
    return getSqlName().toLowerCase();
  }

  public boolean isPk() {
    return this._isPk;
  }

  public boolean isFk() {
    return this._isFk;
  }

  public final boolean isNullable() {
    return this._isNullable;
  }

  public final boolean isIndexed() {
    return this._isIndexed;
  }

  public boolean isUnique() {
    return this._isUnique;
  }

  public final String getDefaultValue() {
    return this._defaultValue;
  }

  public final String getRemarks() {
    return StringHelper.trimBlank(this._remarks);
  }

  public int hashCode() {
    return (getTable().getSqlName() + "#" + getSqlName()).hashCode();
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if ((o instanceof Column)) {
      Column other = (Column) o;
      if (getSqlName().equals(other.getSqlName())) {
        return true;
      }
    }
    return false;
  }

  public String toString() {
    return getSqlName();
  }

  protected final String prefsPrefix() {
    return "tables/" + getTable().getSqlName() + "/columns/" + getSqlName();
  }

  void setFk(boolean flag) {
    this._isFk = flag;
  }

  public String getColumnName() {
    return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(getSqlName()));
  }

  public String getColumnNameFirstLower() {
    return StringHelper.uncapitalize(getColumnName());
  }

  public String getColumnNameFirstUpper() {
    return StringHelper.capitalize(getColumnName());
  }

  public String getColumnNameLowerCase() {
    return getColumnName().toLowerCase();
  }

  /** @deprecated */
  public String getColumnNameLower() {
    return getColumnNameFirstLower();
  }

  public String getJdbcSqlTypeName() {
    String result = JdbcType.getJdbcSqlTypeName(getSqlType());

    return result;
  }

  public String getColumnAlias() {
    return StringHelper.emptyIf(getRemarks(), getColumnNameFirstLower());
  }

  public String getConstantName() {
    return StringHelper.toUnderscoreName(getSqlName()).toUpperCase();
  }

  public boolean getIsNotIdOrVersionField() {
    return !isPk();
  }

  public String getValidateString() {
    String result = getNoRequiredValidateString();
    if (!isNullable()) {
      result = "required " + result;
    }
    return result;
  }

  public String getNoRequiredValidateString() {
    String result = "";
    if (getSqlName().indexOf("mail") >= 0) {
      result = result + "validate-email ";
    }
    if (DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits())) {
      result = result + "validate-number ";
    }
    if (DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits())) {
      result = result + "validate-integer ";
      if (getJavaType().indexOf("Short") >= 0)
        result = result + "max-value-32767";
      else if (getJavaType().indexOf("Integer") >= 0)
        result = result + "max-value-2147483647";
      else if (getJavaType().indexOf("Byte") >= 0) {
        result = result + "max-value-127";
      }

    }

    return result;
  }

  public boolean getIsStringColumn() {
    return DatabaseDataTypesUtils.isString(getSqlType(), getSize(), getDecimalDigits());
  }

  public boolean getIsDateTimeColumn() {
    return DatabaseDataTypesUtils.isDate(getSqlType(), getSize(), getDecimalDigits());
  }

  public boolean getIsNumberColumn() {
    return (DatabaseDataTypesUtils.isFloatNumber(getSqlType(), getSize(), getDecimalDigits()))
        || (DatabaseDataTypesUtils.isIntegerNumber(getSqlType(), getSize(), getDecimalDigits()));
  }

  public boolean isHtmlHidden() {
    return (isPk()) && (this._table.isSingleId());
  }

  public String getJavaType() {
    /*
     * String normalJdbcJavaType = DatabaseDataTypesUtils .getPreferredJavaType(getSqlType(), getSize(),
     * getDecimalDigits()); javaType = GeneratorProperties.getProperty( "java_typemapping." + normalJdbcJavaType,
     * normalJdbcJavaType) .trim();
     */

    return javaType;
  }

  /**
   * 得到简短的javaType的名称，如com.company.model.UserInfo,将返回 UserInfo
   * 
   * @return
   */
  public String getSimpleJavaType() {
    return StringHelper.getJavaClassSimpleName(getJavaType());
  }

  /**
   * 得到尽可能简短的javaType的名称，如果是java.lang.String,将返回String, 如com.company.model.UserInfo,将返回 com.company.model.UserInfo
   * 
   * @return
   */
  public String getPossibleShortJavaType() {
    if (getJavaType().startsWith("java.lang.")) {
      return getSimpleJavaType();
    } else {
      return getJavaType();
    }
  }

  public String getAsType() {
    return ActionScriptDataTypesUtils.getPreferredAsType(getJavaType());
  }

  public String getTestData() {
    return new TestDataGenerator().getDBUnitTestData(getColumnName(), getJavaType(), getSize());
  }

  /** nullValue for ibatis sqlmap: <result property="age" column="age" nullValue="0" /> */
  public String getNullValue() {
    // System.out.println("getNullValue============================="+(JavaPrimitiveTypeMapping.getDefaultValue(getJavaType())));
    return JavaPrimitiveTypeMapping.getDefaultValue(getJavaType());
  }

  public boolean isHasNullValue() {
    // System.out.println("getJavaType============================="+getJavaType());
    // System.out.println("isHasNullValue============================="+(JavaPrimitiveTypeMapping.getWrapperTypeOrNull(getJavaType())
    // != null));
    return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(getJavaType()) != null;
  }

  private void initOtherProperties() {
    String normalJdbcJavaType = DatabaseDataTypesUtils.getPreferredJavaType(getSqlType(), getSize(),
        getDecimalDigits());
    javaType = GeneratorProperties.getProperty("java_typemapping." + normalJdbcJavaType, normalJdbcJavaType).trim();
  }

  public String getFkTableName() {
    return this.fkTableName;
  }

  public void setFkTableName(String fkTableName) {
    this.fkTableName = fkTableName;
  }
}