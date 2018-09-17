package com.lj.app.core.common.generator;

import org.junit.Test;

public class GeneratorMainTest {
	@Test
	public void generateTalbeTest() throws Exception{
		GeneratorProperties.setProperty("basepackage", "com.lj.app.core.common.flows");
		GeneratorProperties.setProperty("basepackage_dir", GeneratorProperties.getProperty("basepackage").replace(".", "/"));
		
		GeneratorProperties.setProperty("outRoot", "e:\\generator-output");
		
		GeneratorProperties.setProperty("author", "liujie");
		
		GeneratorFacade g = new GeneratorFacade();
		g.getGenerator().setTemplateRootDir("classpath:template");
		
		g.clean();
		
		g.generateByTable("UPM_USER");

		System.out.println("");
		System.out
				.println("***************************************************************");
		System.out
				.println("*********************Generate Success**************************");
		System.out
				.println("***************************************************************");
	}

}
