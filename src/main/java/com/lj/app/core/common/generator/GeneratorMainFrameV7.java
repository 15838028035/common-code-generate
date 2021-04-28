package com.lj.app.core.common.generator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.util.BeanHelper;
import com.lj.app.core.common.generator.util.FileHelper;
import com.lj.app.core.common.generator.util.FreemarkerHelper;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.StringHelper;
import com.lj.app.core.common.generator.util.StringUtil;
import com.lj.app.core.common.generator.util.ZipUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 代码生成器Frame
 *
 */
public class GeneratorMainFrameV7 extends CommonGeneratorMainFrame  {

	private String removeExtensions = GeneratorProperties.getProperty(GeneratorConstants.GENERATOR_REMOVE_EXTENSIONS);
	
    /**
     * 
     * 代码生成器Frame
     *
     */
    public GeneratorMainFrameV7(String str) {
      super(str);
    }
    
  /**
   * 添加组件
   */
  @Override
  public void addComponent() {
    
    jdbcUrl = new JLabel("JdbcUrl：");
    add(g, c, jdbcUrl, 0, 1, 1, 1);
    jdbcUrlTextFiled = new JTextField(50);
    jdbcUrlTextFiled.setText(jdbcUrlProp);
    add(g, c, jdbcUrlTextFiled, 1, 1, 1, 1);
    
    jdbcUsername = new JLabel("用户名：");
    add(g, c, jdbcUsername, 0, 2, 1, 1);
    jdbcUsernameTextFiled = new JTextField(50);
    jdbcUsernameTextFiled.setText(jdbcUsernameProp);
    add(g, c, jdbcUsernameTextFiled, 1, 2, 1, 1);
    
    jdbcPassword = new JLabel("密码：");
    add(g, c, jdbcPassword, 0, 3, 1, 1);
    jdbcPasswordTextFiled = new JTextField(50);
    jdbcPasswordTextFiled.setText(jdbcPasswordProp);
    add(g, c, jdbcPasswordTextFiled, 1, 3, 1, 1);

    templateDir = new JLabel("模板目录：");

    add(g, c, templateDir, 0, 4, 1, 1);

    templateDirTextFiled = new JTextField(50);
    templateDirTextFiled.setText(templateDirProp);

    add(g, c, templateDirTextFiled, 1, 4, 1, 1);

    schema = new JLabel("schema：");
    add(g, c, schema, 0, 5, 1, 1);

    schemaTextField = new JTextField(50);
    schemaTextField.setText(schemaProp);
    add(g, c, schemaTextField, 1, 5, 2, 1);

    basepackage = new JLabel("默认包名：");
    add(g, c, basepackage, 0, 6, 1, 1);

    basepackageTextField = new JTextField(50);
    basepackageTextField.setText(basepackageProp);
    add(g, c, basepackageTextField, 1, 6, 2, 1);

    outRoot = new JLabel("输出目录：");
    add(g, c, outRoot, 0, 7, 1, 1);

    outRootTextField = new JTextField(50);
    outRootTextField.setText(outRootProp);
    add(g, c, outRootTextField, 1, 7, 2, 1);
    
    outRootProCheckBox = new JCheckBox("清空输出目录",true);
    
    add(g, c, outRootProCheckBox, 1, 8, 2, 1);

    table = new JLabel("查询表名：");
    add(g, c, table, 0, 9, 1, 1);

    tableTextField = new JTextField(50);
    tableTextField.setText(tableProp);
    add(g, c, tableTextField, 1, 9, 2, 1);

    btnQuery = new JButton("查询");

    c.insets = new Insets(9, 10, 4, 0);

    add(g, c, btnQuery, 1, 10, 1, 1);
    
    
    vName.add("复选框");
    vName.add("行号");
    vName.add("表名称");
    vName.add("备注");
    vName.add("自定义包名称");
    vName.add("排序编号");
    
     jTable = new JTable(new DefaultTableModel(vData , vName));
     
     jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
     
     jTable.setPreferredScrollableViewportSize(new Dimension(1000, 30));
     
     
     jTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer(){
    
        /*(non-Javadoc)
        * 此方法用于向方法调用者返回某一单元格的渲染器（即显示数据的组建--或控件）
        * 可以为JCheckBox JComboBox JTextArea 等
        * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
        */
       @Override
       public Component getTableCellRendererComponent(JTable table,
               Object value, boolean isSelected, boolean hasFocus,
               int row, int column) {
           // 创建用于返回的渲染组件
           JCheckBox ck = new JCheckBox();
           // 使具有焦点的行对应的复选框选中                     
           ck.setSelected(isSelected);
           ck.setOpaque(false);
           // 设置单选box.setSelected(hasFocus);
           // 使复选框在单元格内居中显示
           ck.setHorizontalAlignment((int) 0.5f);
           return ck;
        }});
   
     
     JScrollPane jScrollPane = new JScrollPane(jTable);
     
