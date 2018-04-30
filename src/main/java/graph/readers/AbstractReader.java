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

	public abstract void readInto(Map<Integer, Node> nodes, Set<Edge> edges);

	abstract String getFullName();

	protected void addTypeDependency(Set<Edge> edges, TypeDecl type, Edge.Type edgeType, Map<Integer, Node> nodes) {
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
			Edge e = new Edge(idGenerator.idFor(getFullName()), idGenerator.idFor(type.fullName()), edgeType);
			edges.add(e);
			typeName = type.fullName();
		}

		if (!typeName.isEmpty() && ! nodes.keySet().contains(type.packageName())) {
	        addContainingPackage(typeName, type.packageName(), nodes, edges);
        }
	}

	protected void addGenericTypeDependency(Set<Edge> edges, TypeDecl type, Edge.Type edgeType, Map<Integer, Node> nodes) {
	    String genericTypeName = getGenericTypeName(type);
	    edges.add(new Edge(idGenerator.idFor(getFullName()), idGenerator.idFor(genericTypeName), edgeType));

		for (TypeDecl typeParameter : TypeDeclReader.getTypeParameters(type)) {
			addTypeDependency(edges, typeParameter, Edge.Type.Uses,nodes);
		}
	}

	protected static String getGenericTypeName(TypeDecl t) {
	    return t.packageName() + "." + t.topLevelType().name();
    }

    public void addContainingPackage(String typeName, String packageName, Map<Integer, Node> nodes, Set<Edge> edges) {
	    if (packageName.isEmpty()) {
	        return;
        }

	    Node newNode = new Node(idGenerator.idFor(packageName), packageName, Node.Type.Package, null);
	    nodes.put(idGenerator.idFor(packageName), newNode);

	    Edge newEdge = new Edge(idGenerator.idFor(packageName), idGenerator.idFor(typeName), Edge.Type.Contains);
	    edges.add(newEdge);
    }

    protected Edge createEdge(String source, String target, Edge.Type type) {
		return new Edge(idGenerator.idFor(source), idGenerator.idFor(target), type);
	}
}