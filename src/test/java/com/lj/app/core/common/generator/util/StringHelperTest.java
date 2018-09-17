package com.lj.app.core.common.generator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class StringHelperTest {

	@Test
	public void emptyIfValueIsNotNullTest() {
		assertEquals("value",StringHelper.emptyIf("value", "defaultValue"));
	}
	
	@Test
	public void emptyIfValueIsNullTest() {
		assertEquals("defaultValue",StringHelper.emptyIf(null, "defaultValue"));
		assertEquals("defaultValue",StringHelper.emptyIf("", "defaultValue"));
	}

	@Test
	public void makeAllWordFirstLetterUpperCaseTest() {
		assertEquals(null,StringHelper.makeAllWordFirstLetterUpperCase(null));
		assertEquals("",StringHelper.makeAllWordFirstLetterUpperCase(""));
		assertEquals(" ",StringHelper.makeAllWordFirstLetterUpperCase(" "));
		
		assertEquals("A",StringHelper.makeAllWordFirstLetterUpperCase("a"));
		assertEquals("A",StringHelper.makeAllWordFirstLetterUpperCase("A"));
		
		assertEquals("Selecttalbename",StringHelper.makeAllWordFirstLetterUpperCase("selectTalbeName"));
		assertEquals("SelectTalbeName",StringHelper.makeAllWordFirstLetterUpperCase("select_Talbe_Name"));
		assertEquals("SelectTableName",StringHelper.makeAllWordFirstLetterUpperCase("SELECT_TABLE_NAME"));
		assertEquals("SelectTalbeName",StringHelper.makeAllWordFirstLetterUpperCase("select_talbe_name"));
		assertEquals("SelectTalbeName",StringHelper.makeAllWordFirstLetterUpperCase("select_tAlbe_nAme"));
	}

	@Test
	public void replaceNullTest() {
		assertNull(StringHelper.replace(null, "oldPattern", "newPattern"));
		assertNull(StringHelper.replace(null, null, null));
		assertEquals("inStr",StringHelper.replace("inStr", null, null));
	}
	
	@Test
	@Ignore
	public void replaceNotNullTest() {
		assertEquals("inStr",StringHelper.replace("inStr", "", ""));
		assertEquals("This is a replace not null Test",StringHelper.replace("This is a replace not null Test", "not", ""));
		assertEquals("This is a replace not null expected test",StringHelper.replace("This is a replace not null Test", "not", "null expected test"));
	}

	@Test
	public void capitalizeTest() {
		assertNull(StringHelper.capitalize(null));
		assertEquals("",StringHelper.capitalize(""));
		assertEquals("StringHelperTest",StringHelper.capitalize("stringHelperTest"));
		assertEquals("S",StringHelper.capitalize("s"));
	}

	@Test
	public void uncapitalizeTest() {
		assertNull(StringHelper.uncapitalize(null));
		assertEquals("",StringHelper.uncapitalize(""));
		assertEquals("stringHelperTest",StringHelper.uncapitalize("stringHelperTest"));
		assertEquals("stringHelperTest",StringHelper.uncapitalize("StringHelperTest"));
		assertEquals("s",StringHelper.uncapitalize("s"));
		assertEquals("s",StringHelper.uncapitalize("S"));
	}

	@Test
	public void randomNumericTest() {
		assertTrue(StringHelper.randomNumeric(3).length()==3);
		assertTrue(StringHelper.randomNumeric(4).length()==4);
	}

	@Test
	public void randomIntBooleanBooleanTest() {
	}

	@Test
	public void testRandomIntIntIntBooleanBoolean() {
	}

	@Test
	public void testRandomIntIntIntBooleanBooleanCharArrayRandom() {
	}

	@Test
	public void toUnderscoreNameTest() {
		assertNull(StringHelper.toUnderscoreName(null));
		assertEquals("",StringHelper.toUnderscoreName(""));
		assertEquals(" ",StringHelper.toUnderscoreName(" "));
		
		assertEquals("_tounderscorename",StringHelper.toUnderscoreName("_TOUNDERSCORENAME"));
		assertEquals("tounderscorename",StringHelper.toUnderscoreName("TOUNDERSCORENAME"));
		assertEquals("to_underscore_name",StringHelper.toUnderscoreName("to_UnderscoreName"));
		assertEquals("to_under_score_name",StringHelper.toUnderscoreName("to_Under_score_Name"));
		assertEquals("to_under_score_name",StringHelper.toUnderscoreName("TO_UNDER_SCORE_NAME"));
	}

}
