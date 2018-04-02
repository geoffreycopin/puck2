package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.WildcardExtendsType;

import java.util.Map;
import java.util.Set;

public abstract class AbstractReader {
	protected UniqueIdGenerator idGenerator;

	public AbstractReader(UniqueIdGenerator generator) {
		this.idGenerator = generator;
	}

	public abstract void readInto(Map<String, Node> nodes, Set<Edge> edges);

	abstract String getFullName();

	protected void addTypeDependency(Set<Edge> edges, TypeDecl type, Edge.Type edgeType,Map<String, Node> nodes) {
	    String typeName = "";
	    if (type.isParameterizedType()) {
			addGenericTypeDependency(edges, type, edgeType,nodes);
			typeName = getGenericTypeName(type);
		} else if (type.isWildcard()) {
			addTypeDependency(edges, ((WildcardExtendsType) type).extendsType(), Edge.Type.Uses,nodes);
		} else if (type.isArrayDecl()) {
			addTypeDependency(edges, type.elementType(), edgeType,nodes);
			typeName = type.elementType().fullName();
		} else if (! Util.isPrimitive(type) && ! type.isTypeVariable()) {
			Edge e = new Edge(getFullName(), type.fullName(), edgeType);
			edges.add(e);
			typeName = type.fullName();
		}

		if (!typeName.isEmpty() && ! nodes.keySet().contains(type.packageName())) {
	        addContainingPackage(typeName, type.packageName(), nodes, edges);
        }
	}

	protected void addGenericTypeDependency(Set<Edge> edges, TypeDecl type, Edge.Type edgeType,Map<String, Node> nodes) {
	    String genericTypeName = getGenericTypeName(type);
	    edges.add(new Edge(getFullName(), genericTypeName, edgeType));

		for (TypeDecl typeParameter : TypeDeclReader.getTypeParameters(type)) {
			addTypeDependency(edges, typeParameter, Edge.Type.Uses,nodes);
		}
	}

	protected static String getGenericTypeName(TypeDecl t) {
	    return t.packageName() + "." + t.topLevelType().name();
    }

    public void addContainingPackage(String typeName, String packageName, Map<String, Node> nodes, Set<Edge> edges) {
	    if (packageName.isEmpty()) {
	        return;
        }

	    Node newNode = new Node(idGenerator.generate(), packageName, Node.Type.Package, null);
	    nodes.put(packageName, newNode);

	    Edge newEdge = new Edge(packageName, typeName, Edge.Type.Contains);
	    edges.add(newEdge);
    }
}