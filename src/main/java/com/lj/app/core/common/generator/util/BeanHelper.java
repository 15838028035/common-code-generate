package com.lj.app.core.common.generator.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * java bean工具类
 *
 */
public class BeanHelper {
  public static Map<Object,Object> describe(Object obj) {
    if ((obj instanceof Map)) {
      return (Map) obj;
    }
    Map<Object,Object> map = new HashMap<>();
    PropertyDescriptor[] descriptors = getPropertyDescriptors(obj.getClass());
    for (int i = 0; i < descriptors.length; i++) {
      String name = descriptors[i].getName();
      Method readMethod = descriptors[i].getReadMethod();
      if (readMethod == null) {
        continue;
      }
      try {
        map.put(name, readMethod.invoke(obj, new Object[0]));
      } catch (Exception e) {
        GLogger.warn("error get property value,name:" + name + " on bean:" + obj, e);
      }
    }

    return map;
  }

  public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass) {
    BeanInfo beanInfo = null;
    try {
      beanInfo = Introspector.getBeanInfo(beanClass);
    } catch (IntrospectionException e) {
      return new PropertyDescriptor[0];
    }
    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
    if (descriptors == null) {
      descriptors = new PropertyDescriptor[0];
    }
    return descriptors;
  }
}