public class JustTest {
    public static void main(String[] args) {
        // view 里面 show Bytecode 创建对象的几大过程
        /**
         *     LINENUMBER 3 L0
         *     NEW java/lang/Object               分配内存
         *     DUP
         *     INVOKESPECIAL java/lang/Object.<init> ()V       建立栈中和堆中的引用连接
         *     ASTORE 1                           调构造函数赋初值
         */
        Object o = new Object();
    }
}

