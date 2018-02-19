package graph.readers;

import graph.Edge;
import graph.UniqueIdGenerator;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.TypeDecl;

import java.util.List;

public abstract class BodyDeclReader extends AbstractReader {
    private BodyDecl bodyDecl;

    public BodyDeclReader(BodyDecl decl, UniqueIdGenerator generator) {
        super(generator);
        this.bodyDecl = decl;
    }

    abstract String getFullName();

    protected String getHostTypeName() {
        return bodyDecl.hostType().fullName();
    }

    protected void addTypeDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType) {
        if (type.isParameterizedType()) {
            addGenericTypeDependency(edges, type, edgeType);
        } else if (! (Util.isPrimitive(type) || Util.isBuiltin(type))) {
            edges.add(new Edge(getFullName(), type.fullName(), edgeType));
        }
    }

    protected void addGenericTypeDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType) {
        if (! Util.isBuiltin(type)) {
            String genericTypeName = TypeDeclReader.getGenericTypeName(type);
            edges.add(new Edge(getFullName(), genericTypeName, Edge.Type.Uses));
        }

        for (String typeParameterName: TypeDeclReader.getTypeParametersName(type)) {
            edges.add(new Edge(getFullName(), typeParameterName, edgeType));
        }
    }
}
