import jdk.internal.org.objectweb.asm.*;
import java.io.IOException;
import static jdk.internal.org.objectweb.asm.Opcodes.ASM4;

/**
 * 解析class文件
 * 采用访问者设计模式
 */
public class MyVisitor extends ClassVisitor {
    public MyVisitor() {
        super(ASM4);
    }

    public MyVisitor(int i) {
        super(i);
    }

    public MyVisitor(int i, ClassVisitor classVisitor) {
        super(i, classVisitor);
    }

    /**
     * 可以自定义方法体，对查看的信息进行组织
     * @param version
     * @param access
     * @param name
     * @param signature
     * @param superName
     * @param interfaces
     */
    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        System.out.println(version + " " + access + " " + name + " " + signature + " " + superName);
    }

    @Override
    public void visitSource(String s, String s1) {
        super.visitSource(s, s1);
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {
        super.visitOuterClass(s, s1, s2);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        return super.visitAnnotation(s, b);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String s, boolean b) {
        return super.visitTypeAnnotation(i, typePath, s, b);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {
        super.visitInnerClass(s, s1, s2, i);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc,
                                   String signature, Object value) {
        System.out.println(desc + " " + name);
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        System.out.println(desc + " " + name);
        return null;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }




    public static void main(String[] args) throws IOException {
        MyVisitor myVisitor = new MyVisitor();
        ClassReader classReader = new ClassReader("Main");
//        ClassReader classReader = new ClassReader(MyVisitor.class.getClassLoader()
//                .getResourceAsStream("Main.class"));
        classReader.accept(myVisitor, 1);

    }
}
