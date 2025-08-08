package com.prolinktic.sgdea.util;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class Properties {


  private static Object getPropertyValue(Object object, String propertyName)
      throws NoSuchFieldException, IllegalAccessException {
    Class<?> clazz = object.getClass();
    Field field = clazz.getDeclaredField(propertyName);
    field.setAccessible(true);
    return field.get(object);
  }
}
