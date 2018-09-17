package com.lj.app.core.common.generator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lj.app.core.common.generator.util.GLogger;

/**
 * 
 * 代码生成器Frame
 *
 */
public class GeneratorMainFrame extends JFrame implements ActionListener {

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
   * 执行按钮
   */
  private JButton submit;

  /**
   * 执行结果
   */
  private JTextArea result;

  private GridBagLayout g = new GridBagLayout();

  private GridBagConstraints c = new GridBagConstraints();

  /**
   * 
   * 代码生成器Frame
   *
   */
  public GeneratorMainFrame(String str) {
    super(str);

    setSize(800, 600);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(g);

    getProperties();
    // 调用方法

    addComponent();

    submit.addActionListener(this);

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

    titleInformation = new JLabel("代码生成器");

    add(g, c, titleInformation, 0, 0, 1, 1);
    
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

    basepackage = new JLabel("包名：");
    add(g, c, basepackage, 0, 6, 1, 1);

    basepackageTextField = new JTextField(50);
    basepackageTextField.setText(basepackageProp);
    add(g, c, basepackageTextField, 1, 6, 2, 1);

    outRoot = new JLabel("输出目录：");
    add(g, c, outRoot, 0, 7, 1, 1);

    outRootTextField = new JTextField(50);
    outRootTextField.setText(outRootProp);
    add(g, c, outRootTextField, 1, 7, 2, 1);

    table = new JLabel("表名：");
    add(g, c, table, 0, 8, 1, 1);

    tableTextField = new JTextField(50);
    tableTextField.setText(tableProp);
    add(g, c, tableTextField, 1, 8, 2, 1);

    submit = new JButton("生成");

    c.insets = new Insets(8, 10, 4, 0);

    add(g, c, submit, 1, 9, 1, 1);

    result = new JTextArea(15, 50);

    add(g, c, result, 0, 10, 3, 4);

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

  @Override
  public void actionPerformed(ActionEvent arg0) {
    String templateDirStr = templateDirTextFiled.getText();
    String schemaStr = schemaTextField.getText();
    String basepackageStr = basepackageTextField.getText();
    String outRootStr = outRootTextField.getText();
    String tableStr = tableTextField.getText();
    
    String jdbcUrl = jdbcUrlTextFiled.getText();
    String jdbcUsername = jdbcUsernameTextFiled.getText();
    String jdbcPasswordText = jdbcPasswordTextFiled.getText();

    System.out.println("templateDirStr:" + templateDirStr);

    String retMsg = "文件生成成功,文件目录:\n" + outRootStr;

    try {

      GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
      GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
      GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
      
      GeneratorProperties.setProperty("basepackage", basepackageStr);
      GeneratorProperties.setProperty("basepackage_dir",
          GeneratorProperties.getProperty("basepackage").replace(".", "/"));
      GeneratorProperties.setProperty("outRoot", outRootStr);

      System.out.println("basepackage_dir:" + GeneratorProperties.getProperty("basepackage_dir"));

      GeneratorFacade g = new GeneratorFacade();
      g.clean();
      g.getGenerator().setTemplateRootDir(templateDirStr);

      g.generateByTable(tableStr);

      Runtime.getRuntime().exec("cmd.exe /c start " + outRootStr);
    } catch (Exception e) {
      retMsg = "文件生成失败,文件目录:\n" + outRootStr + ",异常信息:" + e.getMessage();

      GLogger.error("文件生成失败",e);
    }
    System.out.println("");
    System.out.println("***************************************************************");
    System.out.println("*********************Generate Success**************************");
    System.out.println("***************************************************************");

    result.setText(retMsg);

  }

  /**
   * 执行方法
   * @param args 运行参数
   */
  public static void main(String [] args)  {
    new GeneratorMainFrame("代码生成器");
  }
}
