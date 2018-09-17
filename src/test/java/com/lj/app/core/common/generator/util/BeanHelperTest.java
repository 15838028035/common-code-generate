package com.lj.app.core.common.generator.util;

import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BeanHelperTest {

	@Test
	public void describeTest() {
		Map<String,String> map = new HashMap<String,String>();
		assertSame(map,BeanHelper.describe(map));
	}

	@Test
	public void getPropertyDescriptorsTest() {
	}

}
