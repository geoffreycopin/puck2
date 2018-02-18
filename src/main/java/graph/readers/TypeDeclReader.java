package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ParInterfaceDecl;
import org.extendj.ast.TypeDecl;

import java.util.ArrayList;
import java.util.List;

public abstract class TypeDeclReader extends AbstractReader{
    private TypeDecl typeDecl;

    public TypeDeclReader(TypeDecl typeDecl, UniqueIdGenerator generator) {
        super(generator);
        this.typeDecl = typeDecl;
    }

    protected void addPackageDependency(List<Edge> edges) {
        String packageName = typeDecl.packageName();

        if (packageName.isEmpty()) {
            return;
        }

        edges.add(new Edge(packageName, typeDecl.fullName(), Edge.Type.Contains));
    }

    public static String getGenericTypeName(TypeDecl type) {
        return type.fullName().split("<")[0];
    }

    public static List<String> getTypeParametersName(TypeDecl type) {
        List<String> result = new ArrayList<>();

        for (TypeDecl subtype: ((ParInterfaceDecl) type).getParameterization().args) {
            if (subtype.isParameterizedType()) {
                result.add(getGenericTypeName(subtype));
                result.addAll(getTypeParametersName(subtype));
            } else {
                result.add(subtype.fullName());
            }
        }

        return result;
    }
}
