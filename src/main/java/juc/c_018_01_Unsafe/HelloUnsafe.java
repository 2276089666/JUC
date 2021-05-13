package juc.c_018_01_Unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class HelloUnsafe {
    static class M {
        private M() {}

        int i =0;
    }



   public static void main(String[] args) throws InstantiationException, NoSuchFieldException, IllegalAccessException {
        // 得到Unsafe里面的私有属性 private static final Unsafe theUnsafe;
       Field field = Unsafe.class.getDeclaredField("theUnsafe");

       // 私有的属性变为可以访问
       field.setAccessible(true);

       // 通过静态Unsafe字段获取它的对象
       Unsafe unsafe = (Unsafe)field.get(null);

       M m = (M) unsafe.allocateInstance(M.class);
       m.i=100;
       System.out.println(m.i);
   }
}


