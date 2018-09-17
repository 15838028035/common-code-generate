package com.lj.app.core.common.generator.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JdbcTypeTest {

	@Test
	public void getJdbcSqlTypeNameTest() {
		assertEquals("BIT",JdbcType.getJdbcSqlTypeName(-7));
		assertEquals("TINYINT",JdbcType.getJdbcSqlTypeName(-6));
		assertEquals("SMALLINT",JdbcType.getJdbcSqlTypeName(5));
		assertEquals("INTEGER",JdbcType.getJdbcSqlTypeName(4));
		assertEquals("BIGINT",JdbcType.getJdbcSqlTypeName(-5));
		
		assertEquals("FLOAT",JdbcType.getJdbcSqlTypeName(6));
		assertEquals("REAL",JdbcType.getJdbcSqlTypeName(7));
		assertEquals("DOUBLE",JdbcType.getJdbcSqlTypeName(8));
		assertEquals("NUMERIC",JdbcType.getJdbcSqlTypeName(2));
		
		
		assertEquals("DECIMAL",JdbcType.getJdbcSqlTypeName(3));
		assertEquals("CHAR",JdbcType.getJdbcSqlTypeName(1));
		assertEquals("VARCHAR",JdbcType.getJdbcSqlTypeName(12));
		assertEquals("LONGVARCHAR",JdbcType.getJdbcSqlTypeName(-1));
		assertEquals("DATE",JdbcType.getJdbcSqlTypeName(91));
		
		assertEquals("TIME",JdbcType.getJdbcSqlTypeName(92));
		assertEquals("TIMESTAMP",JdbcType.getJdbcSqlTypeName(93));
		assertEquals("BINARY",JdbcType.getJdbcSqlTypeName(-2));
		assertEquals("VARBINARY",JdbcType.getJdbcSqlTypeName(-3));
		assertEquals("LONGVARBINARY",JdbcType.getJdbcSqlTypeName(-4));
		assertEquals("NULL",JdbcType.getJdbcSqlTypeName(0));
		
		assertEquals("OTHER",JdbcType.getJdbcSqlTypeName(1111));
		assertEquals("BLOB",JdbcType.getJdbcSqlTypeName(2004));
		assertEquals("CLOB",JdbcType.getJdbcSqlTypeName(2005));
		assertEquals("BOOLEAN",JdbcType.getJdbcSqlTypeName(16));
		
		assertEquals("CURSOR",JdbcType.getJdbcSqlTypeName(-10));
		assertEquals("UNDEFINED",JdbcType.getJdbcSqlTypeName(-2147482648));
		assertEquals("NVARCHAR",JdbcType.getJdbcSqlTypeName(-9));
		assertEquals("NCHAR",JdbcType.getJdbcSqlTypeName(-15));
		assertEquals("NCLOB",JdbcType.getJdbcSqlTypeName(2011));
		
		assertEquals(null,JdbcType.getJdbcSqlTypeName(9999));
	
	}

}
