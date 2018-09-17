package com.lj.app.core.common.generator.provider.db.model;

import java.util.List;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.ListHashtable;

public class ForeignKey {
  protected String relationShip = null;
  protected String firstRelation = null;
  protected String secondRelation = null;
  protected Table parentTable;
  protected String tableName;
  protected ListHashtable columns;
  protected ListHashtable parentColumns;

  public ForeignKey(Table aTable, String tblName) {
    this.parentTable = aTable;
    this.tableName = tblName;
    this.columns = new ListHashtable();
    this.parentColumns = new ListHashtable();
  }

  public String getTableName() {
    return this.tableName;
  }

  public String getParentTableName() {
    return this.parentTable.getSqlName();
  }

  public void addColumn(String col, String parentCol, Integer seq) {
    this.columns.put(seq, col);
    this.parentColumns.put(seq, parentCol);
  }

  public String getColumn(String parentCol) {
    Object key = this.parentColumns.getKeyForValue(parentCol);
    String col = (String) this.columns.get(key);

    return col;
  }

  public ListHashtable getColumns() {
    return this.columns;
  }

  private void initRelationship() {
    this.firstRelation = "";
    this.secondRelation = "";
    Table foreignTable = null;
    try {
      foreignTable = DbTableFactory.getInstance().getTable(this.tableName);
    } catch (Exception e) {
      GLogger.error("initRelationship",e);
    }
    List parentPrimaryKeys = this.parentTable.getPrimaryKeyColumns();
    List foreignPrimaryKeys = foreignTable.getPrimaryKeyColumns();

    if (hasAllPrimaryKeys(parentPrimaryKeys, this.parentColumns))
      this.firstRelation = "one";
    else {
      this.firstRelation = "many";
    }
    if (hasAllPrimaryKeys(foreignPrimaryKeys, this.columns))
      this.secondRelation = "one";
    else {
      this.secondRelation = "many";
    }
    this.relationShip = (this.firstRelation + "-to-" + this.secondRelation);
  }

  private boolean hasAllPrimaryKeys(List pkeys, ListHashtable cols) {
    boolean hasAll = true;

    int numKeys = pkeys.size();
    if (numKeys != cols.size()) {
      return false;
    }
    for (int i = 0; i < numKeys; i++) {
      Column col = (Column) pkeys.get(i);
      String colname = col.getColumnName();
      if (!cols.contains(colname)) {
        return false;
      }
    }
    return hasAll;
  }

  public boolean isParentColumnsFromPrimaryKey() {
    boolean isFrom = true;
    List keys = this.parentTable.getPrimaryKeyColumns();
    int numKeys = getParentColumns().size();
    for (int i = 0; i < numKeys; i++) {
      String pcol = (String) getParentColumns().getOrderedValue(i);
      if (!primaryKeyHasColumn(pcol)) {
        isFrom = false;
        break;
      }
    }
    return isFrom;
  }

  private boolean primaryKeyHasColumn(String aColumn) {
    boolean isFound = false;
    int numKeys = this.parentTable.getPrimaryKeyColumns().size();
    for (int i = 0; i < numKeys; i++) {
      Column sqlCol = (Column) this.parentTable.getPrimaryKeyColumns().get(i);
      String colname = sqlCol.getColumnName();
      if (colname.equals(aColumn)) {
        isFound = true;
        break;
      }
    }
    return isFound;
  }

  public boolean getHasImportedKeyColumn(String aColumn) {
    boolean isFound = false;
    List cols = getColumns().getOrderedValues();
    int numCols = cols.size();
    for (int i = 0; i < numCols; i++) {
      String col = (String) cols.get(i);
      if (col.equals(aColumn)) {
        isFound = true;
        break;
      }
    }
    return isFound;
  }

  public String getFirstRelation() {
    if (this.firstRelation == null)
      initRelationship();
    return this.firstRelation;
  }

  public Table getSqlTable() {
    Table table = null;
    try {
      table = DbTableFactory.getInstance().getTable(this.tableName);
    } catch (Exception e) {
      GLogger.error("getSqlTable",e);
    }
    return table;
  }

  public Table getParentTable() {
    return this.parentTable;
  }

  public String getRelationShip() {
    if (this.relationShip == null)
      initRelationship();
    return this.relationShip;
  }

  public String getSecondRelation() {
    if (this.secondRelation == null)
      initRelationship();
    return this.secondRelation;
  }

  public ListHashtable getParentColumns() {
    return this.parentColumns;
  }

  public boolean getHasImportedKeyParentColumn(String aColumn) {
    boolean isFound = false;
    List cols = getParentColumns().getOrderedValues();
    int numCols = cols.size();
    for (int i = 0; i < numCols; i++) {
      String col = (String) cols.get(i);
      if (col.equals(aColumn)) {
        isFound = true;
        break;
      }
    }
    return isFound;
  }
}