     jScrollPane.setPreferredSize(new Dimension(600, 400));
    
    add(g, c,  jScrollPane, 0, 200, 600, 400,GridBagConstraints.CENTER);
    
    
    TableColumn column = null;  
    for (int i = 0; i < jTable.getColumnModel().getColumnCount(); i++) {  
        column = jTable.getColumnModel().getColumn(i);  
        column.setPreferredWidth(100);
    }  
    
    jTable.setFillsViewportHeight(true);  
    jTable.updateUI(); 
    
    
    submit = new JButton("生成");

    c.insets = new Insets(8, 2, 4, 0);

    add(g, c, submit, 1, 1600, 1, 1);

    result = new JTextArea(5, 50);

    add(g, c, result, 0, 1800, 5, 2);

  }

  @Override
  public void addActionListener() {
        btnQuery.addActionListener(new BtnQueryListener());
        submit.addActionListener(new BtnSubmitListener()); 
  }
  
  /**
   * 
   * 查询监听器
   *
   */
  public class BtnQueryListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String outRootStr = outRootTextField.getText();
			String tableName = tableTextField.getText();

			String jdbcUrl = jdbcUrlTextFiled.getText();
			String jdbcUsername = jdbcUsernameTextFiled.getText();
			String jdbcPasswordText = jdbcPasswordTextFiled.getText();
			
			String retMsg = "查询数据库成功";
			
			long startTime = System.currentTimeMillis();
			

			try {

				GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
				GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
				GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
				
				result.setText("正在查询中，请稍等.....");
				
				 List<Table> result = DbTableFactory.getInstance().releaseConnection().getAllTables();
			      
			      //清空容器
				 GLogger.info("VdataSize{0}", vData.size());
				 vData.clear();
				 
				 // 性能优化，不要再for循环中创建对象
				 Vector<Object> vTmp = null;
			      for (int i = 0; i < result.size(); i++) {
				        Table table =  result.get(i);
				        
				        if(StringUtil.isBlank(tableName)) {
				  	         vTmp = new Vector<>();
				  	        vTmp.add(i+1);
				            vTmp.add(i+1);
				            vTmp.add(table.getSqlName());
				            vTmp.add(table.getRemarks());
				            vTmp.add("");
				            vTmp.add(i+1);
				            
				            vData.add(vTmp);
				        }
				        
				        if(StringUtil.isNotBlank(tableName) && table.getSqlName().toUpperCase().contains(tableName.toUpperCase())) {
				             vTmp = new Vector<>();
				             vTmp.add(i+1);
				             vTmp.add(i+1);
				            vTmp.add(table.getSqlName());
				            vTmp.add(table.getRemarks());
				            vTmp.add("");
				            vTmp.add(i+1);
				            
				            vData.add(vTmp);
			          }
			       
			      }
			      
			      changeAndSortTable(jTable, vName, vData);
			      
			      GLogger.info("开始更新表格数据,.........表格数据条数:" + vData.size());
			      jTable.updateUI();

			} catch (Exception e) {
				retMsg = "查询数据库出现异常" + outRootStr + ",异常信息:" + e.getMessage();

				GLogger.error("查询数据库出现异常", e);
			}
			
			long endTime = System.currentTimeMillis();
			String executeTime = "查询时间:" +( endTime-startTime) +"ms \r\n";
			
			result.setText(executeTime + retMsg);

		}
	}
  
	public class BtnSubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String templateDirStr = templateDirTextFiled.getText();
			String basepackageStr = basepackageTextField.getText();
			String outRootStr = outRootTextField.getText();

			GLogger.info("templateDirStr:" + templateDirStr);

			String retMsg = "文件生成成功,文件目录:\n" + outRootStr;
			long startTime = System.currentTimeMillis();

			try {
				
				if(jTable.getSelectedRows().length<=0) {
					JOptionPane.showMessageDialog(null, "请选择至少一条记录", "提示信息",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				result.setText("正在执行中，请稍等.....");
				
				GeneratorProperties.setProperty("basepackage", basepackageStr);
		  		GeneratorProperties.setProperty("basepackage_dir",
                GeneratorProperties.getProperty("basepackage").replace(".", "/"));
		  		
				GeneratorProperties.setProperty("outRoot", outRootStr);
				
				GeneratorProductAndConsumerFacade g = new GeneratorProductAndConsumerFacade();
				//清空输出目录
				if(outRootProCheckBox.isSelected()) {
					g.clean();
				}
					
				g.getGenerator().setTemplateRootDir(templateDirStr);
				
				List<Table> tableDataList = new ArrayList();
				List<Table> tableDataList2 = new ArrayList();
				 Table tableObj = null;
				 for(int rowindex : jTable.getSelectedRows()){
					 tableObj = new Table();
					 		
					 GLogger.info("选表格数据1{0} {1}" ,rowindex ,jTable.getValueAt(rowindex, 1));
					 GLogger.info("选表格数据2{0} {1}" ,rowindex , jTable.getValueAt(rowindex, 2));
					 GLogger.info("选表格数据3 {0} {1}" , rowindex , jTable.getValueAt(rowindex, 3));
					 
					tableObj.setSqlName((String)jTable.getValueAt(rowindex, 2));
					tableObj.setSortNo(Integer.valueOf(jTable.getValueAt(rowindex, 5).toString()));
						
				    tableDataList.add(tableObj);
				 }
				 
				 //自定义按照sortNo排序
			        Collections.sort(tableDataList,new Comparator<Table>(){
			            @Override
			            public int compare(Table o1, Table o2) {
			            	Table dableViewData1 = (Table)o1;
			            	Table dableViewData2 = (Table)o2;
			                 return dableViewData2.getSortNo().compareTo(dableViewData1.getSortNo());
			            }
			         });

			        tableDataList.forEach(table->{
			        	System.out.println(table);
			        	String sqlTableName = table.getSqlName();
			        	try{
			        		table = DbTableFactory.getInstance().getTable(sqlTableName);
			        		tableDataList2.add(table);
			        	}catch(Exception e) {
			        		GLogger.error("查询数据库失败", e);
			        	}
			        });
			        
			        
			        Map templateModel = new HashMap();
			        templateModel.put("tableList", tableDataList2);
			        
			        Map filePathModel = new HashMap<>();
			        filePathModel.putAll(GeneratorProperties.getProperties());
			        filePathModel.putAll(BeanHelper.describe(table));
			        
			        List<Exception> exceptionList =  generateBy( g.getGenerator().getTemplateRootDirs(),  templateModel,filePathModel,g.getGenerator() );
			        
				Runtime.getRuntime().exec("cmd.exe /c start " + outRootStr);
			} catch (Exception e) {
				retMsg = "文件生成失败,文件目录:\n" + outRootStr + ",异常信息:" + e.getMessage();

				GLogger.error("文件生成失败", e);
			}
			
			GLogger.info("");
			GLogger.info("***************************************************************");
			GLogger.info("*********************Generate Success**************************");

			long endTime = System.currentTimeMillis();
			String executeTime = "生成表个数:" + jTable.getSelectedRows().length + "\r \n 执行时间:" +( endTime-startTime) +"ms \r\n";
			
			GLogger.info("生成表个数:{0}" , jTable.getSelectedRows().length);
			GLogger.info("执行时间: {0} ms", endTime-startTime);
			GLogger.info("***************************************************************");
			
			result.setText(executeTime + retMsg);

		}
	}
	
	/**
	 * 填充数据并排序后显示
	 * @param table
	 * @param tableHead
	 * @param data
	 */
	public static void changeAndSortTable(JTable jTable, Vector tableHead,Vector data){
		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel(data, tableHead){
			
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column){
				Class returnValue;
				 if ((column >= 5) && (column < getColumnCount())) {  
                     returnValue = getValueAt(0, column).getClass();  
                 } else {  
                     returnValue = Object.class;  
                 }  
                 return returnValue; 
			}
			
		};
		
		TableSorter tableSorter  = new TableSorter(tableModel);
		
		jTable.setModel(tableSorter);
		
		tableSorter.setTableHeader(jTable.getTableHeader());
		
		 TableColumn column =null;
		 for (int i = 0; i < jTable.getColumnModel().getColumnCount(); i++) {  
			   column = jTable.getColumnModel().getColumn(i);  
		        column.setPreferredWidth(100);
		    }  
		    
	    jTable.setFillsViewportHeight(true);  
	    
	    jTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer(){
	        
	        /*(non-Javadoc)
	        * 此方法用于向方法调用者返回某一单元格的渲染器（即显示数据的组建--或控件）
	        * 可以为JCheckBox JComboBox JTextArea 等
	        * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	        */
	       @Override
	       public Component getTableCellRendererComponent(JTable table,
	               Object value, boolean isSelected, boolean hasFocus,
	               int row, int column) {
	           // 创建用于返回的渲染组件
	           JCheckBox ck = new JCheckBox();
	           // 使具有焦点的行对应的复选框选中                     
	           ck.setSelected(isSelected);
	           ck.setOpaque(false);
	           // 设置单选box.setSelected(hasFocus);
	           // 使复选框在单元格内居中显示
	           ck.setHorizontalAlignment((int) 0.5f);
	           return ck;
	        }});
	    
	    jTable.updateUI(); 

	}

	  public List generateBy(List<File> templateRootDirs,Map templateModel, Map filePathModel, Generator g) throws Exception {
		    if (templateRootDirs.size() == 0)  {
		      throw new IllegalStateException("'templateRootDirs' cannot empty");
		    }

		    templateRootDirs =  processTemplateRootDirs(templateRootDirs); //新增处理zip、jar等模版目录

		    List<Exception> allExceptions = new ArrayList<>();
		    for (int i = 0; i < templateRootDirs.size(); i++) {
		      File templateRootDir = (File) templateRootDirs.get(i);
		      List<Exception> exceptions = generateBy(templateRootDirs,templateRootDir, templateModel, filePathModel,g);
		      allExceptions.addAll(exceptions);
		    }
		    return allExceptions;
		  }
	  
	  /**
	     * 用于子类覆盖,预处理模板目录,如执行文件解压动作 
	     **/
	  protected List<File> processTemplateRootDirs(List<File> templateRootDirs) throws Exception {
	    return unzipIfTemplateRootDirIsZipFile(templateRootDirs);
	  }
	  
	  /**
	   * 解压模板目录,如果模板目录是一个zip,jar文件 . 并且支持指定 zip文件的子目录作为模板目录,通过 !号分隔
	   * 指定zip文件: c:\\some.zip   
	   * 指定zip文件子目录: c:\some.zip!/folder/
	   * @throws MalformedURLException 
	   **/
	  private List<File> unzipIfTemplateRootDirIsZipFile(List<File> templateRootDirs) throws MalformedURLException {
	    List<File> unzipIfTemplateRootDirIsZipFile = new ArrayList<File>();
	    for(int i = 0; i < templateRootDirs.size(); i++) {
	      File file = templateRootDirs.get(i);
	      String templateRootDir = FileHelper.toFilePathIfIsURL(file);
	      
	      String subFolder = "";
	      int zipFileSeperatorIndexOf = templateRootDir.indexOf("!");
	      if(zipFileSeperatorIndexOf >= 0) {
	        subFolder = templateRootDir.substring(zipFileSeperatorIndexOf+1);
	        templateRootDir = templateRootDir.substring(0,zipFileSeperatorIndexOf);
	      }
	      
	      if(new File(templateRootDir).isFile()) {
	        File tempDir = ZipUtils.unzip2TempDir(new File(templateRootDir),"tmp_generator_template_folder_for_zipfile");
	        unzipIfTemplateRootDirIsZipFile.add(new File(tempDir,subFolder));
	      }else {
	          unzipIfTemplateRootDirIsZipFile.add(new File(templateRootDir,subFolder));
	      }
	    }
	    return unzipIfTemplateRootDirIsZipFile;
	}
	  
	
	private  List<Exception> generateBy(List<File> templateRootDirs,File templateRootDir, Map templateModel, Map filePathModel, Generator g) throws Exception {
	    if (templateRootDir == null)  {
	      throw new IllegalStateException("'templateRootDir' must be not null");
	    }
	    System.out
	        .println("-------------------load template from templateRootDir = '" + templateRootDir.getAbsolutePath() + "'");

	    List<File> templateFiles = new ArrayList<>();
	    FileHelper.listFiles(templateRootDir, templateFiles);
	   
	    List<Exception> exceptions = new ArrayList<>();
	    for (int i = 0; i < templateFiles.size(); i++) {
	      File templateFile = (File) templateFiles.get(i);
	      String templateRelativePath = FileHelper.getRelativePath(templateRootDir, templateFile);
	      String outputFilePath = templateRelativePath;
	      if ((templateFile.isDirectory()) || (templateFile.isHidden()))  {
	        continue;
	      }
	      if (templateRelativePath.trim().equals("")) {
	        continue;
	      }
	      if (templateFile.getName().toLowerCase().endsWith(".include")) {
	        System.out.println("[skip]\t\t endsWith '.include' template:" + templateRelativePath);
	      } else {
	        int testExpressionIndex = -1;
	        if ((testExpressionIndex = templateRelativePath.indexOf('@')) != -1) {
	          outputFilePath = templateRelativePath.substring(0, testExpressionIndex);
	          String testExpressionKey = templateRelativePath.substring(testExpressionIndex + 1);
	          Object expressionValue = filePathModel.get(testExpressionKey);
	          if (expressionValue == null) {
	            System.err.println("[not-generate] WARN: test expression is null by key:[" + testExpressionKey
	                + "] on template:[" + templateRelativePath + "]");
	            continue;
	          }
	          if (!"true".equals(String.valueOf(expressionValue))) {
	            System.out.println("[not-generate]\t test expression '@" + testExpressionKey + "' is false,template:"
	                + templateRelativePath);
	            continue;
	          }

	        }

	        for (String removeExtension : removeExtensions.split(",")) {
	            if (outputFilePath.endsWith(removeExtension)) {
	              outputFilePath = outputFilePath.substring(0, outputFilePath.length() - removeExtension.length());
	              break;
	            }
	          }
	        
	        
	        String targetFilename = null;
	        try {
	          // 使freemarker支持过滤,如 ${className?lower_case} 现在为 ${className^lower_case}
	          outputFilePath = outputFilePath.replace('^', '?');

	          targetFilename = getTargetFilename(filePathModel, outputFilePath,templateRootDirs);
	          Configuration conf = newFreeMarkerConfiguration(templateRootDirs, "UTF-8", templateFile.getName());

	          g.generateNewFileOrInsertIntoFile(templateModel, targetFilename, conf, templateRelativePath, outputFilePath);

	        } catch (Exception e) {
	            GLogger.warn("iggnore generate error,template is:" + templateRelativePath + " cause:" + e);
	            throw new RuntimeException(
	                "generate oucur error,templateFile is:" + templateRelativePath + " => " + targetFilename, e);
	        }
	      }
	    }
	    return exceptions;
	  }
	
	  private String getTargetFilename(Map filePathModel, String templateFilepath,List<File> templateRootDirs) throws Exception {
		    StringWriter out = new StringWriter();
		    Template template = new Template("filePath", new StringReader(templateFilepath), newFreeMarkerConfiguration(templateRootDirs));
		    try {
		      template.process(filePathModel, out);
		      return out.toString();
		    } catch (Exception e) {
		      throw new IllegalStateException(
		          "cannot generate filePath from templateFilepath:" + templateFilepath + " cause:" + e);
		    }
		  }
	  

	  private Configuration newFreeMarkerConfiguration(List<File> templateRootDirs) throws IOException {
		    Configuration config = new Configuration();

		    FileTemplateLoader[] templateLoaders = new FileTemplateLoader[templateRootDirs.size()];
		    for (int i = 0; i <templateRootDirs.size(); i++) {
		      templateLoaders[i] = new FileTemplateLoader((File) templateRootDirs.get(i));
		    }
		    MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(templateLoaders);

		    config.setTemplateLoader(multiTemplateLoader);
		    config.setNumberFormat("###############");
		    config.setBooleanFormat("true,false");
		    config.setDefaultEncoding("UTF-8");
		    return config;
		  }
	  
	  /**
	   * 新建模板
	   * @param templateRootDirs 模板目录
	   * @param defaultEncoding 编码
	   * @param templateName 模板名称
	   * @return 配置对象
	   * @throws IOException 异常
	   */
	  public static Configuration newFreeMarkerConfiguration(List<File> templateRootDirs, String defaultEncoding,
	      String templateName) throws IOException {
	    Configuration conf = new Configuration();

	    FileTemplateLoader[] templateLoaders = new FileTemplateLoader[templateRootDirs.size()];
	    for (int i = 0; i < templateRootDirs.size(); i++) {
	      templateLoaders[i] = new FileTemplateLoader((File) templateRootDirs.get(i));
	    }
	    MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(templateLoaders);

	    conf.setTemplateLoader(multiTemplateLoader);
	    conf.setNumberFormat("###############");
	    conf.setBooleanFormat("true,false");
	    conf.setDefaultEncoding(defaultEncoding);

	    List<String> autoIncludes = getParentPaths(templateName, "macro.include");
	    List<String> availableAutoInclude = FreemarkerHelper.getAvailableAutoInclude(conf, autoIncludes);
	    conf.setAutoIncludes(availableAutoInclude);
	    GLogger.info("set Freemarker.autoIncludes:" + availableAutoInclude + " for templateName:" + templateName
	        + " autoIncludes:" + autoIncludes);
	    return conf;
	  }
	  
	  /**
	   * 获得路径
	   * @param templateName 模板名称
	   * @param suffix 前缀
	   * @return 路径列表
	   */
	  public static List<String> getParentPaths(String templateName, String suffix) {
	    String [] array = StringHelper.tokenizeToStringArray(templateName, "\\/");
	    List<String> list = new ArrayList<String>();
	    list.add(suffix);
	    list.add(File.separator + suffix);
	    String path = "";
	    for (int i = 0; i < array.length; i++) {
	      path = path + File.separator + array[i];
	      list.add(path + File.separator + suffix);
	    }
	    return list;
	  }
	  	
  /**
   * 执行方法
   * @param args 运行参数
   */
  public static void main(String [] args)  {
    new GeneratorMainFrameV7("代码生成器V7");
  }
}
