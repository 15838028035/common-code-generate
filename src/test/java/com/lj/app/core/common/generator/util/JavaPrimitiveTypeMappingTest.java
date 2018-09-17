package com.lj.app.core.common.generator.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JavaPrimitiveTypeMappingTest {

		@Test
		public void test_getPrimitiveType_getWrapperType() {
			assertEquals("int",JavaPrimitiveTypeMapping.getPrimitiveType("java.lang.Integer"));
			assertEquals("short",JavaPrimitiveTypeMapping.getPrimitiveType("java.lang.Short"));
			assertEquals("int",JavaPrimitiveTypeMapping.getPrimitiveType("Integer"));
			assertEquals(null,JavaPrimitiveTypeMapping.getPrimitiveType(null));
			assertEquals("int",JavaPrimitiveTypeMapping.getPrimitiveType("int"));
			assertEquals("Blog",JavaPrimitiveTypeMapping.getPrimitiveType("Blog"));
			assertEquals("abc.User",JavaPrimitiveTypeMapping.getPrimitiveType("abc.User"));
			assertEquals("Integer",JavaPrimitiveTypeMapping.getWrapperType("int"));
			assertEquals("Long",JavaPrimitiveTypeMapping.getWrapperType("long"));
			assertEquals("Character",JavaPrimitiveTypeMapping.getWrapperType("char"));
			assertEquals("Badqiu",JavaPrimitiveTypeMapping.getWrapperType("Badqiu"));
			assertEquals(null,JavaPrimitiveTypeMapping.getWrapperType(null));
		}
		
		@Test
		public void getWrapperTypeOrNullTest(){
			assertEquals(null,JavaPrimitiveTypeMapping.getWrapperTypeOrNull("Integer"));
		}
	
		@Test
		public void getDefaultValueTest() {
			assertEquals("0",JavaPrimitiveTypeMapping.getDefaultValue("Money"));
			assertEquals("0",JavaPrimitiveTypeMapping.getDefaultValue("short"));
			assertEquals("0",JavaPrimitiveTypeMapping.getDefaultValue("int"));
			assertEquals("null",JavaPrimitiveTypeMapping.getDefaultValue("Long"));
			assertEquals("null",JavaPrimitiveTypeMapping.getDefaultValue("javablog"));
		}

}
