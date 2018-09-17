package com.lj.app.core.common.generator.provider.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lj.app.core.common.generator.GeneratorProperties;
import com.lj.app.core.common.generator.provider.db.model.Column;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.util.GLogger;

public class DbTableFactory {
  private Connection connection;
  private static DbTableFactory instance = null;

  private DbTableFactory() {
    init();
  }

  private void init() {
    String driver = GeneratorProperties.getRequiredProperty("jdbc.driver");
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("not found jdbc driver class:[" + driver + "]", e);
    }
  }

  public static synchronized DbTableFactory getInstance() {
    if (instance == null)
      instance = new DbTableFactory();
    return instance;
  }

  public String getCatalog() {
    return GeneratorProperties.getNullIfBlank("jdbc.catalog");
  }

  public String getSchema() {
    return GeneratorProperties.getNullIfBlank("jdbc.schema");
  }

  public Connection getConnection() throws SQLException {
    if ((this.connection == null) || (this.connection.isClosed())) {
      Properties properties = new Properties();
      properties.setProperty("user", GeneratorProperties.getRequiredProperty("jdbc.username"));
      properties.setProperty("password", GeneratorProperties.getProperty("jdbc.password"));
      properties.setProperty("remarks", "true");
      properties.setProperty("useInformationSchema", "true");
      this.connection = DriverManager.getConnection(GeneratorProperties.getRequiredProperty("jdbc.url"), properties);
    }
    return this.connection;
  }

  public List getAllTables() throws Exception {
    Connection conn = getConnection();
    return getAllTables(conn);
  }

  public Table getTable(String sqlTableName) throws Exception {
    Table t = _getTable(sqlTableName);
    if ((t == null) && (!sqlTableName.equals(sqlTableName.toUpperCase()))) {
      t = _getTable(sqlTableName.toUpperCase());
    }
    if ((t == null) && (!sqlTableName.equals(sqlTableName.toLowerCase()))) {
      t = _getTable(sqlTableName.toLowerCase());
    }

    if (t == null) {
      throw new RuntimeException("not found table with give name:" + sqlTableName);
    }
    return t;
  }

  private Table _getTable(String sqlTableName) throws SQLException {
    Connection conn = getConnection();
    DatabaseMetaData dbMetaData = conn.getMetaData();
    ResultSet rs = conn.getMetaData().getTables(null, null, sqlTableName.toUpperCase(), new String[] { "TABLE" });
    if (rs.next()) {
      Table table = createTable(conn, rs);
      return table;
    }
    return null;
  }

  private Table createTable(Connection conn, ResultSet rs) throws SQLException {
    String realTableName = null;
    try {
      ResultSetMetaData rsMetaData = rs.getMetaData();
      String schemaName = rs.getString("TABLE_SCHEM") == null ? "" : rs.getString("TABLE_SCHEM");
      realTableName = rs.getString("TABLE_NAME");
      String tableType = rs.getString("TABLE_TYPE");
      String remarks = rs.getString("REMARKS");
      if ((remarks == null) && (isOracleDataBase())) {
        remarks = getOracleTableComments(realTableName);
      }

      Table table = new Table();
      table.setSqlName(realTableName);
      table.setRemarks(remarks);

      if (("SYNONYM".equals(tableType)) && (isOracleDataBase())) {
        table.setOwnerSynonymName(getSynonymOwner(realTableName));
      }

      retriveTableColumns(table);

      table.initExportedKeys(conn.getMetaData());
      table.initImportedKeys(conn.getMetaData());
      return table;
    } catch (SQLException e) {
      throw new RuntimeException("create table object error,tableName:" + realTableName, e);
    }

  }

  private List getAllTables(Connection conn) throws SQLException {
    DatabaseMetaData dbMetaData = conn.getMetaData();
    ResultSet rs = dbMetaData.getTables(getCatalog(), getSchema(), null, null);
    List tables = new ArrayList();
    while (rs.next()) {
      Table table = createTable(conn, rs);
      tables.add(table);
    }
    return tables;
  }

  private boolean isOracleDataBase() {
    boolean ret = false;
    try {
      ret = getMetaData().getDatabaseProductName().toLowerCase().indexOf("oracle") != -1;
    } catch (Exception ignore) {
    }
    return ret;
  }

  private String getSynonymOwner(String synonymName) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    String ret = null;
    try {
      ps = getConnection().prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
      ps.setString(1, synonymName);
      ps.setString(2, getSchema());
      rs = ps.executeQuery();
      if (rs.next()) {
        ret = rs.getString(1);
      } else {
        String databaseStructure = getDatabaseStructureInfo();
        throw new RuntimeException(
            "Wow! Synonym " + synonymName + " not found. How can it happen? " + databaseStructure);
      }
    } catch (SQLException e) {
      String databaseStructure = getDatabaseStructureInfo();
      GLogger.error(e.getMessage(), e);
      throw new RuntimeException("Exception in getting synonym owner " + databaseStructure);
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (Exception e) {
        }
      if (ps != null)
        try {
          ps.close();
        } catch (Exception e) {
        }
    }
    return ret;
  }

  private String getDatabaseStructureInfo() {
    ResultSet schemaRs = null;
    ResultSet catalogRs = null;
    String nl = System.getProperty("line.separator");
    StringBuilder sb = new StringBuilder(nl);

    sb.append("Configured schema:").append(getSchema()).append(nl);
    sb.append("Configured catalog:").append(getCatalog()).append(nl);
    try {
      schemaRs = getMetaData().getSchemas();
      sb.append("Available schemas:").append(nl);
      while (schemaRs.next())
        sb.append("  ").append(schemaRs.getString("TABLE_SCHEM")).append(nl);
    } catch (SQLException e2) {
      GLogger.warn("Couldn't get schemas", e2);
      sb.append("  ?? Couldn't get schemas ??").append(nl);
    } finally {
      try {
        schemaRs.close();
      } catch (Exception ignore) {
      }
    }
    try {
      catalogRs = getMetaData().getCatalogs();
      sb.append("Available catalogs:").append(nl);
      while (catalogRs.next())
        sb.append("  ").append(catalogRs.getString("TABLE_CAT")).append(nl);
    } catch (SQLException e2) {
      GLogger.warn("Couldn't get catalogs", e2);
      sb.append("  ?? Couldn't get catalogs ??").append(nl);
    } finally {
      try {
        if(catalogRs!=null){
          catalogRs.close();
        }
        
      } catch (Exception ignore) {
      }
    }
    return sb.toString();
  }

  private DatabaseMetaData getMetaData() throws SQLException {
    return getConnection().getMetaData();
  }

  private void retriveTableColumns(Table table) throws SQLException {
    GLogger.debug("-------setColumns(" + table.getSqlName() + ")");

    List primaryKeys = getTablePrimaryKeys(table);
    table.setPrimaryKeyColumns(primaryKeys);

    List foreignKeys = getTableForeignKeys(table);
    table.setForeignKeyColumns(foreignKeys);

    List indices = new LinkedList();

    Map uniqueIndices = new HashMap();

    Map uniqueColumns = new HashMap();
    ResultSet indexRs = null;
    try {
      if (table.getOwnerSynonymName() != null) {
        indexRs = getMetaData().getIndexInfo(getCatalog(), table.getOwnerSynonymName(), table.getSqlName(), false,
            true);
      } else {
        indexRs = getMetaData().getIndexInfo(null, null, table.getSqlName(), false, true);
      }
      while (indexRs.next()) {
        String columnName = indexRs.getString("COLUMN_NAME");
        if (columnName != null) {
          GLogger.debug("index:" + columnName);
          indices.add(columnName);
        }

        String indexName = indexRs.getString("INDEX_NAME");
        boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");

        if ((!nonUnique) && (columnName != null) && (indexName != null)) {
          List l = (List) uniqueColumns.get(indexName);
          if (l == null) {
            l = new ArrayList();
            uniqueColumns.put(indexName, l);
          }
          l.add(columnName);
          uniqueIndices.put(columnName, indexName);
          GLogger.debug("unique:" + columnName + " (" + indexName + ")");
        }
      }
    } catch (Throwable t) {
    } finally {
      if (indexRs != null) {
        indexRs.close();
      }
    }

    List columns = getTableColumns(table, primaryKeys, foreignKeys, indices, uniqueIndices, uniqueColumns);

    for (Iterator i = columns.iterator(); i.hasNext();) {
      Column column = (Column) i.next();
      table.addColumn(column);
    }

    if (primaryKeys.size() == 0)
      GLogger.warn("WARNING: The JDBC driver didn't report any primary key columns in " + table.getSqlName());
  }

  private List getTableColumns(Table table, List primaryKeys, List<String> foreignKeys, List indices, Map uniqueIndices,
      Map uniqueColumns) throws SQLException {
    List columns = new LinkedList();
    ResultSet columnRs = getColumnsResultSet(table);

    while (columnRs.next()) {
      int sqlType = columnRs.getInt("DATA_TYPE");
      String sqlTypeName = columnRs.getString("TYPE_NAME");
      String columnName = columnRs.getString("COLUMN_NAME");
      String columnDefaultValue = columnRs.getString("COLUMN_DEF");

      String remarks = columnRs.getString("REMARKS");
      if ((remarks == null) && (isOracleDataBase())) {
        remarks = getOracleColumnComments(table.getSqlName(), columnName);
      }

      boolean isNullable = 1 == columnRs.getInt("NULLABLE");
      int size = columnRs.getInt("COLUMN_SIZE");
      int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");

      boolean isPk = primaryKeys.contains(columnName);

      boolean isFk = false;
      String fkTableName = "";
      for (int i = 0; i < foreignKeys.size(); i++) {
        String[] result = ((String) foreignKeys.get(i)).split("\\|\\|");
        if (columnName.equals(result[1])) {
          isFk = true;
          fkTableName = result[0];

          break;
        }
      }

      boolean isIndexed = indices.contains(columnName);
      String uniqueIndex = (String) uniqueIndices.get(columnName);
      List columnsInUniqueIndex = null;
      if (uniqueIndex != null) {
        columnsInUniqueIndex = (List) uniqueColumns.get(uniqueIndex);
      }

      boolean isUnique = (columnsInUniqueIndex != null) && (columnsInUniqueIndex.size() == 1);
      if (isUnique) {
        GLogger.debug("unique column:" + columnName);
      }
      Column column = new Column(table, sqlType, sqlTypeName, columnName, size, decimalDigits, isPk, isFk, fkTableName,
          isNullable, isIndexed, isUnique, columnDefaultValue, remarks);

      columns.add(column);
    }
    columnRs.close();
    return columns;
  }

  private ResultSet getColumnsResultSet(Table table) throws SQLException {
    ResultSet columnRs = null;
    if (table.getOwnerSynonymName() != null)
      columnRs = getMetaData().getColumns(getCatalog(), table.getOwnerSynonymName(), table.getSqlName(), null);
    else {
      columnRs = getMetaData().getColumns(null, null, table.getSqlName(), null);
    }
    return columnRs;
  }

  private List<String> getTablePrimaryKeys(Table table) throws SQLException {
    List primaryKeys = new LinkedList();
    ResultSet primaryKeyRs = null;
    if (table.getOwnerSynonymName() != null) {
      primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(), table.getOwnerSynonymName(), table.getSqlName());
    } else {
      primaryKeyRs = getMetaData().getPrimaryKeys(null, null, table.getSqlName());
    }
    while (primaryKeyRs.next()) {
      String columnName = primaryKeyRs.getString("COLUMN_NAME");
      GLogger.debug("primary key:" + columnName);
      primaryKeys.add(columnName);
    }
    primaryKeyRs.close();
    return primaryKeys;
  }

  private List<String> getTableForeignKeys(Table table) throws SQLException {
    List foreignKeys = new LinkedList();
    ResultSet foreignKeyRs = null;
    if (table.getOwnerSynonymName() != null) {
      foreignKeyRs = getMetaData().getImportedKeys(getCatalog(), table.getOwnerSynonymName(), table.getSqlName());
    } else {
      foreignKeyRs = getMetaData().getImportedKeys(null, null, table.getSqlName());
    }
    while (foreignKeyRs.next()) {
      String foreignTableName = foreignKeyRs.getString("PKTABLE_NAME");
      String foreignColumnName = foreignKeyRs.getString("FKCOLUMN_NAME");
      GLogger.debug("foreign table:" + foreignTableName + "foreign key:" + foreignColumnName);
      foreignKeys.add(foreignTableName + "||" + foreignColumnName);
    }
    foreignKeyRs.close();

    return foreignKeys;
  }

  private String getOracleTableComments(String table) {
    String sql = "SELECT comments FROM user_tab_comments WHERE table_name='" + table + "'";
    return queryForString(sql);
  }

  private String queryForString(String sql) {
    Statement s = null;
    ResultSet rs = null;
    String str = null;
    try {
      s = getConnection().createStatement();
      rs = s.executeQuery(sql);
      if (rs.next()) {
        str = rs.getString(1);
        return str;
      }
      return str;
    } catch (SQLException e) {
      GLogger.error("SQLException",e);
      e = null;
      return e.getMessage();
    } finally {
      try {
        if (s != null) {
          s.close();
        }
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
        GLogger.error("SQLException",e);
      }
    }
  }

  private String getOracleColumnComments(String table, String column) {
    String sql = "SELECT comments FROM user_col_comments WHERE table_name='" + table + "' AND column_name = '" + column
        + "'";
    return queryForString(sql);
  }
}