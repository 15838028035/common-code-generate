package com.lj.app.core.common.generator;

/**
 * 
 *表格数据模型 
 *
 */
public class TableViewData {

	/**
	 * 表名称
	 */
	private String tableName;
	
	/**
	 * 基础包
	 */
	private String basepackage;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBasepackage() {
		return basepackage;
	}

	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}
	
}
