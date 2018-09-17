package com.lj.app.core.common.generator.util;

import org.junit.Before;
import org.junit.Test;

public class GLoggerTest {
	
	private GLogger gLogger;
	@Before
	public void setUp() {
		gLogger = new GLogger();
	}

	@Test
	public void debugTest() {
		GLogger.logLevel=0;
		GLogger.debug("debug");
	}
	
	@Test
	public void debugNotShowTest() {
		GLogger.logLevel=2;
		GLogger.debug("debug");
	}

	@Test
	public void infoTest() {
		GLogger.logLevel=5;
		GLogger.info("info");
	}
	
	@Test
	public void infoNotShowTest() {
		GLogger.logLevel=6;
		GLogger.info("info");
	}

	@Test
	public void warnTest() {
		GLogger.logLevel=15;
		GLogger.warn("warn");
	}
	
	@Test
	public void warnNotShowTest() {
		GLogger.logLevel=20;
		GLogger.warn("warn");
	}

	@Test
	public void warnThrowableTest() {
		GLogger.logLevel=15;
		GLogger.warn("warn", new Throwable("throwable msg"));
	}
	
	@Test
	public void warnThrowableNotShowTest() {
		GLogger.logLevel=20;
		GLogger.warn("warn", new Throwable("throwable msg"));
	}

	@Test
	public void errorTest() {
		GLogger.logLevel=10;
		GLogger.error("error");
	}
	
	@Test
	public void errorNotShowTest() {
		GLogger.logLevel=30;
		GLogger.error("error");
	}

	@Test
	public void errorThrowableTest() {
		GLogger.logLevel=10;
		GLogger.error("error", new Throwable("throwable msg"));
	}
	
	@Test
	public void errorThrowableNotShowTest() {
		GLogger.logLevel=30;
		GLogger.error("error", new Throwable("throwable msg"));
	}

}
