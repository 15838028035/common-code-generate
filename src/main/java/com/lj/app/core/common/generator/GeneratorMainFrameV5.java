package com.lj.app.core.common.generator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Column;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.util.GLogger;

/**
 * 
 * 代码生成器Frame
 *
 */
public class GeneratorMainFrameV5 extends CommonGeneratorMainFrame  {
  
    
    
  /**
   * 
   * 代码生成器Frame
   *
   */
  public GeneratorMainFrameV5(String str) {
      super(str,1360,1200);
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
    
    btnQuery = new JButton("查询");

    c.insets = new Insets(8, 10, 4, 0);

    add(g, c, btnQuery, 1, 10, 1, 1);
    
    vName.add("列名");
    vName.add("列类型");
    vName.add("长度");
    vName.add("备注");
    vName.add("列表是否显示");
    vName.add("列表查询类型");
    vName.add("表单是否显示");
    vName.add("表单类型");
    
     jTable = new JTable(new DefaultTableModel(vData , vName));
     
     jTable.setPreferredScrollableViewportSize(new Dimension(900, 30));
   
     JScrollPane jScrollPane = new JScrollPane(jTable);
     
     jScrollPane.setPreferredSize(new Dimension(600, 600));
    
    add(g, c,  jScrollPane, 3, 200, 1000, 600,GridBagConstraints.WEST);
    
    jTable.setFillsViewportHeight(true);  
    jTable.updateUI(); 

	    
	DefaultTreeModel treeModelPointed = new DefaultTreeModel(root);//利用根节点创建树模型，并采用非默认的判断方式
	 jTree = new javax.swing.JTree(treeModelPointed);
	 
	 JScrollPane jTreePane = new JScrollPane(jTree);
     jTreePane.setPreferredSize(new Dimension(200, 600));
     jTreePane.setBackground(Color.BLUE);
     jTreePane.setForeground(Color.green);
	 
    add(g, c, jTreePane, 0, 250, 200, 600,GridBagConstraints.WEST);
    
    
    submit = new JButton("生成");

    c.insets = new Insets(8, 2, 4, 0);

    add(g, c, submit, 1, 1600, 1, 1);

    result = new JTextArea(10, 60);

    add(g, c, new JScrollPane(result), 0, 1800, 10, 2);
        

  }

  /**
   * 添加抽象action监听
   */
  @Override
  public  void addActionListener(){
      btnQuery.addActionListener(new BtnQueryListener());
      submit.addActionListener(new BtnSubmitListener());
      jTree.addTreeSelectionListener(new JTreeTreeSelectionListener());
  }

  public class JTreeTreeSelectionListener implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode note=(DefaultMutableTreeNode) jTree.getLastSelectedPathComponent(); //返回最后选中的结点        
        String name=note.toString();//获得这个结点的名称
        System.out.println("name===="+name);
        
        
        if("ROOT".equalsIgnoreCase(name)) {
        	return ;
        }
        
        String outRootStr = outRootTextField.getText();
		String tableName = name.trim();

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
			
		      //清空容器
			 vData.clear();
			 
			 StringBuilder sb = new StringBuilder();
			
			 
			 // 性能优化，不要再for循环中创建对象
			 Vector<Object> vTmp = null;
		    	  
		    	  Table queryTable = DbTableFactory.getInstance().releaseConnection().getTable(tableName);
		    	  
		    	  Set<Column> tableColumns = queryTable.getColumns();
		    	  
		    	  Iterator<Column> it = tableColumns.iterator();
		    	  
		    	  while(it.hasNext()){
		    		  Column columnObj = (Column)it.next();
		    		  	vTmp = new Vector();
			            vTmp.add(columnObj.getSqlName());
			            vTmp.add(columnObj.getSqlTypeName());
			            vTmp.add(columnObj.getSize());
			            vTmp.add(columnObj.getRemarks());
			            
			            vTmp.add(true);//列是否显示
			            jTable.getColumnModel().getColumn(4).setCellEditor(jTable.getDefaultEditor(Boolean.class));
			            jTable.getColumnModel().getColumn(4).setCellRenderer(jTable.getDefaultRenderer(Boolean.class));
			            
			            if(columnObj.getIsStringColumn()){
			            	vTmp.add("like");
			            }else {
			            	vTmp.add("=");
			            }
			            
			            JComboBox c = new JComboBox();
                c.addItem("=");
                 c.addItem("like");
                jTable.getColumnModel().getColumn(5) .setCellEditor(new DefaultCellEditor(c));
                
			            vTmp.add(true);
			            jTable.getColumnModel().getColumn(6).setCellEditor(jTable.getDefaultEditor(Boolean.class));
                jTable.getColumnModel().getColumn(6).setCellRenderer(jTable.getDefaultRenderer(Boolean.class));
			            
			            if(columnObj.getIsDateTimeColumn()){
			            	vTmp.add("date");
			            }else {
			            	vTmp.add("input");
			            }
			            
			            
			     JComboBox c2 = new JComboBox();
                c2.addItem("input");
                c2.addItem("textarea");
                c2.addItem("select");
                c2.addItem("radio");
                c2.addItem("date");
                c2.addItem("checkbox");
                c2.addItem("file");
                jTable.getColumnModel().getColumn(7) .setCellEditor(new DefaultCellEditor(c2));
                
			            vData.add(vTmp);
		    	  }
		    	   
