package graph.readers;

import java.util.Map;
import java.util.Set;

import org.extendj.ast.*;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class FieldReader extends BodyDeclReader {
    private FieldDecl fieldDecl;
    private Node fieldNode;

	public FieldReader(UniqueIdGenerator generator, FieldDecl fieldDecl) {
		super(fieldDecl, generator);
		this.fieldDecl=fieldDecl;
	}

	@Override
    protected String getFullName() {
	    return fieldNode.getFullName();
    }

	public void readInto(Map<Integer, Node> nodes, Set<Edge> edges) {
		for (FieldDeclarator v : fieldDecl.getDeclaratorList()) {
		    String fullName = getHostTypeName() + "." + v.name();
			fieldNode = new Node(idGenerator.idFor(fullName), fullName, Node.Type.Attribute, v);
			nodes.put(idGenerator.idFor(fullName), fieldNode);
		}

		addHostClassDependency(edges);
		addFieldTypeDependency(edges, nodes);
	}

	private void addHostClassDependency(Set<Edge> edges) {
		Edge e = createEdge(getHostTypeName(), fieldNode.getFullName(), Edge.Type.Contains);
	    edges.add(e);
    }

    private void addFieldTypeDependency(Set<Edge> edges, Map<Integer, Node> nodes) {
	    addTypeDependency(edges, fieldDecl.getTypeAccess().type(), Edge.Type.Uses,nodes);
    }
}
