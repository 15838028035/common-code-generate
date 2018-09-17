package com.lj.app.core.common.properties;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.lj.app.core.common.generator.util.ClassHelper;
import com.lj.app.core.common.generator.util.PropertyPlaceholderUtil;
import com.lj.app.core.common.generator.util.PropertyPlaceholderUtil.PropertyPlaceholderConfigurerResolver;
import com.lj.app.core.common.generator.util.StringUtil;

/**
 * 
 * 属性读取工具类
 */
public class PropertiesReader {
  private static Log logger = LogFactory.getLog(PropertiesReader.class);
  private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
  private static final String ENV_COMMON = "env-common.properties";
  private static final String ENV_DEV = "env-dev.properties";
  private static final String ENV_TEST = "env-test.properties";
  private static final String ENV_PRO = "env-pro.properties";
  private static final String CORE_URL = "";
  private static PropertyPlaceholderUtil propertyPlaceholderUtil = new PropertyPlaceholderUtil("${", "}", ":", false);

  private static Properties properties = new Properties();

  static {
    loadConfigFile();
  }

  private static void loadConfigFile() {
    try {

      logger.warn("Now,Loading default file~~~");
      loadConfigFileByUrlAndFileName(CORE_URL, ENV_COMMON);

      String springProfilesActive = System.getProperty("spring.profiles.active");
      if (StringUtil.isEqual(springProfilesActive, "dev")) {
        loadConfigFileByUrlAndFileName(CORE_URL, ENV_DEV);
      }
      if (StringUtil.isEqual(springProfilesActive, "test")) {
        loadConfigFileByUrlAndFileName(CORE_URL, ENV_TEST);
      }
      if (StringUtil.isEqual(springProfilesActive, "pro")) {
        loadConfigFileByUrlAndFileName(CORE_URL, ENV_PRO);
      }
      logger.warn("Loading config file finished~~");
    } catch (Exception e) {
      logger.error("loadConfigFileError===" + e.getMessage());
    }
  }

  private static void loadConfigFileByUrlAndFileName(String properPath, String configName) throws Exception {
    String properFile = properPath + configName;
    logger.warn("Now,Loading prov file=== " + properFile);
    Properties provProperties = PropertiesLoaderUtils.loadAllProperties(properFile,
        PropertiesReader.class.getClassLoader());
    // provProperties = resolveProperties(provProperties);
    properties.putAll(provProperties);
  }

  public static String getValue(String properName, String key, String defalutValue) {
    String value = getValue(properName, key);
    return (value == null || value.equals("")) ? defalutValue : value;
  }

  /**
   * 获得属性的值
   * 
   * @param properName
   *          属性文名称
   * @param key
   *          属性
   * @return 获得属性的值
   */
  public static String getValue(String properName, String key) {
    try {
      Properties properties = propertiesMap.get(properName);
      if (properties != null) {
        logger.debug(
            "ReadConfig[properName=" + properName + ",key=" + key + ",value=" + properties.getProperty(key) + "]");
        return properties.getProperty(key);
      }
      properties = PropertiesLoaderUtils.loadAllProperties(properName, PropertiesReader.class.getClassLoader());
      propertiesMap.put(properName, properties);
      logger
          .debug("ReadConfig[properName=" + properName + ",key=" + key + ",value=" + properties.getProperty(key) + "]");
      return properties.getProperty(key);
    } catch (IOException e) {
      logger.error(e);
    }
    return null;
  }

  public static Properties getProperties() {
    return properties;
  }

  /**
   * 获得属性的默认值
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return String 获得属性的默认值
   */
  public static String getValueAndDefault(String key, String defaultValue) {
    String value = properties.getProperty(key, defaultValue);
    logger.debug("ReadConfig[key=" + key + ",value=" + value + "]");
    return value;
  }

  public static Object setProperty(String key, int value) {
    return setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, long value) {
    return setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, float value) {
    return setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, double value) {
    return setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, boolean value) {
    return setProperty(key, String.valueOf(value));
  }

  /**
   * 设置属性
   * 
   * @param key
   *          属性key
   * @param value
   *          属性值
   * @return Object 属性
   */
  public static Object setProperty(String key, String value) {
    return properties.setProperty(key, value);
  }

  public static boolean contains(Object value) {
    return properties.contains(value);
  }

  public static boolean containsKey(Object key) {
    return properties.containsKey(key);
  }

  public static boolean containsValue(Object value) {
    return properties.containsValue(value);
  }