			      GLogger.info("开始更新表格数据,.........表格数据条数:" + vData.size());
			   
			      jTable.updateUI();
			      FitTableColumns(jTable);

		} catch (Exception ex) {
			retMsg = "查询数据库出现异常" + outRootStr + ",异常信息:" + ex.getMessage();

			GLogger.error("查询数据库出现异常", ex);
		}
		
		long endTime = System.currentTimeMillis();
		String executeTime = "查询时间:" +( endTime-startTime) +"ms \r\n";
		
		result.setText(executeTime + retMsg);
	}
	  
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
				 List<Table> resultList = DbTableFactory.getInstance().releaseConnection().getAllTables();
				 root.removeAllChildren();
			      for (Table tableTmp: resultList) {
				        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(tableTmp.getSqlName(),true);
				        root.add(treeNode);
			      }
			    
			      
		      GLogger.info("开始更新表格数据,.........表格数据条数:" + resultList.size());
		      jTree.updateUI(); //刷新jTree结构

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
				
				  DefaultMutableTreeNode note=(DefaultMutableTreeNode) jTree.getLastSelectedPathComponent(); //返回最后选中的结点        
			        String name=note.toString();//获得这个结点的名称
				
				result.setText("正在执行中，请稍等.....");
				
				GeneratorProperties.setProperty("outRoot", outRootStr);
				
				GeneratorProductAndConsumerFacade g = new GeneratorProductAndConsumerFacade();
				
				//清空输出目录
				if(outRootProCheckBox.isSelected()) {
					g.clean();
				}
					
				g.getGenerator().setTemplateRootDir(templateDirStr);
				
				//确保有序
			 	Set<Column> tableColumSet = new LinkedHashSet<>(); 
			 
		  		GeneratorProperties.setProperty("basepackage", basepackageStr);
		  		GeneratorProperties.setProperty("basepackage_dir",
                GeneratorProperties.getProperty("basepackage").replace(".", "/"));
			      
			    Table table = DbTableFactory.getInstance().getTable(name);
			      
			     Set<Column> tableColumns = table.getColumns();
		    	  
		    	  Iterator<Column> it = tableColumns.iterator();
		    	  int i = 0;
		    	  while(it.hasNext()){
		    		  	Column columnObj = (Column)it.next();
		    		  	Boolean listIsShow = (Boolean)jTable.getValueAt(i, 4);
		    		  	String  listMatchType = (String)jTable.getValueAt(i, 5);
		    		  	Boolean formIsShow = (Boolean)jTable.getValueAt(i, 6);
		    		  	String formShowType = (String)jTable.getValueAt(i, 7);
		    		  	
		    			columnObj.setListIsShow(listIsShow);
		    		  	columnObj.setListMatchType(listMatchType);
		    		    columnObj.setFormIsShow(formIsShow);
		    		  	columnObj.setFormShowType(formShowType);
		    		  	tableColumSet.add(columnObj);
		    		  	
			            i++;
		    	  }
			      
			      table.setColumns(tableColumSet);
			      
			      g.generateByTable(g.createGeneratorForDbTable(), table);
				      
				Runtime.getRuntime().exec("cmd.exe /c start " + outRootStr);
			} catch (Exception e) {
				retMsg = "文件生成失败,文件目录:\n" + outRootStr + ",异常信息:" + e.getMessage();

				GLogger.error("文件生成失败", e);
			}
			
			GLogger.info("");
			GLogger.info("***************************************************************");
			GLogger.info("*********************Generate Success**************************");
			GLogger.info("***************************************************************");

			long endTime = System.currentTimeMillis();
			String executeTime = "生成表个数:" + jTable.getSelectedRows().length + "\r \n 执行时间:" +( endTime-startTime) +"ms \r\n";
			
			result.setText(executeTime + retMsg);

		}
	}
	
  /**
   * 执行方法
   * @param args 运行参数
   */
  public static void main(String [] args)  {
    new GeneratorMainFrameV5("代码生成器V5");
  }
}
