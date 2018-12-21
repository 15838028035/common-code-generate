package com.lj.app.core.common.generator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.StringUtil;

/**
 * 
 * 代码生成器Frame
 *
 */
public class GeneratorMainFrameV2 extends JFrame  {

  /**
   * 标题
   */
  private JLabel titleInformation;

  /**
   * jdbc.url
   */
  private JLabel jdbcUrl;
  
  /**
   * jdbc.username
   */
  private JLabel jdbcUsername;
  /**
   * jdbc.password
   */
  private JLabel jdbcPassword;
  
  /**
   * 模板目录
   */
  private JLabel templateDir;
  /**
   * schema
   */
  private JLabel schema;

  /**
   * 包目录
   * 
   */
  private JLabel basepackage;

  /**
   * 输出目录
   */
  private JLabel outRoot;

  /**
   * 表
   */
  private JLabel table;

  /**
   * jdbcUrl
   */
  private JTextField jdbcUrlTextFiled;
  /**
   * jdbcUsername
   */
  private JTextField jdbcUsernameTextFiled;
  /**
   * jdbcPassword
   */
  private JTextField jdbcPasswordTextFiled;
  
  /**
   * 模板目录
   */
  private JTextField templateDirTextFiled;

  /**
   * schema
   */
  private JTextField schemaTextField;

  /**
   * 包目录
   */
  private JTextField basepackageTextField;

  /**
   * 输出目录
   */
  private JTextField outRootTextField;

  /**
   * 表
   */
  private JTextField tableTextField;
  
  /**
   * jdbc.Url
   */
  private String jdbcUrlProp;
  
  /**
   * jdbc.username
   */
  private String jdbcUsernameProp;
  /**
   * jdbc.password
   */
  private String jdbcPasswordProp;

  /**
   * 模板目录
   */
  private String templateDirProp;
  /**
   * schema
   */
  private String schemaProp;
  /**
   * package
   */
  private String basepackageProp;
  /**
   * 输出目录
   */
  private String outRootProp;
  /**
   * 表名
   */
  private String tableProp;
  
  /**
   * 查询
   */
  private JButton btnQuery;
  
  /**
   * 表格数据
   */
  private JTable   jTable;

  /**
   * 执行按钮
   */
  private JButton submit;

  /**
   * 执行结果
   */
  private JTextArea result;
  
  /**
   * 表格列
   */
  private Vector vData = new Vector();
  
  /**
   * 表格列名称
   */
  private Vector vName = new Vector(); 
  

  private GridBagLayout g = new GridBagLayout();

  private GridBagConstraints c = new GridBagConstraints();

  /**
   * 
   * 代码生成器Frame
   *
   */
  public GeneratorMainFrameV2(String str) {
    super(str);

    setSize(1200, 900);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(g);

    getProperties();
    // 调用方法

    addComponent();
    
    btnQuery.addActionListener(new BtnQueryListener());

    submit.addActionListener(new BtnSubmitListener());

    setVisible(true);

    setLocationRelativeTo(null);// 设居中显示;

  }

  /**
   * 获得配置属性
   */
  public void getProperties() {
    templateDirProp = GeneratorProperties.getProperty("template");
    schemaProp = GeneratorProperties.getProperty("TABLE_SCHEM");
    basepackageProp = GeneratorProperties.getProperty("basepackage");
    outRootProp = GeneratorProperties.getProperty("outRoot");
    tableProp = GeneratorProperties.getProperty("TABLE_NAME");
    jdbcUrlProp = GeneratorProperties.getProperty("jdbc.url");
    jdbcUsernameProp = GeneratorProperties.getProperty("jdbc.username");
    jdbcPasswordProp = GeneratorProperties.getProperty("jdbc.password");
  }

