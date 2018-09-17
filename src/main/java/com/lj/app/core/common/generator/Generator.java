package com.lj.app.core.common.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lj.app.core.common.generator.util.FileHelper;
import com.lj.app.core.common.generator.util.FreemarkerHelper;
import com.lj.app.core.common.generator.util.GLogger;
import com.lj.app.core.common.generator.util.IOHelper;
import com.lj.app.core.common.generator.util.StringHelper;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 生成器类
 *
 */
public class Generator {
  private static final String GENERATOR_INSERT_LOCATION = "generator-insert-location";
  private List templateRootDirs = new ArrayList();
  private String outRootDir;
  private boolean ignoreTemplateGenerateException = true;
  private String removeExtensions = GeneratorProperties.getProperty("generator_removeExtensions");

  String encoding = "UTF-8";

  private String includes = GeneratorProperties.getProperty("generator_includes"); // 需要处理的模板，使用逗号分隔符,示例值:
                                                                                   // java_src/**,java_test/**
  private String excludes = GeneratorProperties.getProperty("generator_excludes"); // 不需要处理的模板，使用逗号分隔符,示例值:
                                                                                   // java_src/**,java_test/**
  private String sourceEncoding = GeneratorProperties.getProperty("generator_sourceEncoding");
  private String outputEncoding = GeneratorProperties.getProperty("generator_outputEncoding");

  public void setTemplateRootDir(File templateRootDir) {
    setTemplateRootDirs(new File[] { templateRootDir });
  }

  public void setTemplateRootDirs(File... templateRootDirs) {
    this.templateRootDirs = new ArrayList<File>(Arrays.asList(templateRootDirs));
  }

  public void addTemplateRootDir(File f) {
    this.templateRootDirs.add(f);
  }

  /**
   * 设置模板目录，支持用逗号分隔多个模板目录
   * @param templateRootDir   设置模板目录，支持用逗号分隔多个模板目录
   */
  public void setTemplateRootDir(String templateRootDir) {
    setTemplateRootDirs(StringHelper.tokenizeToStringArray(templateRootDir, ","));
  }

  /**
   * 设置模板目录
   * @param templateRootDirs   设置模板目录
   */
  public void setTemplateRootDirs(String... templateRootDirs) {
    ArrayList<File> tempDirs = new ArrayList<File>();
    for (String dir : templateRootDirs) {
      tempDirs.add(FileHelper.getFile(dir));
    }
    this.templateRootDirs = tempDirs;
  }

  public boolean isIgnoreTemplateGenerateException() {
    return this.ignoreTemplateGenerateException;
  }

  public void setIgnoreTemplateGenerateException(boolean ignoreTemplateGenerateException) {
    this.ignoreTemplateGenerateException = ignoreTemplateGenerateException;
  }

  public String getEncoding() {
    return this.encoding;
  }

  /**
   * 设置编码
   * @param  v 编码
   */
  public void setEncoding(String v) {
    if (v == null)  {
      throw new IllegalArgumentException("encoding must be not null");
    }
    this.encoding = v;
  }

  public List getTemplateRootDirs() {
    return templateRootDirs;
  }

  public void setTemplateRootDirs(List templateRootDirs) {
    this.templateRootDirs = templateRootDirs;
  }

  public String getIncludes() {
    return includes;
  }

  public void setIncludes(String includes) {
    this.includes = includes;
  }

  public String getExcludes() {
    return excludes;
  }

  public void setExcludes(String excludes) {
    this.excludes = excludes;
  }

  public String getSourceEncoding() {
    return sourceEncoding;
  }

  /**
   * 设置编码
   * @param sourceEncoding 设置编码
   */
  public void setSourceEncoding(String sourceEncoding) {
    if (StringHelper.isBlank(sourceEncoding)) {
      throw new IllegalArgumentException("sourceEncoding must be not empty");
    }
    this.sourceEncoding = sourceEncoding;
  }

  public String getOutputEncoding() {
    return outputEncoding;
  }

  /**
   * 设置编码
   * @param outputEncoding 设置编码
   */
  public void setOutputEncoding(String outputEncoding) {
    if (StringHelper.isBlank(outputEncoding)) {
      throw new IllegalArgumentException("outputEncoding must be not empty");
    }
    this.outputEncoding = outputEncoding;
  }

  /**
   * 设置根目录
   * @param rootDir 根目录
   */
  public void setOutRootDir(String rootDir) {
    if (rootDir == null)  {
      throw new IllegalArgumentException("outRootDir must be not null");
    }
    this.outRootDir = rootDir;
  }

  /**
   * 删除目录
   * @throws IOException 异常
   */
  public void deleteOutRootDir() throws IOException {
    if (StringHelper.isBlank(getOutRootDir()))  {
      throw new IllegalStateException("'outRootDir' property must be not null.");
    }
    GLogger.info("[delete dir] " + getOutRootDir());
    FileHelper.deleteDirectory(new File(getOutRootDir()));
  }

