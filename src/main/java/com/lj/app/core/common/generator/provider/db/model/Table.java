package com.lj.app.core.common.generator.provider.db.model;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.util.StringHelper;

public class Table {
  String sqlName;
  String remarks;
  String customClassName;
  private String ownerSynonymName = null;
  Set<Column> columns = new LinkedHashSet();
  List<Column> primaryKeyColumns = new ArrayList();
  List<Column> foreignKeysColumns = new ArrayList();

  String catalog = DbTableFactory.getInstance().getCatalog();
  String schema = DbTableFactory.getInstance().getSchema();
  private ForeignKeys exportedKeys;
  private ForeignKeys importedKeys;
  public static final String PKTABLE_NAME = "PKTABLE_NAME";
  public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
  public static final String FKTABLE_NAME = "FKTABLE_NAME";
  public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
  public static final String KEY_SEQ = "KEY_SEQ";

  public Set<Column> getColumns() {
    return this.columns;
  }

  public void setColumns(Set columns) {
    this.columns = columns;
  }

  public String getOwnerSynonymName() {
    return this.ownerSynonymName;
  }

  public void setOwnerSynonymName(String ownerSynonymName) {
    this.ownerSynonymName = ownerSynonymName;
  }

  public List<Column> getPrimaryKeyColumns() {
    return this.primaryKeyColumns;
  }

  public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
    this.primaryKeyColumns = primaryKeyColumns;
  }

  public void setForeignKeyColumns(List<Column> foreignKeysColumns) {
    this.foreignKeysColumns = foreignKeysColumns;
  }

  public String getSqlName() {
    return this.sqlName;
  }

  public void setSqlName(String sqlName) {
    this.sqlName = sqlName;
  }

  public String getRemarks() {
    return StringHelper.trimBlank(this.remarks);
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public void addColumn(Column column) {
    this.columns.add(column);
  }

  public void setClassName(String customClassName) {
    this.customClassName = customClassName;
  }

  public String getClassName() {
    if (StringHelper.isBlank(customClassName)) {
      return StringHelper.toJavaClassName(getSqlName());
    } else {
      return customClassName;
    }
  }

  public String getTableAlias() {
    return StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getClassName()));
  }

  public String getClassNameLowerCase() {
    return getClassName().toLowerCase();
  }

  public String getUnderscoreName() {
    return getSqlName().toLowerCase();
  }

  public String getClassNameFirstLower() {
    return StringHelper.uncapitalize(getClassName());
  }

  public String getConstantName() {
    return StringHelper.toUnderscoreName(getClassName()).toUpperCase();
  }

  public boolean isSingleId() {
    return getPkCount() == 1;
  }

  public boolean isCompositeId() {
    return getPkCount() > 1;
  }

  public boolean isNotCompositeId() {
    return !isCompositeId();
  }

  public int getPkCount() {
    int pkCount = 0;
    for (Column c : this.columns) {
      if (c.isPk()) {
        pkCount++;
      }
    }
    return pkCount;
  }

  /** @deprecated */
  public List getCompositeIdColumns() {
    return getPkColumns();
  }

  public List getPkColumns() {
    List results = new ArrayList();
    for (Column c : getColumns()) {
      if (c.isPk())
        results.add(c);
    }
    return results;
  }

  public List getNotPkColumns() {
    List results = new ArrayList();
    for (Column c : getColumns()) {
      if (!c.isPk())
        results.add(c);
    }
    return results;
  }

  public Column getIdColumn() {
    for (Column c : getColumns()) {
      if (c.isPk())
        return c;
    }
    return null;
  }

  public void initImportedKeys(DatabaseMetaData dbmd) throws SQLException {
    ResultSet fkeys = dbmd.getImportedKeys(this.catalog, this.schema, this.sqlName);

    while (fkeys.next()) {
      String pktable = fkeys.getString("PKTABLE_NAME");
      String pkcol = fkeys.getString("PKCOLUMN_NAME");
      String fktable = fkeys.getString("FKTABLE_NAME");
      String fkcol = fkeys.getString("FKCOLUMN_NAME");
      String seq = fkeys.getString("KEY_SEQ");
      Integer iseq = new Integer(seq);
      getImportedKeys().addForeignKey(pktable, pkcol, fkcol, iseq);
    }
    fkeys.close();
  }

  public void initExportedKeys(DatabaseMetaData dbmd) throws SQLException {
    ResultSet fkeys = dbmd.getExportedKeys(this.catalog, this.schema, this.sqlName);

    while (fkeys.next()) {
      String pktable = fkeys.getString("PKTABLE_NAME");
      String pkcol = fkeys.getString("PKCOLUMN_NAME");
      String fktable = fkeys.getString("FKTABLE_NAME");
      String fkcol = fkeys.getString("FKCOLUMN_NAME");
      String seq = fkeys.getString("KEY_SEQ");
      Integer iseq = new Integer(seq);
      getExportedKeys().addForeignKey(fktable, fkcol, pkcol, iseq);
    }
    fkeys.close();
  }

  public ForeignKeys getExportedKeys() {
    if (this.exportedKeys == null) {
      this.exportedKeys = new ForeignKeys(this);
    }
    return this.exportedKeys;
  }

  public ForeignKeys getImportedKeys() {
    if (this.importedKeys == null) {
      this.importedKeys = new ForeignKeys(this);
    }
    return this.importedKeys;
  }

  public String toString() {
    return "Database Table:" + getSqlName() + " to ClassName:" + getClassName();
  }
}