  /**
   * 添加组件
   */
  public void addComponent() {
    
    jdbcUrl = new JLabel("JdbcUrl：");
    add(g, c, jdbcUrl, 0, 1, 1, 1);
    jdbcUrlTextFiled = new JTextField(100);
    jdbcUrlTextFiled.setText(jdbcUrlProp);
    add(g, c, jdbcUrlTextFiled, 1, 1, 1, 1);
    
    jdbcUsername = new JLabel("用户名：");
    add(g, c, jdbcUsername, 0, 2, 1, 1);
    jdbcUsernameTextFiled = new JTextField(100);
    jdbcUsernameTextFiled.setText(jdbcUsernameProp);
    add(g, c, jdbcUsernameTextFiled, 1, 2, 1, 1);
    
    jdbcPassword = new JLabel("密码：");
    add(g, c, jdbcPassword, 0, 3, 1, 1);
    jdbcPasswordTextFiled = new JTextField(100);
    jdbcPasswordTextFiled.setText(jdbcPasswordProp);
    add(g, c, jdbcPasswordTextFiled, 1, 3, 1, 1);
    

    templateDir = new JLabel("模板目录：");

    add(g, c, templateDir, 0, 4, 1, 1);

    templateDirTextFiled = new JTextField(100);
    templateDirTextFiled.setText(templateDirProp);

    add(g, c, templateDirTextFiled, 1, 4, 1, 1);

    schema = new JLabel("schema：");
    add(g, c, schema, 0, 5, 1, 1);

    schemaTextField = new JTextField(100);
    schemaTextField.setText(schemaProp);
    add(g, c, schemaTextField, 1, 5, 2, 1);

    basepackage = new JLabel("默认包名：");
    add(g, c, basepackage, 0, 6, 1, 1);

    basepackageTextField = new JTextField(100);
    basepackageTextField.setText(basepackageProp);
    add(g, c, basepackageTextField, 1, 6, 2, 1);

    outRoot = new JLabel("输出目录：");
    add(g, c, outRoot, 0, 7, 1, 1);

    outRootTextField = new JTextField(100);
    outRootTextField.setText(outRootProp);
    add(g, c, outRootTextField, 1, 7, 2, 1);

    table = new JLabel("查询表名：");
    add(g, c, table, 0, 8, 1, 1);

    tableTextField = new JTextField(100);
    tableTextField.setText(tableProp);
    add(g, c, tableTextField, 1, 8, 2, 1);

    btnQuery = new JButton("查询");

    c.insets = new Insets(8, 10, 4, 0);

    add(g, c, btnQuery, 1, 9, 1, 1);
    
    
    vName.add("");
    vName.add("行号");
    vName.add("表名称");
    vName.add("备注");
    vName.add("自定义包名称");
    
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
       
    	 
   
     
     JTableHeader head=jTable.getTableHeader();
     
    add(g, c,  head, 0, 16, 200, 100,GridBagConstraints.PAGE_START);
    
    add(g, c,  jTable, 0, 20, 200, 140,GridBagConstraints.CENTER);
    
    TableColumn column = null;  
    for (int i = 0; i < jTable.getColumnModel().getColumnCount(); i++) {  
        column = jTable.getColumnModel().getColumn(i);  
        column.setPreferredWidth(200);
    }  
    
    jTable.setFillsViewportHeight(true);  
    jTable.updateUI(); 
    
    
    submit = new JButton("生成");

    c.insets = new Insets(8, 22, 4, 0);

    add(g, c, submit, 1, 180, 1, 1);

    result = new JTextArea(5, 100);

    add(g, c, result, 0, 200, 5, 2);

  }

  /**
   * 添加布局
   * @param g 布局
   * @param c 布局
   * @param jc 布局
   * @param x x
   * @param y y
   * @param gw 宽度
   * @param gh 高度
   */
  public void add(GridBagLayout g, GridBagConstraints c, JComponent jc, int x, int y, int gw, int gh) {

    c.gridx = x;
    c.gridy = y;
    c.anchor = GridBagConstraints.WEST;
    c.gridwidth = gw;
    c.gridheight = gh;

    g.setConstraints(jc, c);
    add(jc);
  }
  
