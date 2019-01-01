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
	
	/**
	 * 排序编号
	 */
	private Integer sortNo;

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

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
}
