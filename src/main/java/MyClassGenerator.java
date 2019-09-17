import jdk.internal.org.objectweb.asm.ClassWriter;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * 产生一个类：
 * package pkg;
 * public interface Comparable {
 *     int LESS = -1;
 *     int EQUAL = 0;
 *     int GREATER = 1;
 *     int compareTo(Object o);
 * }
 */
public class MyClassGenerator {
    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        // 版本、修饰符、类全名、null、父类对象、null
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                null);
        // 即接口成员变量默认的public static final修饰符、变量名、返回类型I：int、null、赋值为-1
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        // 定义方法名，形参和返回类型
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();
        MyClassLoader myClassLoader = new MyClassLoader();
        Class c = myClassLoader.defineClass("pkg.Comparable", b);
        System.out.println(c.getFields()[0]);
        System.out.println(c.getMethods()[0]);
    }
}
