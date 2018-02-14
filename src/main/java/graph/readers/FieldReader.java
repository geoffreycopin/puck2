package graph.readers;

import java.util.List;
import java.util.Map;

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

	public void readInto(Map<String, Node> nodes, List<Edge> edges) {
		for (FieldDeclarator v : fieldDecl.getDeclaratorList()) {
		    String fullName = getHostTypeName() + "." + v.name();
			fieldNode = new Node(idGenerator.generate(), fullName, Node.Type.Attribute, v.getTypeAccess());
			nodes.put(fullName, fieldNode);
		}

		addHostClassDependency(edges);
		addFieldTypeDependency(edges);
	}

	private void addHostClassDependency(List<Edge> edges) {
	    edges.add(new Edge(getHostTypeName(), fieldNode.getFullName(), Edge.Type.Contains));
    }

    private void addFieldTypeDependency(List<Edge> edges) {
	    if (Util.isPrimitive(fieldDecl.getTypeAccess().type())) {
            return;
        }

	    String fieldTypeName = fieldDecl.getTypeAccess().type().fullName();
	    edges.add(new Edge(fieldNode.getFullName(), fieldTypeName, Edge.Type.IsA));
    }
}
