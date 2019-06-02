package com.lj.app.core.common.generator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * 
 * 代码生成器Frame
 *
 */
public abstract class CommonGeneratorMainFrame extends JFrame  {

  /**
   * 标题
   */
  protected JLabel titleInformation;

  /**
   * jdbc.url
   */
  protected JLabel jdbcUrl;
  
  /**
   * jdbc.username
   */
  protected JLabel jdbcUsername;
  /**
   * jdbc.password
   */
  protected JLabel jdbcPassword;
  
  /**
   * 模板目录
   */
  protected JLabel templateDir;
  /**
   * schema
   */
  protected JLabel schema;

  /**
   * 包目录
   * 
   */
  protected JLabel basepackage;

  /**
   * 输出目录
   */
  protected JLabel outRoot;

  /**
   * 表
   */
  protected JLabel table;

  /**
   * jdbcUrl
   */
  protected JTextField jdbcUrlTextFiled;
  /**
   * jdbcUsername
   */
  protected JTextField jdbcUsernameTextFiled;
  /**
   * jdbcPassword
   */
  protected JTextField jdbcPasswordTextFiled;
  
  /**
   * 模板目录
   */
  protected JTextField templateDirTextFiled;

  /**
   * schema
   */
  protected JTextField schemaTextField;

  /**
   * 包目录
   */
  protected JTextField basepackageTextField;

  /**
   * 输出目录
   */
  protected JTextField outRootTextField;

  /**
   * 表
   */
  protected JTextField tableTextField;
  
  /**
   * jdbc.Url
   */
  protected String jdbcUrlProp;
  
  /**
   * jdbc.username
   */
  protected String jdbcUsernameProp;
  /**
   * jdbc.password
   */
  protected String jdbcPasswordProp;

  /**
   * 模板目录
   */
  protected String templateDirProp;
  /**
   * schema
   */
  protected String schemaProp;
  /**
   * package
   */
  protected String basepackageProp;
  /**
   * 输出目录
   */
  protected String outRootProp;
  
  /**
   * 清空输出目录
   */
  protected  JCheckBox outRootProCheckBox;
  
  /**
   * 表名
   */
  protected String tableProp;

  /**
   * 执行按钮
   */
  protected JButton submit;

  /**
   * 执行结果
   */
  protected JTextArea result;
  
  /**
   * 查询
   */
  protected JButton btnQuery;
  
  /**
   * 表格数据
   */
  protected JTable   jTable;
  
  /**
   * 表格列
   */
  protected Vector<Object> vData = new Vector();
  
  /**
   * 表格列名称
   */
  protected Vector<Object> vName = new Vector(); 

  protected GridBagLayout g = new GridBagLayout();

  protected GridBagConstraints c = new GridBagConstraints();

  /**
   * 
   * 代码生成器Frame
   *
   */
  public CommonGeneratorMainFrame(String str) {
    super(str);

    setSize(1200, 1024);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(g);

    getProperties();
    // 调用方法

    addComponent();

    addActionListener();

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
  public abstract void addComponent() ;
  
  /**
   * 添加抽象action监听
   */
  public abstract void addActionListener();
  
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
   * 表格根据内容自适应列宽
   * @param myTable
   */
  public void FitTableColumns(JTable myTable){
      JTableHeader header = myTable.getTableHeader();
         int rowCount = myTable.getRowCount();
         Enumeration columns = myTable.getColumnModel().getColumns();
         while(columns.hasMoreElements()){
             TableColumn column = (TableColumn)columns.nextElement();
             int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
             int width = (int)myTable.getTableHeader().getDefaultRenderer()
                     .getTableCellRendererComponent(myTable, column.getIdentifier()
                             , false, false, -1, col).getPreferredSize().getWidth();
             for(int row = 0; row<rowCount; row++){
                 int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
                   myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                 width = Math.max(width, preferedWidth);
             }
             header.setResizingColumn(column); // 此行很重要
             column.setWidth(width+myTable.getIntercellSpacing().width);
         }
 }
}
