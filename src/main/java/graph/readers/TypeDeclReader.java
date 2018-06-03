package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ParClassDecl;
import org.extendj.ast.ParInterfaceDecl;
import org.extendj.ast.Parameterization;
import org.extendj.ast.TypeDecl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class TypeDeclReader extends AbstractReader{
    private TypeDecl typeDecl;

    public TypeDeclReader(TypeDecl typeDecl, Graph graph) {
        super(graph);
        this.typeDecl = typeDecl;
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
