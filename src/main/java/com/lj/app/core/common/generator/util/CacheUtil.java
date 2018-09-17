package com.lj.app.core.common.generator.util;

import java.util.HashMap;

/**
 * 缓存工具类
 *
 */
public class CacheUtil {

  public static HashMap<String, Object> cache = new HashMap<String, Object>();

  /**
   * 存储
   * 
   * @param key
   *          缓存key
   * @param obj
   *          缓存值
   */
  public static void store(String key, Object obj) {
    cache.put(key, obj);
  }

  /**
   * 查找缓存
   * 
   * @param key
   *          缓存key
   * @return Object 缓存值
   */
  public static Object find(String key) {
    return cache.get(key);
  }

  /**
   * 删除缓存
   * 
   * @param key
   *          缓存key
   */
  public static void removeCache(String key) {
    cache.remove(key);
  }

  public static boolean hasStore(String key) {
    return cache.get(key) != null;
  }

  /**
   * 获得缓存大小
   * 
   * @return int 获得缓存大小
   */
  public static int getCacheSize() {
    return cache.size();
  }

  /**
   * 清除所有缓存
   */
  public static void clealAll() {
    cache.clear();
  }

}
