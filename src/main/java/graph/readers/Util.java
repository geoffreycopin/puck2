package graph.readers;

import org.extendj.ast.TypeDecl;

public class Util {
    public static boolean isPrimitive(TypeDecl t) {
        return t.packageName().equals("@primitive");
    }

    public static boolean isBuiltin(TypeDecl t) {
        String firstComponent = t.packageName().split("\\.")[0];
        return firstComponent.equals("java");
    }
}
