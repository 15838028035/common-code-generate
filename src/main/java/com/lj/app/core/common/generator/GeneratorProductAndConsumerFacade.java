package com.lj.app.core.common.generator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.provider.java.model.JavaClass;
import com.lj.app.core.common.generator.util.BeanHelper;
import com.lj.app.core.common.generator.util.ClassHelper;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.StringHelper;

/**
 * 
 * 生成器门面类
 *
 */
public class GeneratorProductAndConsumerFacade {

  private Generator generator = new Generator();
  
  /**
   *阻塞队列
   */
  private BlockingQueue<TableViewData> blockingQueue = new LinkedBlockingDeque();

  /**
   * 构造函数
   */
  public GeneratorProductAndConsumerFacade() {
    if (StringHelper.isNotBlank(GeneratorProperties.getProperty("outRoot"))) {
      generator.setOutRootDir(GeneratorProperties.getProperty("outRoot"));
    }
  }

  public Generator getGenerator() {
    return generator;
  }

  public void setGenerator(Generator generator) {
    this.generator = generator;
  }

  /**
   * 打印所有表名称
   * @throws Exception 异常
   */
  public void printAllTableNames() throws Exception {
    List tables = DbTableFactory.getInstance().getAllTables();
    System.out.println("\n----All TableNames BEGIN----");
    for (int i = 0; i < tables.size(); i++) {
      String sqlName = ((Table) tables.get(i)).getSqlName();
      System.out.println("g.generateTable(\"" + sqlName + "\");");
    }
    System.out.println("----All TableNames END----");
  }

  /**
   * 根据表名称生成所有
   * @throws Exception 异常
   */
  public void generateByAllTable() throws Exception {
    List tables = DbTableFactory.getInstance().getAllTables();
    for (int i = 0; i < tables.size(); i++) {
      generateByTable(((Table) tables.get(i)).getSqlName());
    }
  }

  /**
   * 根据表名称生成代码
   * @param tableName 表名称
   * @throws Exception  异常
   */
  public void generateByTable(String tableName) throws Exception {
    generateByTable(StringHelper.tokenizeToStringArray(tableName, ","));
  }
  
  /**
   * 根据表名称生成代码
   * @param tableName 表名称
   * @throws Exception  异常
   */
  public void generateByTable(String ... tableNames) throws Exception {
    for (String tableName : tableNames) {
    	TableViewData tableViewData = new TableViewData();
    	tableViewData.setTableName(tableName);
      blockingQueue.add(tableViewData);
    }
    
    consumerTable();
  }
  
  /**
   * 根据表名称生成代码
   * @param tableName 表名称
   * @throws Exception  异常
   */
  public void add(TableViewData tableViewData) throws Exception {
    consumerTable();
  }
  
  /**
   * 根据表名称生成代码
   * @param tableName 表名称
   * @throws Exception  异常
   */
  public void add(List<TableViewData> list) throws Exception {
	  list.forEach(TableViewData-> blockingQueue.add(TableViewData) );
  }
  
  /**
   * 根据表名称生成代码
   * @param tableName 表名称
   * @throws Exception  异常
   */
  public void consumerTable() throws Exception {
	  while(blockingQueue.size()>0) {
		  TableViewData tableViewData = (TableViewData)  blockingQueue.take();
		  
	      Generator g = createGeneratorForDbTable();
	      
	  	GeneratorProperties.setProperty("basepackage", tableViewData.getBasepackage());
		GeneratorProperties.setProperty("basepackage_dir",
	    GeneratorProperties.getProperty("basepackage").replace(".", "/"));

	      
	      Table table = DbTableFactory.getInstance().getTable(tableViewData.getTableName());
	      generateByTable(g, table,1, blockingQueue.size()+1);
	  }
    
  }
  

  /**
   * 根据表名称生成代码
   * @param g 生成对象
   * @param table 表名称
   * @throws Exception  异常
   */
  public void generateByTable(Generator g, Table table) throws Exception {
    GeneratorModel m = GeneratorModel.newFromTable(table);
    String displayText = table.getSqlName() + " => " + table.getClassName();
    generateBy(g, m, displayText);
  }
  
  /**
   * 根据表名称生成代码
   * @param g 生成对象
   * @param table 表名称
   * @throws Exception  异常
   */
  public void generateByTable(Generator g, Table table, Integer a, Integer b) throws Exception {
    GeneratorModel m = GeneratorModel.newFromTable(table);
    String displayText = table.getSqlName() + " => " + table.getClassName();
    generateBy(g, m, displayText,a,b);
  }

  /**
   * 根据表名称生成代码
   * @param tableName 表名称
   * @param className 类名称
   * @throws Exception  异常
   */
  public void generateByTable(String tableName, String className) throws Exception {
    Generator g = createGeneratorForDbTable();
    Table table = DbTableFactory.getInstance().getTable(tableName);
    table.setClassName(className);
    generateByTable(g, table);
  }

  public void generateByClass(Class clazz) throws Exception {
    Generator g = createGeneratorForJavaClass();
    GeneratorModel m = GeneratorModel.newFromClass(clazz);
    String displayText = "JavaClass:" + clazz.getSimpleName();
    generateBy(g, m, displayText);
  }

