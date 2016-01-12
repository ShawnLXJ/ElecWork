package cn.lxj.swork.util;

import java.lang.reflect.ParameterizedType;

public class ClassCover {
    @SuppressWarnings("rawtypes")
    public static Class getClass(Class tclass) {
    //泛型转换
    ParameterizedType pt = (ParameterizedType)tclass.getGenericSuperclass();
    Class t = (Class)pt.getActualTypeArguments()[0];
    return t;
    
    }
}
