package com.lj.app.core.common.freemarker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.lj.app.core.common.exception.FreemarkerTemplateException;
import com.lj.app.core.common.generator.GeneratorConstants;
import com.lj.app.core.common.generator.GeneratorProperties;
import com.lj.app.core.common.generator.util.ClassHelper;
import com.lj.app.core.common.generator.util.GLogger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 模板工具类
 *
 */
public class FreeMarkerTemplateUtils {

  private Configuration configuration = null;

  public FreeMarkerTemplateUtils() {
    configuration = new Configuration();
    configuration.setDefaultEncoding("UTF-8");
  }

  public Configuration setDirectoryForTemplateLoading(File file) throws Exception {
    configuration.setDirectoryForTemplateLoading(file);
    return configuration;
  }

  /**
   * 获得模板编码
   * 
   * @return String
   */
  public static String getFremarkerTemplateEncoding() {
    return GeneratorProperties.getProperty(GeneratorConstants.GENERATOR_SOURCE_ENCODING);
  }

  public String processTemplate(String templateName, Object model) throws FreemarkerTemplateException, Exception {
    Template template = getTemplate(configuration, templateName);
    return processTemplateIntoString(template, model);
  }

  public void processTemplate(String templateName, Object model, Writer out) throws FreemarkerTemplateException {
    Template template = getTemplate(configuration, templateName);
    processTemplate(template, model, out);
  }

  public String processTemplate(String templateName, Object model, String encoding)
      throws FreemarkerTemplateException, Exception {
    Template template = getTemplate(configuration, templateName, encoding);
    return processTemplateIntoString(template, model);
  }

  public String processTemplate(String templateName, Object model, Locale locale, String encoding)
      throws FreemarkerTemplateException, Exception {
    Template template = getTemplate(configuration, templateName, locale, encoding);
    return processTemplateIntoString(template, model);
  }
  
  /**
   *  解析模板内容
   * @param template  解析模板内容
   * @param model 对象
   * @param out write对象
   * @throws FreemarkerTemplateException 模板异常
   */
  public static void processTemplate(Template template, Object model, Writer out) throws FreemarkerTemplateException {
    try {
      template.process(model, out);
    } catch (IOException e) {
      throw new FreemarkerTemplateException("process template occer IOException,templateName:" + template.getName(), e);
    } catch (TemplateException e) {
      throw new FreemarkerTemplateException(
          "process template occer TemplateException,templateName:" + template.getName(), e);
    }
  }

  /**
   *  解析模板内容
   * @param template 模板
   * @param model 对象
   * @return 模板内容
   * @throws FreemarkerTemplateException 模板异常
   * @throws Exception 异常
   */
  public static String processTemplateIntoString(Template template, Object model)
      throws FreemarkerTemplateException, Exception {
    StringWriter result = new StringWriter();
    template.process(model, result);
    return result.toString();
  }

  /**
   * 获得模板
   * @param conf 配置
   * @param templateName 模板名称 
   * @return 获得模板
   * @throws FreemarkerTemplateException 模板异常
   */
  public static Template getTemplate(Configuration conf, String templateName) throws FreemarkerTemplateException {
    try {
      return conf.getTemplate(templateName);
    } catch (IOException e) {
      throw new FreemarkerTemplateException("load template error,templateName:" + templateName, e);
    }
  }

  /**
   * 获得模板
   * @param conf 配置
   * @param templateName 模板名称 
   * @return 获得模板
   */
  public static Template getTemplate(Configuration conf, String templateName, String encoding)
      throws FreemarkerTemplateException {
    try {
      return conf.getTemplate(templateName, encoding);
    } catch (IOException e) {
      throw new FreemarkerTemplateException("load template error,templateName:" + templateName, e);
    }
  }

  /**
   * 获得模板
   * @param conf 配置
   * @param templateName 模板名称 
   * @param locale 区域
   * @param encoding 编码
   * @return 获得模板
   * @throws FreemarkerTemplateException 模板异常
   */
  public static Template getTemplate(Configuration conf, String templateName, Locale locale, String encoding)
      throws FreemarkerTemplateException {
    try {
      return conf.getTemplate(templateName, locale, encoding);
    } catch (IOException e) {
      throw new FreemarkerTemplateException("load template error,templateName:" + templateName, e);
    }
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * 获得公共的配置变量
   * 
   * @return map
   */
  public static Map getShareVars() {
    Map templateModel = new HashMap();
    templateModel.putAll(System.getProperties());
    templateModel.put("env", System.getenv());
    templateModel.put("now", new Date());
    templateModel.putAll(getToolsMap());
    return templateModel;
  }

  /** 得到模板可以引用的工具类 */
  public static Map getToolsMap() {
    Map toolsMap = new HashMap();
    String[] tools = GeneratorProperties.getStringArray(GeneratorConstants.GENERATOR_TOOLS_CLASS);
    for (String className : tools) {
      try {
        Object instance = ClassHelper.newInstance(className);
        toolsMap.put(Class.forName(className).getSimpleName(), instance);
        GLogger.error("put tools class:" + className + " with key:" + Class.forName(className).getSimpleName());
      } catch (Exception e) {
        GLogger.error("cannot load tools by className:" + className + " cause:" + e);
      }
    }
    return toolsMap;
  }

  /**
   * 读取模板文件内容
   * 
   * @return 模板文件内容
   */
  public static String readTemplateFileContent(String templatePath)
      throws FileNotFoundException, IOException, Exception {
    String templateFileContent = null;
    File templateFile = new File(templatePath);
    templateFileContent = org.apache.commons.io.FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
    return templateFileContent;
  }

  /**
   * 写入模板文件内容
   * 
   */
  public static String writeTemplateFileContent(String templatePath, String templateFileContent)
      throws FileNotFoundException, IOException, Exception {
    File templateFile = new File(templatePath);
    org.apache.commons.io.FileUtils.writeStringToFile(templateFile, templateFileContent, StandardCharsets.UTF_8);
    return templateFileContent;
  }

}
