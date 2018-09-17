package com.lj.app.core.common.generator.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DatabaseDataTypesUtilsTest {

	@Test
	public void isFloatNumberTest() {
		assertTrue(DatabaseDataTypesUtils.isFloatNumber("Float"));
		assertTrue(DatabaseDataTypesUtils.isFloatNumber("Double"));
		assertTrue(DatabaseDataTypesUtils.isFloatNumber("BigDecimal"));
		
		assertFalse(DatabaseDataTypesUtils.isFloatNumber(null));
		assertFalse(DatabaseDataTypesUtils.isFloatNumber(""));
		
		assertFalse(DatabaseDataTypesUtils.isFloatNumber("typeNotExists"));
	}
	
	@Test
	public void isFloatNumberWithSqlTypeTest() {
		assertFalse(DatabaseDataTypesUtils.isFloatNumber(1,2,3));
	}

	@Test
	public void isIntegerNumberTest() {
		assertTrue(DatabaseDataTypesUtils.isIntegerNumber("Long"));
		assertTrue(DatabaseDataTypesUtils.isIntegerNumber("Integer"));
		assertTrue(DatabaseDataTypesUtils.isIntegerNumber("Short"));
		assertTrue(DatabaseDataTypesUtils.isIntegerNumber("Byte"));
		
		assertFalse(DatabaseDataTypesUtils.isIntegerNumber("null"));
		assertFalse(DatabaseDataTypesUtils.isIntegerNumber(""));
		
		assertFalse(DatabaseDataTypesUtils.isIntegerNumber("typeNotExists"));
	}
	
	@Test
	public void isIntegerNumberWithSqlTypeTest() {
		assertFalse(DatabaseDataTypesUtils.isIntegerNumber(1,2,3));
	}

	@Test
	public void isDateTest() {
		assertTrue(DatabaseDataTypesUtils.isDate("Date"));
		assertTrue(DatabaseDataTypesUtils.isDate("Timestamp"));
		assertTrue(DatabaseDataTypesUtils.isDate("Time"));
		
		assertFalse(DatabaseDataTypesUtils.isDate(null));
		assertFalse(DatabaseDataTypesUtils.isDate(""));
		
		assertFalse(DatabaseDataTypesUtils.isDate(null));
		assertFalse(DatabaseDataTypesUtils.isDate(""));
		assertFalse(DatabaseDataTypesUtils.isDate("DateNot"));
		assertFalse(DatabaseDataTypesUtils.isDate("TimestampNot"));
		assertFalse(DatabaseDataTypesUtils.isDate("TimeNot"));
	}
	
	@Test
	public void isDateWithSqlTypeTest() {
		assertFalse(DatabaseDataTypesUtils.isDate(1,2,3));
	}

	@Test
	public void isStringTest() {
		assertTrue(DatabaseDataTypesUtils.isString("String"));
		
		assertFalse(DatabaseDataTypesUtils.isString(null));
		assertFalse(DatabaseDataTypesUtils.isString(""));
		assertFalse(DatabaseDataTypesUtils.isString("notStr"));
		assertFalse(DatabaseDataTypesUtils.isString("StringIsNot"));
	}

	@Test
	public void isStringWithSqlTypeTest() {
		assertTrue(DatabaseDataTypesUtils.isString(1,2,3));
	}
	
	@Test
	public void getPreferredJavaTypeTest() {
		assertEquals("java.lang.Boolean",DatabaseDataTypesUtils.getPreferredJavaType(3,1,0));
		assertEquals("java.lang.Byte",DatabaseDataTypesUtils.getPreferredJavaType(3,2,0));
		assertEquals("java.lang.Short",DatabaseDataTypesUtils.getPreferredJavaType(3,3,0));
		assertEquals("java.lang.Short",DatabaseDataTypesUtils.getPreferredJavaType(3,4,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(3,5,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(3,6,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(3,7,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(3,8,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(3,9,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,10,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,11,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,12,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,13,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,14,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,15,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,16,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,17,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(3,18,0));
		assertEquals("java.math.BigDecimal",DatabaseDataTypesUtils.getPreferredJavaType(3,19,0));
		
		assertEquals("java.math.BigDecimal",DatabaseDataTypesUtils.getPreferredJavaType(3,20,0));
		
		assertEquals("java.lang.Boolean",DatabaseDataTypesUtils.getPreferredJavaType(2,1,0));
		assertEquals("java.lang.Byte",DatabaseDataTypesUtils.getPreferredJavaType(2,2,0));
		assertEquals("java.lang.Short",DatabaseDataTypesUtils.getPreferredJavaType(2,3,0));
		assertEquals("java.lang.Short",DatabaseDataTypesUtils.getPreferredJavaType(2,4,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(2,5,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(2,6,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(2,7,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(2,8,0));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(2,9,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,10,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,11,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,12,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,13,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,14,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,15,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,16,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,17,0));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(2,18,0));
		assertEquals("java.math.BigDecimal",DatabaseDataTypesUtils.getPreferredJavaType(2,19,0));
		
		assertEquals("java.math.BigDecimal",DatabaseDataTypesUtils.getPreferredJavaType(2,20,0));
		
		assertEquals("java.lang.Byte",DatabaseDataTypesUtils.getPreferredJavaType(-6,0,1));
		assertEquals("java.lang.Short",DatabaseDataTypesUtils.getPreferredJavaType(5,0,1));
		assertEquals("java.lang.Integer",DatabaseDataTypesUtils.getPreferredJavaType(4,0,1));
		assertEquals("java.lang.Long",DatabaseDataTypesUtils.getPreferredJavaType(-5,0,1));
		assertEquals("java.lang.Float",DatabaseDataTypesUtils.getPreferredJavaType(7,0,1));
		assertEquals("java.lang.Double",DatabaseDataTypesUtils.getPreferredJavaType(6,0,1));
		
		assertEquals("java.lang.Double",DatabaseDataTypesUtils.getPreferredJavaType(8,0,1));
		
		assertEquals("java.math.BigDecimal",DatabaseDataTypesUtils.getPreferredJavaType(3,0,1));
		assertEquals("java.math.BigDecimal",DatabaseDataTypesUtils.getPreferredJavaType(2,0,1));
		assertEquals("java.lang.Boolean",DatabaseDataTypesUtils.getPreferredJavaType(-7,0,1));
		assertEquals("java.lang.Boolean",DatabaseDataTypesUtils.getPreferredJavaType(16,0,1));
		assertEquals("String",DatabaseDataTypesUtils.getPreferredJavaType(1,0,1));
		assertEquals("String",DatabaseDataTypesUtils.getPreferredJavaType(12,0,1));
		
		assertEquals("String",DatabaseDataTypesUtils.getPreferredJavaType(-1,0,1));
		assertEquals("byte[]",DatabaseDataTypesUtils.getPreferredJavaType(-2,0,1));
		assertEquals("byte[]",DatabaseDataTypesUtils.getPreferredJavaType(-3,0,1));
		assertEquals("byte[]",DatabaseDataTypesUtils.getPreferredJavaType(-4,0,1));
		
		assertEquals("java.sql.Date",DatabaseDataTypesUtils.getPreferredJavaType(91,0,1));
		assertEquals("java.sql.Time",DatabaseDataTypesUtils.getPreferredJavaType(92,0,1));
		assertEquals("java.sql.Timestamp",DatabaseDataTypesUtils.getPreferredJavaType(93,0,1));
		assertEquals("java.sql.Clob",DatabaseDataTypesUtils.getPreferredJavaType(2005,0,1));
		assertEquals("java.sql.Blob",DatabaseDataTypesUtils.getPreferredJavaType(2004,0,1));
		assertEquals("java.sql.Array",DatabaseDataTypesUtils.getPreferredJavaType(2003,0,1));
		assertEquals("java.sql.Ref",DatabaseDataTypesUtils.getPreferredJavaType(2006,0,1));
		assertEquals("java.lang.Object",DatabaseDataTypesUtils.getPreferredJavaType(2002,0,1));
		assertEquals("java.lang.Object",DatabaseDataTypesUtils.getPreferredJavaType(2000,0,1));
		
		assertEquals("java.lang.Object",DatabaseDataTypesUtils.getPreferredJavaType(99999,0,1));
		
	}

}
