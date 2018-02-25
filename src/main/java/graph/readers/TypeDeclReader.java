package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ParClassDecl;
import org.extendj.ast.ParInterfaceDecl;
import org.extendj.ast.Parameterization;
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

    public static List<TypeDecl> getTypeParameters(TypeDecl type) {
        List<TypeDecl> result = new ArrayList<>();

        for (TypeDecl subtype: getParameterization(type).args) {
            if (subtype.isParameterizedType()) {
                result.addAll(getTypeParameters(subtype));
            }
            result.add(subtype.hostType());
        }

        return result;
    }

    private static Parameterization getParameterization(TypeDecl type) {
        if (type instanceof ParInterfaceDecl) {
            return ((ParInterfaceDecl) type).getParameterization();
        } else {
            return ((ParClassDecl) type).getParameterization();
        }
    }
}
