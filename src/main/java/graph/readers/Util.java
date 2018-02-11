package graph.readers;

import org.extendj.ast.TypeDecl;

public class Util {
    public static boolean isPrimitiveType(TypeDecl t) {
        return t.packageName().equals("@primitive");
    }
}
