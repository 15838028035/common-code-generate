package com.lj.app.core.common.generator.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionScriptDataTypesUtilsTest {

	@Test
	public void getPreferredAsTypeTest() {
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Short"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("short"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Integer"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("int"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Long"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("long"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Float"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("float"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Double"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.math.BigDecimal"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Byte"));
		assertEquals("Number",ActionScriptDataTypesUtils.getPreferredAsType("byte"));
		
		assertEquals("Boolean",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.Boolean"));
		assertEquals("Boolean",ActionScriptDataTypesUtils.getPreferredAsType("boolen"));
		
		assertEquals("String",ActionScriptDataTypesUtils.getPreferredAsType("char"));
		assertEquals("String",ActionScriptDataTypesUtils.getPreferredAsType("char[]"));
		assertEquals("String",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.String"));
		assertEquals("String",ActionScriptDataTypesUtils.getPreferredAsType("java.sql.Clob"));
		
		assertEquals("flash.utils.ByteArray",ActionScriptDataTypesUtils.getPreferredAsType("byte[]"));
		assertEquals("flash.utils.ByteArray",ActionScriptDataTypesUtils.getPreferredAsType("java.sql.Blob"));
		
		assertEquals("Array",ActionScriptDataTypesUtils.getPreferredAsType("java.sql.Array"));
		assertEquals("Array",ActionScriptDataTypesUtils.getPreferredAsType("java.lang.reflect.Array"));
		
		assertEquals("mx.collections.ArrayCollection",ActionScriptDataTypesUtils.getPreferredAsType("java.util.Collection"));
		assertEquals("mx.collections.ArrayCollection",ActionScriptDataTypesUtils.getPreferredAsType("java.util.List"));
		assertEquals("mx.collections.ArrayCollection",ActionScriptDataTypesUtils.getPreferredAsType("java.util.ArrayList"));
		assertEquals("mx.collections.ArrayCollection",ActionScriptDataTypesUtils.getPreferredAsType("java.util.List"));
		
		assertEquals("Object",ActionScriptDataTypesUtils.getPreferredAsType("java.util.Set"));
		assertEquals("Object",ActionScriptDataTypesUtils.getPreferredAsType("java.util.HashSet"));
		assertEquals("Object",ActionScriptDataTypesUtils.getPreferredAsType("java.util.Map"));
		assertEquals("Object",ActionScriptDataTypesUtils.getPreferredAsType("java.util.HashMap"));
		
		assertEquals("Date",ActionScriptDataTypesUtils.getPreferredAsType("java.sql.Date"));
		assertEquals("Date",ActionScriptDataTypesUtils.getPreferredAsType("java.sql.Time"));
		assertEquals("Date",ActionScriptDataTypesUtils.getPreferredAsType("java.util.Date"));
		assertEquals("Date",ActionScriptDataTypesUtils.getPreferredAsType("java.sql.Timestamp"));
		
		assertEquals("javaTypeNotExists",ActionScriptDataTypesUtils.getPreferredAsType("javaTypeNotExists"));
	}

}
