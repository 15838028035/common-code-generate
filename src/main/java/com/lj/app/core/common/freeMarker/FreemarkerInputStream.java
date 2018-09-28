package com.lj.app.core.common.freeMarker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.lj.app.core.common.generator.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * freemarker读取器
 *
 */
public class FreemarkerInputStream extends InputStream {
  private InputStream delegateInput;
  private Configuration conf;
  private ByteArrayInputStream freemarkerProcessedInput;
  /**
   * 编码
   */
  private String encoding;
  /**
   * freemarker模板可以引用的变量
   */
  private Map<String, Object> variables = new HashMap<String, Object>();

  public FreemarkerInputStream(InputStream input) {
    this(input, Util.newDefaultConfirutaion(), null, null);
  }

  public FreemarkerInputStream(InputStream input, Map<String, Object> variables) {
    this(input, Util.newDefaultConfirutaion(), null, variables);
  }

  public FreemarkerInputStream(InputStream input, Configuration conf) {
    this(input, conf, null, null);
  }

  public FreemarkerInputStream(InputStream input, Configuration conf, Map<String, Object> variables) {
    this(input, conf, null, variables);
  }

  public FreemarkerInputStream(InputStream input, String encoding) {
    this(input, Util.newDefaultConfirutaion(), encoding, null);
  }

  public FreemarkerInputStream(InputStream input, String encoding, Map<String, Object> variables) {
    this(input, Util.newDefaultConfirutaion(), encoding, variables);
  }

  /**
   * freemarker读取器
   * @param input 输入流
   * @param conf 配置
   * @param encoding 编码
   * @param variables 变量
   */
  public FreemarkerInputStream(InputStream input, Configuration conf, String encoding, Map<String, Object> variables) {
    this.delegateInput = input;
    this.conf = conf;
    this.encoding = encoding;
    this.variables = variables;
    this.freemarkerProcessedInput = processByFreemarker(input);
  }

  private ByteArrayInputStream processByFreemarker(InputStream input) {
    try {
      Map<String, Object> rootMap = newTemplateVariables();
      InputStreamReader reader = StringUtil.isBlank(encoding) ? new InputStreamReader(input)
          : new InputStreamReader(input, encoding);
      Template template = new Template("" + input, reader, conf);
      StringWriter cachedTemplateOutput = new StringWriter();
      template.process(rootMap, cachedTemplateOutput);
      return new ByteArrayInputStream(StringUtil.isBlank(encoding) ? cachedTemplateOutput.toString().getBytes()
          : cachedTemplateOutput.toString().getBytes(encoding));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  protected Map newTemplateVariables() {
    Map rootMap = new HashMap();
    rootMap.putAll(Util.relaceAllKeyChar(System.getProperties(), '.', '_'));
    rootMap.put("env", System.getenv());
    if (variables != null) {
      rootMap.putAll(variables);
    }
    return rootMap;
  }

  @Override
  public int hashCode() {
    return freemarkerProcessedInput.hashCode();
  }

  @Override
  public int read(byte[] b) throws IOException {
    return freemarkerProcessedInput.read(b);
  }
  
  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return freemarkerProcessedInput.read(b, off, len);
  }
  
  @Override
  public  int read() throws IOException {
    return freemarkerProcessedInput.read();
  }

  @Override
  public boolean equals(Object obj) {
    return freemarkerProcessedInput.equals(obj);
  }

  @Override
  public long skip(long n) throws IOException {
    return freemarkerProcessedInput.skip(n);
  }

  @Override
  public int available() throws IOException {
    return freemarkerProcessedInput.available();
  }

  @Override
  public String toString() {
    return freemarkerProcessedInput.toString();
  }

  @Override
  public void close() throws IOException {
    freemarkerProcessedInput.close();
    delegateInput.close();
  }

  @Override
  public void mark(int readlimit) {
    freemarkerProcessedInput.mark(readlimit);
  }

  @Override
  public void reset() throws IOException {
    freemarkerProcessedInput.reset();
  }

  @Override
  public boolean markSupported() {
    return freemarkerProcessedInput.markSupported();
  }
}