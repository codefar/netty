package org.greenleaf.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Parents<E> {

     private Class<?> child;

     public Parents() {
          Class<?> c = this.getClass(); //子类创建 会创建父类 子类调用时 此处的this是子类
          Type t = c.getGenericSuperclass(); //获得带有泛型的父类
          System.out.println(t.getTypeName());
          if (t instanceof ParameterizedType) {
               Type[] p = ((ParameterizedType) t).getActualTypeArguments();  //取得所有泛型
               this.child = (Class<E>) p[0];
               System.out.println(child.getSimpleName());
               System.out.println(child.getCanonicalName());
               System.out.println(child.toGenericString());
          }
     }
}