  public List generateBy(Map templateModel, Map filePathModel) throws Exception {
    if (this.templateRootDirs.size() == 0)  {
      throw new IllegalStateException("'templateRootDirs' cannot empty");
    }

    List allExceptions = new ArrayList();
    for (int i = 0; i < this.templateRootDirs.size(); i++) {
      File templateRootDir = (File) this.templateRootDirs.get(i);
      List exceptions = generateBy(templateRootDir, templateModel, filePathModel);
      allExceptions.addAll(exceptions);
    }
    return allExceptions;
  }

  private List<Exception> generateBy(File templateRootDir, Map templateModel, Map filePathModel) throws Exception {
    if (templateRootDir == null)  {
      throw new IllegalStateException("'templateRootDir' must be not null");
    }
    System.out
        .println("-------------------load template from templateRootDir = '" + templateRootDir.getAbsolutePath() + "'");

    List templateFiles = new ArrayList();
    FileHelper.listFiles(templateRootDir, templateFiles);

    List exceptions = new ArrayList();
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

          targetFilename = getTargetFilename(filePathModel, outputFilePath);
          Configuration conf = newFreeMarkerConfiguration(templateRootDirs, encoding, templateFile.getName());

          generateNewFileOrInsertIntoFile(templateModel, targetFilename, conf, templateRelativePath, outputFilePath);

        } catch (Exception e) {
          if (this.ignoreTemplateGenerateException) {
            GLogger.warn("iggnore generate error,template is:" + templateRelativePath + " cause:" + e);
            exceptions.add(e);
          } else {
            throw new RuntimeException(
                "generate oucur error,templateFile is:" + templateRelativePath + " => " + targetFilename, e);
          }
        }
      }
    }
    return exceptions;
  }

  private Configuration newFreeMarkerConfiguration() throws IOException {
    Configuration config = new Configuration();

    FileTemplateLoader[] templateLoaders = new FileTemplateLoader[this.templateRootDirs.size()];
    for (int i = 0; i < this.templateRootDirs.size(); i++) {
      templateLoaders[i] = new FileTemplateLoader((File) this.templateRootDirs.get(i));
    }
    MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(templateLoaders);

    config.setTemplateLoader(multiTemplateLoader);
    config.setNumberFormat("###############");
    config.setBooleanFormat("true,false");
    config.setDefaultEncoding(this.encoding);
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

  private void generateNewFileOrInsertIntoFile(Map templateModel, String targetFilename, Configuration config,
      String templateFile, String outputFilePath) throws Exception {
    Template template = config.getTemplate(templateFile);
    template.setOutputEncoding(this.encoding);

    File absoluteOutputFilePath = getAbsoluteOutputFilePath(targetFilename);
    if (absoluteOutputFilePath.exists()) {
      StringWriter newFileContentCollector = new StringWriter();
      if (isFoundInsertLocation(template, templateModel, absoluteOutputFilePath, newFileContentCollector)) {
        System.out.println("[insert]\t generate content into:" + targetFilename);
        IOHelper.saveFile(absoluteOutputFilePath, newFileContentCollector.toString());
        return;
      }
    }

    System.out.println("[generate]\t template:" + templateFile + " to " + targetFilename);
    saveNewOutputFileContent(template, templateModel, absoluteOutputFilePath);
  }

  private String getTargetFilename(Map filePathModel, String templateFilepath) throws Exception {
    StringWriter out = new StringWriter();
    Template template = new Template("filePath", new StringReader(templateFilepath), newFreeMarkerConfiguration());
    try {
      template.process(filePathModel, out);
      return out.toString();
    } catch (Exception e) {
      throw new IllegalStateException(
          "cannot generate filePath from templateFilepath:" + templateFilepath + " cause:" + e);
    }
  }

  private File getAbsoluteOutputFilePath(String targetFilename) {
    String outRoot = getOutRootDir();
    File outputFile = new File(outRoot, targetFilename);
    outputFile.getParentFile().mkdirs();
    return outputFile;
  }

  private boolean isFoundInsertLocation(Template template, Map model, File outputFile, StringWriter newFileContent)
      throws IOException, TemplateException {
    LineNumberReader reader = new LineNumberReader(new FileReader(outputFile));
    String line = null;
    boolean isFoundInsertLocation = false;

    PrintWriter writer = new PrintWriter(newFileContent);
    while ((line = reader.readLine()) != null) {
      writer.println(line);

      if ((!isFoundInsertLocation) && (line.indexOf("generator-insert-location") >= 0)) {
        template.process(model, writer);
        writer.println();
        isFoundInsertLocation = true;
      }
    }

    writer.close();
    reader.close();
    return isFoundInsertLocation;
  }

  private void saveNewOutputFileContent(Template template, Map model, File outputFile)
      throws IOException, TemplateException {
    Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), this.encoding));
    template.process(model, out);
    out.close();
  }

  /**
   * 清除
   * @throws IOException 异常
   */
  public void clean() throws IOException {
    String outRoot = getOutRootDir();
    System.out.println("[Delete Dir]\t" + outRoot);
    FileHelper.deleteDirectory(new File(outRoot));
  }

  private String getOutRootDir() {
    if (this.outRootDir == null)  {
      throw new IllegalStateException("'outRootDir' property must be not null.");
    }
    return this.outRootDir;
  }

  public void setRemoveExtensions(String removeExtensions) {
    this.removeExtensions = removeExtensions;
  }

  public String getRemoveExtensions() {
    return removeExtensions;
  }
}