  private void generateBy(Generator g, GeneratorModel m, String displayText, Integer a, Integer b) throws Exception {
    System.out.println("***************************************************************");
    System.out.println("* BEGIN generate " +a + "/" +  b + "  " + displayText + ",时间:" + LocalDateTime.now());
    System.out.println("***************************************************************");
    List<Exception> exceptions = g.generateBy(m.templateModel, m.filePathModel);
    if (exceptions.size() > 0) {
      System.err.println("[Generate Error Summary]");
      for (Exception e : exceptions) {
        GLogger.error("[GENERATE ERROR]:",e);
      }
    }
    
    System.out.println("* End generate " + displayText + ",时间:" + LocalDateTime.now());
  }
  
  private void generateBy(Generator g, GeneratorModel m, String displayText) throws Exception {
    System.out.println("***************************************************************");
    System.out.println("* BEGIN generate " + displayText + ",时间:" + LocalDateTime.now());
    System.out.println("***************************************************************");
    List<Exception> exceptions = g.generateBy(m.templateModel, m.filePathModel);
    if (exceptions.size() > 0) {
      System.err.println("[Generate Error Summary]");
      for (Exception e : exceptions) {
        GLogger.error("[GENERATE ERROR]:",e);
      }
    }
    
    System.out.println("* End generate " + displayText + ",时间:" + LocalDateTime.now());
  }

  public void clean() throws IOException {
    Generator g = createGeneratorForDbTable();
    g.clean();
  }

  public void deleteOutRootDir() throws IOException {
    generator.deleteOutRootDir();
  }

  /**
   * 创建配置生成对象
   * @return 生成对象
   */
  public Generator createGeneratorForDbTable() {
    Generator g = getGenerator();
    g.setRemoveExtensions(GeneratorProperties.getProperty("generator_removeExtensions"));
    return g;
  }

  private Generator createGeneratorForJavaClass() {
    Generator g = new Generator();
    g.setTemplateRootDir(new File("template/javaclass").getAbsoluteFile());
    g.setOutRootDir(GeneratorProperties.getRequiredProperty("outRoot"));
    g.setRemoveExtensions(GeneratorProperties.getProperty("generator_removeExtensions"));
    return g;
  }

  /**
   * 
   * 生成模型
   *
   */
  public static class GeneratorModel {
    private  Map filePathModel;
    private  Map templateModel;

    public GeneratorModel(Map templateModel, Map filePathModel) {
      this.templateModel = templateModel;
      this.filePathModel = filePathModel;
    }

    /**
     * 生成模型
     * @param table 表
     * @return 生成模型
     */
    public static GeneratorModel newFromTable(Table table) {
      Map<Object,Object> templateModel = new HashMap<>();
      templateModel.putAll(GeneratorProperties.getProperties());
      templateModel.put("table", table);
      templateModel.putAll(getShareVars());

      Map<Object,Object> filePathModel = new HashMap<>();
      filePathModel.putAll(GeneratorProperties.getProperties());
      filePathModel.putAll(BeanHelper.describe(table));
      filePathModel.putAll(getShareVars());
      return new GeneratorModel(templateModel, filePathModel);
    }

    public static GeneratorModel newFromClass(Class clazz) {
      Map<Object,Object> templateModel = new HashMap<>();
      templateModel.putAll(GeneratorProperties.getProperties());
      templateModel.put("clazz", new JavaClass(clazz));
      templateModel.putAll(getShareVars());

      Map<Object,Object> filePathModel = new HashMap<>();
      filePathModel.putAll(GeneratorProperties.getProperties());
      filePathModel.putAll(BeanHelper.describe(clazz));
      filePathModel.putAll(getShareVars());
      return new GeneratorModel(templateModel, filePathModel);
    }
  }

  public static Map<Object,Object> getShareVars() {
    Map<Object,Object> templateModel = new HashMap<>();
    templateModel.putAll(System.getProperties());
    templateModel.putAll(GeneratorProperties.getProperties());
    templateModel.put("env", System.getenv());
    templateModel.put("now", new Date());
    templateModel.put(GeneratorConstants.DATABASE_TYPE.code,
        GeneratorProperties.getDatabaseType(GeneratorConstants.DATABASE_TYPE.code));
    templateModel.putAll(GeneratorContext.getContext());
    templateModel.putAll(getToolsMap());
    return templateModel;
  }

  /** 得到模板可以引用的工具类 */
  private static Map<Object,Object> getToolsMap() {
    Map<Object,Object> toolsMap = new HashMap<>();
    String[] tools = GeneratorProperties.getStringArray(GeneratorConstants.GENERATOR_TOOLS_CLASS);
    Arrays.asList(tools).forEach(className -> {
    	try {
            Object instance = ClassHelper.newInstance(className);
            toolsMap.put(Class.forName(className).getSimpleName(), instance);
            GLogger.debug("put tools class:" + className + " with key:" + Class.forName(className).getSimpleName());
          } catch (Exception e) {
            GLogger.error("cannot load tools by className:" + className + " cause:" + e);
          }
    });
    
    return toolsMap;
  }

}