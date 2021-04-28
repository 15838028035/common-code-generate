package com.lj.app.core.common.generator;

import org.junit.Test;

public class GeneratorProductAndConsumerFacadeTest {

	@Test
	public void generateTalbeTest() throws Exception{
		GeneratorProperties.setProperty("basepackage", "com.lj.app.core.common.flows");
		GeneratorProperties.setProperty("basepackage_dir", GeneratorProperties.getProperty("basepackage").replace(".", "/"));
		
		GeneratorProperties.setProperty("outRoot", "f:\\generator-output");
		
		GeneratorProperties.setProperty("author", "liujie");
		
		GeneratorProductAndConsumerFacade g = new GeneratorProductAndConsumerFacade();
		g.getGenerator().setTemplateRootDir("f:\\template");
		
		g.clean();
		
		g.generateByTable("sec_user");
		

		System.out.println("");
		System.out
				.println("***************************************************************");
		System.out
				.println("*********************Generate Success**************************");
		System.out
				.println("***************************************************************");
	}

}