  /**
   * 添加布局
   * @param g 布局
   * @param c 布局
   * @param jc 布局
   * @param x x
   * @param y y
   * @param gw 宽度
   * @param gh 高度
   */
  public void add(GridBagLayout g, GridBagConstraints c, JComponent jc, int x, int y, int gw, int gh, int anchor) {

    c.gridx = x;
    c.gridy = y;
    c.anchor = anchor;
    c.gridwidth = gw;
    c.gridheight = gh;

    g.setConstraints(jc, c);
    add(jc);
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

			try {

				GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
				GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
				GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
				
				 List<Table> result = DbTableFactory.getInstance().releaseConnection().getAllTables();
			      
			      //清空容器
				 GLogger.info("VdataSize:" + vData.size());
				 vData.clear();
				 
				 //初始化添加
			 	Vector vTmpInit = new Vector();
			 	vTmpInit.add(-1);
			 	vTmpInit.add(-1);
	            vTmpInit.add("表名称");
	            vTmpInit.add("备注");
	            vTmpInit.add("自定义包名称");
	            
	            vData.add(vTmpInit);
			     
			      for (int i = 0; i < result.size(); i++) {
				        Table table = (Table) result.get(i);
				        
				        if(StringUtil.isBlank(tableName)) {
				  	        Vector vTmp = new Vector();
				  	        vTmp.add(i);
				            vTmp.add(i);
				            vTmp.add(table.getSqlName());
				            vTmp.add(table.getRemarks());
				            vTmp.add("");
				            
				            vData.add(vTmp);
				        }
				        
				        if(StringUtil.isNotBlank(tableName) && table.getSqlName().contains(tableName)) {
				            Vector vTmp = new Vector();
				            vTmp.add(i);
				            vTmp.add(i);
				            vTmp.add(table.getSqlName());
				            vTmp.add(table.getRemarks());
				            vTmp.add("");
				            
				            vData.add(vTmp);
			          }
			       
			      }
			      
			      GLogger.info("开始更新表格数据,.........表格数据条数:" + vData.size());
			      jTable.updateUI();

			} catch (Exception e) {
				retMsg = "查询数据库出现异常" + outRootStr + ",异常信息:" + e.getMessage();

				GLogger.error("查询数据库出现异常", e);
			}
			result.setText(retMsg);

		}
	}
  
	public class BtnSubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String templateDirStr = templateDirTextFiled.getText();
			String basepackageStr = basepackageTextField.getText();
			String outRootStr = outRootTextField.getText();
			String tableStr = tableTextField.getText();

			System.out.println("templateDirStr:" + templateDirStr);

			String retMsg = "文件生成成功,文件目录:\n" + outRootStr;

			try {
				
				GeneratorProductAndConsumerFacade g = new GeneratorProductAndConsumerFacade();
				g.clean();
					
				g.getGenerator().setTemplateRootDir(templateDirStr);
				
				 for(int rowindex : jTable.getSelectedRows()){
					 		
					 GLogger.info("选表格数据1" + rowindex + " " + jTable.getValueAt(rowindex, 1));
					 GLogger.info("选表格数据2" + rowindex + " " + jTable.getValueAt(rowindex, 2));
					 GLogger.info("选表格数据3" + rowindex + " " + jTable.getValueAt(rowindex, 3));
					

						g.generateByTable(tableStr);
						TableViewData tableViewData = new TableViewData();
						tableViewData.setTableName((String)jTable.getValueAt(rowindex, 2));
						
						if(StringUtil.isNotBlank((String)jTable.getValueAt(rowindex, 4))) {
							basepackageStr = (String)jTable.getValueAt(rowindex, 4);
						}
						
						tableViewData.setBasepackage(basepackageStr);
						
						g.generateByTable(tableViewData);
				 }

				Runtime.getRuntime().exec("cmd.exe /c start " + outRootStr);
			} catch (Exception e) {
				retMsg = "文件生成失败,文件目录:\n" + outRootStr + ",异常信息:" + e.getMessage();

				GLogger.error("文件生成失败", e);
			}
			
			GLogger.info("");
			GLogger.info("***************************************************************");
			GLogger.info("*********************Generate Success**************************");
			GLogger.info("***************************************************************");

			result.setText(retMsg);

		}
	}

  /**
   * 执行方法
   * @param args 运行参数
   */
  public static void main(String [] args)  {
    new GeneratorMainFrameV2("代码生成器V2");
  }
}