  /**
   * 获得Integer类型
   * 
   * @param key
   *          属性key
   * @return Integer 获得Integer类型
   */
  public static Integer getInteger(String key) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return null;
    }
    return Integer.parseInt(value);
  }

  /**
   * 获得int类型
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return 获得int类型
   */
  public static int getInt(String key, int defaultValue) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    return Integer.parseInt(value);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static int getRequiredInt(String key) throws IllegalStateException {
    return Integer.parseInt(getRequiredProperty(key));
  }

  /**
   * 获得Long类型
   * 
   * @param key
   *          属性key
   * @return Long 获得Long类型
   */
  public static Long getLong(String key) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return null;
    }
    return Long.parseLong(value);
  }

  /**
   * 获得long类型
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return 获得long类型
   */
  public static long getLong(String key, long defaultValue) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    return Long.parseLong(value);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static long getRequiredLong(String key) throws IllegalStateException {
    return Long.parseLong(getRequiredProperty(key));
  }

  /**
   * 获得Boolean类型
   * 
   * @param key
   *          属性key
   * @return Boolean 获得Boolean类型
   */
  public static Boolean getBoolean(String key) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return null;
    }
    return Boolean.parseBoolean(value);
  }

  /**
   * 获得boolean类型
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return 获得boolean类型
   */
  public static boolean getBoolean(String key, boolean defaultValue) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    return Boolean.parseBoolean(value);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static boolean getRequiredBoolean(String key) throws IllegalStateException {
    return Boolean.parseBoolean(getRequiredProperty(key));
  }

  /**
   * 获得Float类型
   * 
   * @param key
   *          属性key
   * @return Float 获得Float类型
   */
  public static Float getFloat(String key) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return null;
    }
    return Float.parseFloat(value);
  }

  /**
   * 获得float类型
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return 获得float类型
   */
  public static float getFloat(String key, float defaultValue) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    return Float.parseFloat(value);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static float getRequiredFloat(String key) throws IllegalStateException {
    return Float.parseFloat(getRequiredProperty(key));
  }

  /**
   * 获得Double类型
   * 
   * @param key
   *          属性key
   * @return Double 获得Float类型
   */
  public static Double getDouble(String key) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return null;
    }
    return Double.parseDouble(value);
  }

  /**
   * 获得double类型
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return 获得double类型
   */
  public static double getDouble(String key, double defaultValue) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    return Double.parseDouble(value);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static double getRequiredDouble(String key) throws IllegalStateException {
    return Double.parseDouble(getRequiredProperty(key));
  }
  /**
   * 获得属性的值
   * 
   * @param key
   *          属性key
   * @param defaultValue
   *          默认值
   * @return String 属性值
   */
  public static String getProperty(String key, String defaultValue) {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    return value;
  }

  /**
   * 获得属性的值
   * 
   * @param key
   *          属性key
   * @return String 属性值
   */
  public static String getProperty(String key) {
    String value = properties.getProperty(key);
    logger.debug("ReadConfig[key=" + key + ",value=" + value + "]");
    return value;
  }

  public static void clear() {
    properties.clear();
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static String getRequiredProperty(String key) throws IllegalStateException {
    String value = getProperty(key);
    if (StringUtil.isBlank(value)) {
      throw new IllegalStateException("required property is blank by key=" + key);
    }
    return value;
  }

  /**
   * 获得字符串数组
   * 
   * @param key
   *          属性key
   * @return String[] 属性数组
   */
  public static String[] getPropertyArray(String key) {
    String value = StringUtil.trimBlank(getProperty(key));
    return value.split(",");
  }

  /**
   * 获得属性列表list
   * 
   * @param key
   *          属性key
   * @return List 字符串列表
   */
  public static List<String> getPropertyList(String key) {
    String value = StringUtil.trimBlank(getProperty(key));
    return StringUtil.splitStringToStringList(value);
  }

  public Object getClassInstance(String key, Object defaultinstance) throws IllegalArgumentException {
    return (containsKey(key) ? ClassHelper.newInstance(key) : defaultinstance);
  }

  /**
   * 解析属性
   * 
   * @param props
   *          属性
   * @return Properties 属性
   */
  public static Properties resolveProperties(Properties props) {
    Properties result = new Properties();
    for (Object s : props.keySet()) {
      String sourceKey = s.toString();
      String key = resolveProperty(sourceKey, props);
      String value = resolveProperty(props.getProperty(sourceKey), props);
      result.setProperty(key, value);
    }
    return result;
  }

  /**
   * 解析属性
   * 
   * @param 属性key
   * @param props
   *          属性
   * @return 属性值
   */
  public static String resolveProperty(String v, Properties props) {
    PropertyPlaceholderConfigurerResolver propertyPlaceholderConfigurerResolver = 
        new PropertyPlaceholderConfigurerResolver(props);
    return propertyPlaceholderUtil.replacePlaceholders(v, propertyPlaceholderConfigurerResolver);
  }

}
