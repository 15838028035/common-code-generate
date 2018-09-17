package com.lj.app.core.common.generator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestDataGeneratorTest {
	
	private TestDataGenerator testDataGenerator;

	@Before
	public void setUp() {
		testDataGenerator = new TestDataGenerator();
	}
	
	@Test
	public void getDBUnitTestDataTest() {
		
		
		assertEquals("0",testDataGenerator.getDBUnitTestData("columnName", "Boolean", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "Timestamp", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "java.sql.Date", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "java.sql.Time", 1));
		
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "String", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "String", 100));
		
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "byte", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "short", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "integer", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "int", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "long", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "double", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "bigdecimal", 1));
		assertNotNull(testDataGenerator.getDBUnitTestData("columnName", "float", 1));
		
		assertEquals("",testDataGenerator.getDBUnitTestData("columnName", "javaTypeNotExists", 1));
	}

}
