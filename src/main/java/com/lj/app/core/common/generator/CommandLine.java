package com.lj.app.core.common.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.util.ArrayHelper;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.StringHelper;
import com.lj.app.core.common.generator.util.SystemHelper;
/**
 * 命令行工具类,可以直接运行
 * 
 */
public class CommandLine {
	
	public static void main(String[] args) throws Exception {
		freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
		
		startProcess();
	}

	private static void startProcess() throws Exception {
		Scanner sc = new Scanner(System.in);
		GLogger.info("templateRootDir:"+new File(getTemplateRootDir()).getAbsolutePath());
		
	  GeneratorProperties.setProperty("basepackage", getBasepackage());
      GeneratorProperties.setProperty("basepackage_dir",
      GeneratorProperties.getProperty("basepackage").replace(".", "/"));
	      
		printUsages();
		while(sc.hasNextLine()) {
			try {
				processLine(sc);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				Thread.sleep(700);
				printUsages();
			}
		}
	}

	private static void processLine(Scanner sc) throws Exception {
		GeneratorFacade facade = new GeneratorFacade();
		String cmd = sc.next();
		if("setTemplate".equalsIgnoreCase(cmd)) {
			  
			  String[] args = nextArguments(sc);
			  
			  String TemplateDir = null;
			  
			  if(args.length>0) {
				  TemplateDir = args[0];
			  }
			  
		  System.setProperty("templateRootDir", TemplateDir);
		      
	    }else if("setPackage".equalsIgnoreCase(cmd)) {
			  
			  String[] args = nextArguments(sc);
			  
			  String basepackage = null;
			  
			  if(args.length>0) {
				  basepackage = args[0];
			  }
		  
		  GLogger.info("basepackage:"+basepackage);
			
		  GeneratorProperties.setProperty("basepackage",basepackage);
	      GeneratorProperties.setProperty("basepackage_dir",
	      GeneratorProperties.getProperty("basepackage").replace(".", "/"));
	      
		      
	    }else if("printAllTable".equalsIgnoreCase(cmd)) {
		  
		  String[] args = nextArguments(sc);
		  
		  String tableName = null;
		  
		  if(args.length>0) {
		    tableName = args[0];
		  }
		  
		   List<Table> result = DbTableFactory.getInstance().getAllTables();
	      
	      List<Map<String,Object>> restultList = new ArrayList<>();
	     
	      for (int i = 0; i < result.size(); i++) {
	        Table table = (Table) result.get(i);
	        
	        if(tableName ==null) {
  	        GLogger.info("================<"+table.getSqlName()+  ">==================");
  	        GLogger.info("sqlName:"+table.getSqlName());
  	        GLogger.info("remarks:"+table.getRemarks());
	        }
	        
	        if(tableName!=null && table.getSqlName().contains(tableName)) {
            GLogger.info("================<"+table.getSqlName()+  ">==================");
            GLogger.info("sqlName:"+table.getSqlName());
            GLogger.info("remarks:"+table.getRemarks());
          }
	       
	      }
     
    } else if("gen".equalsIgnoreCase(cmd)) {
			String[] args = nextArguments(sc);
			if(args.length == 0) return;
			facade.getGenerator().setIncludes(getIncludes(args,1));
			facade.getGenerator().setTemplateRootDir(getTemplateRootDir());
			if(args[0]!=null && "*".equals(args[0])) {
				  facade.generateByAllTable();
			}else {
				facade.generateByTable(args[0]);
			}
			if(SystemHelper.isWindowsOS) {
			    Runtime.getRuntime().exec("cmd.exe /c start "+GeneratorProperties.getRequiredProperty("outRoot").replace('/', '\\'));
			}
		}else if("del".equalsIgnoreCase(cmd)) {
			String[] args = nextArguments(sc);
			if(args.length == 0) return;
			facade.getGenerator().setIncludes(getIncludes(args,1));
			facade.getGenerator().deleteOutRootDir();
		}else if("quit".equalsIgnoreCase(cmd)) {
		    System.exit(0);
		}else {
			GLogger.error(" [ERROR] unknow command:"+cmd);
		}
	}

	private static String getIncludes(String[] args, int i) {
		String includes = ArrayHelper.getValue(args, i);
		if(includes == null) return null;
		return includes.indexOf("*") >= 0 || includes.indexOf(",") >= 0 ? includes : includes+"/**";
	}
	
	private static String getTemplateRootDir() {
		return System.getProperty("templateRootDir", "template");
	}
	private static String getBasepackage() {
		return System.getProperty("basepackage", "a.b.c");
	}

	private static void printUsages() {
		GLogger.info("Usage : java -server -Xms128m -Xmx384m com.lj.app.core.common.generator.CommandLine -DtemplateRootDir="+getTemplateRootDir());
		GLogger.info("\tsetTemplate templateDir ");
		GLogger.info("\tsetPackage basepackage ");
		GLogger.info("\tprintAllTable tableName ");
		GLogger.info("\tgen table_name [include_path]: generate files by table_name");
		GLogger.info("\tdel table_name [include_path]: delete files by table_name");
		GLogger.info("\tgen * [include_path]: search database all tables and generate files");
		GLogger.info("\tdel * [include_path]: search database all tables and delete files");
		GLogger.info("\tquit : quit");
		GLogger.info("\t[include_path] subdir of templateRootDir,example: 1. dao  2. dao/**,service/**");
		GLogger.info("\t current templateRootDir:"+getTemplateRootDir());
		GLogger.info("\t current basepackage:"+getBasepackage());
		GLogger.info("please input command:");
	}
	
	private static String[] nextArguments(Scanner sc) {
		return StringHelper.tokenizeToStringArray(sc.nextLine()," ");
	}
}