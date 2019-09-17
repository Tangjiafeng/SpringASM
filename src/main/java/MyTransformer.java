import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import transform.TransClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM4;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * 结合读操作和写操作来进行类的动态转化
 * 涉及适配器模式
 */
public class MyTransformer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 首先读取一个类信息，预备下文的操作，进而转化为另一个类
        ClassReader cr = new ClassReader(TransClass.class.getClassLoader()
                .getResourceAsStream("transform/TransClass.class"));

        ClassWriter cw = new ClassWriter(0);

        // 类层级的visitor，与ClassWriter建立联系
        ClassVisitor cv = new ClassVisitor(ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String sign, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, sign, exceptions);
                // 方法层级的visitor
                return new MethodVisitor(ASM4, mv) {
                    @Override
                    public void visitCode() {
                        // 关注修改源码的操作，对所有的方法第一行加入如下语句
                        visitMethodInsn(INVOKESTATIC, "transform/Util", "before", "()V", false);
                        super.visitCode();// 若只有只一句，则不会发生改变
                    }
                };
            }
        };

        // 访问者模式的应用，执行读写操作
        cr.accept(cv, 0);
        byte[] b = cw.toByteArray();
        MyClassLoader myClassLoader = new MyClassLoader();
        // 将涉及的类加载到内存中，如果注释掉，则执行到自动加载（推断）
        myClassLoader.loadClass("transform.Util");
        Class c = myClassLoader.defineClass("transform.TransClass", b);
        c.getConstructor().newInstance();// 实例化，会执行无参构造方法，
    }
}
