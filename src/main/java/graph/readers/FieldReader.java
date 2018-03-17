package graph.readers;

import java.util.List;
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

	public void readInto(Map<String, Node> nodes, Set<Edge> edges) {
		for (FieldDeclarator v : fieldDecl.getDeclaratorList()) {
		    String fullName = getHostTypeName() + "." + v.name();
			fieldNode = new Node(idGenerator.generate(), fullName, Node.Type.Attribute, v.getTypeAccess());
			nodes.put(fullName, fieldNode);
		}

		addHostClassDependency(edges);
		addFieldTypeDependency(edges,nodes);
	}

	private void addHostClassDependency(Set<Edge> edges) {
	    edges.add(new Edge(getHostTypeName(), fieldNode.getFullName(), Edge.Type.Contains));
    }

    private void addFieldTypeDependency(Set<Edge> edges,Map<String, Node> nodes) {
	    addTypeDependency(edges, fieldDecl.getTypeAccess().type(), Edge.Type.Uses,nodes);
    }
}
