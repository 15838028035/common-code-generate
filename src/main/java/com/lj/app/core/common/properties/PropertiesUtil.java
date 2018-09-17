package com.lj.app.core.common.properties;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 属性工具类
 *
 */
public class PropertiesUtil {

  private static Log logger = LogFactory.getLog(PropertiesUtil.class);

  public static String getValue(String properName, String key) {
    return PropertiesReader.getValue(properName, key);
  }

  public static Properties getProperty() {
    return PropertiesReader.getProperties();
  }

  public static String getProperty(String name, String defaultValue) {
    return PropertiesReader.getValueAndDefault(name, defaultValue);
  }
  
  public static String getProperty(String key) {
    return PropertiesReader.getProperty(key);
  }

  /**
   * 获得去除首尾空格属性的值
   * 
   * @param name
   *          属性名称
   * @return String 获得去除首尾空格属性的值
   */
  public static String getPropertyTrim(String name) {
    String value = getProperty(name);
    if (value == null) {
      logger.warn("can't get key value from config file!! key[" + name + "] is not config!!!");
      return "";
    } else {
      return value.trim();
    }
  }

  public static String getPropertyTrim(String name, String defaultValue) {
    String value = getProperty(name, defaultValue);
    return (value == null) ? defaultValue : value.trim();
  }

  public static String getPropertyUpper(String name) {
    return getPropertyTrim(name).toUpperCase();
  }

  public static String getPropertyUpper(String name, String defaultValue) {
    return getPropertyTrim(name, defaultValue).toUpperCase();
  }

  public static String getPropertyLower(String name) {
    return getPropertyTrim(name).toLowerCase();
  }

  public static String getPropertyLower(String name, String defaultValue) {
    return getPropertyTrim(name, defaultValue).toLowerCase();
  }

  public static boolean getBooleanTrueOrFalse(String name, boolean defaultValue) {
    String value = getPropertyTrim(name, String.valueOf(defaultValue));
    return (value.equals("true"));
  }

  public static boolean getBooleanTrueOrFalse(String name) {
    return (getPropertyTrim(name, "false").equals("true"));
  }

  /**
   * 获得国际化消息
   * 
   * @param filename
   *          文件名称
   * @param key
   *          属性key
   * @return String 获得国际化消息
   */
  public static String getMessage(String filename, String key) {
    Locale locale = Locale.getDefault();
    if (filename == null || filename.length() <= 0) {
      return "";
    }
    if (key == null || key.length() <= 0) {
      return "";
    }
    logger.debug(locale.getLanguage());
    logger.debug(locale.getCountry());

    try {
      ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
      String value = bundle.getString(key);
      return value;
    } catch (Exception e) {
      logger.error(e.getMessage());
      return key;
    }
  }

  /**
   * 获得国际化消息
   * 
   * @param filename
   *          文件名称
   * @param key
   *          属性key
   * @param args
   *          列表参数
   * @return 获得国际化消息
   */
  public static String getMessage(String filename, String key, List<String> args) {
    String value = getMessage(filename, key);
    if (args != null && args.size() > 0) {
      value = MessageFormat.format(value, args.toArray());
    }

    return value;
  }

  public static Object setProperty(String key, int value) {
    return PropertiesReader.setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, long value) {
    return PropertiesReader.setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, float value) {
    return PropertiesReader.setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, double value) {
    return PropertiesReader.setProperty(key, String.valueOf(value));
  }

  public static Object setProperty(String key, boolean value) {
    return PropertiesReader.setProperty(key, String.valueOf(value));
  }
  
  public static Object setProperty(String key, String value) {
    return PropertiesReader.setProperty(key, value);
  }

  public static boolean contains(Object value) {
    return PropertiesReader.contains(value);
  }

  public static boolean containsKey(Object key) {
    return PropertiesReader.containsKey(key);
  }

  public static boolean containsValue(Object value) {
    return PropertiesReader.containsValue(value);
  }

  public static Integer getInteger(String key) {
    return PropertiesReader.getInteger(key);
  }

  public static int getInt(String key, int defaultValue) {
    return PropertiesReader.getInt(key, defaultValue);
  }

  public static int getRequiredInt(String key) throws IllegalStateException {
    return PropertiesReader.getRequiredInt(key);
  }

  public static Long getLong(String key) {
    return PropertiesReader.getLong(key);
  }

  public static long getLong(String key, long defaultValue) {
    return PropertiesReader.getLong(key, defaultValue);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static long getRequiredLong(String key) throws IllegalStateException {
    return PropertiesReader.getRequiredLong(key);
  }

  public static Boolean getBoolean(String key) {
    return PropertiesReader.getBoolean(key);
  }

  public static boolean getBoolean(String key, boolean defaultValue) {
    return PropertiesReader.getBoolean(key, defaultValue);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static boolean getRequiredBoolean(String key) throws IllegalStateException {
    return Boolean.parseBoolean(getRequiredProperty(key));
  }

  public static Float getFloat(String key) {
    return PropertiesReader.getFloat(key);
  }

  public static float getFloat(String key, float defaultValue) {
    return PropertiesReader.getFloat(key, defaultValue);
  }

  /**
   * 必须存在这个key的值,不然抛 IllegalStateException异常
   **/
  public static float getRequiredFloat(String key) throws IllegalStateException {
    return PropertiesReader.getRequiredFloat(key);
  }

  public static Double getDouble(String key) {
    return PropertiesReader.getDouble(key);
  }

  public static double getDouble(String key, double defaultValue) {
    return PropertiesReader.getDouble(key, defaultValue);
  }

  public static double getRequiredDouble(String key) throws IllegalStateException {
    return PropertiesReader.getRequiredDouble(key);
  }
  
  public static void clear() {
    PropertiesReader.clear();
  }

  public static String getRequiredProperty(String key) throws IllegalStateException {
    return PropertiesReader.getRequiredProperty(key);
  }

  /**
   * 获得字符串数组
   * 
   * @param key
   *          属性key
   * @return 获得字符串数组
   */
  public static String[] getPropertyArray(String key) {
    return PropertiesReader.getPropertyArray(key);
  }

  /**
   * 获得属性列表list
   * 
   * @param key
   *          属性key
   * @return 获得属性列表list
   */
  public static List<String> getPropertyList(String key) {
    return PropertiesReader.getPropertyList(key);
  }
}
