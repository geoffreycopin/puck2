package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ArrayDecl;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.WildcardExtendsType;

import java.util.List;
import java.util.Map;

public abstract class AbstractReader {
    protected UniqueIdGenerator idGenerator;

    public AbstractReader(UniqueIdGenerator generator) {
        this.idGenerator = generator;
    }

    public abstract void readInto(Map<String, Node> nodes, List<Edge> edges);

    abstract String getFullName();

    protected void addTypeDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType) {
        if (type.isParameterizedType()) {
            addGenericTypeDependency(edges, type, edgeType);
        } else if (type.isWildcard()) {
            addTypeDependency(edges, ((WildcardExtendsType) type).extendsType(), Edge.Type.Uses);
        } else if (type.isArrayDecl()) {
            addTypeDependency(edges, type.elementType(), edgeType);
        } else if (!(Util.isPrimitive(type) || Util.isBuiltin(type)) && ! type.isTypeVariable()) {
            edges.add(new Edge(getFullName(), type.fullName(), edgeType));
        }
    }

    protected void addGenericTypeDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType) {
        if (!Util.isBuiltin(type)) {
            String genericTypeName = TypeDeclReader.getGenericTypeName(type);
            edges.add(new Edge(getFullName(), genericTypeName, edgeType));
        }

        for (TypeDecl typeParameter : TypeDeclReader.getTypeParameters(type)) {
            addTypeDependency(edges, typeParameter, Edge.Type.Uses);
        }
    }

}