package graph.readers;

import java.util.Map;
import java.util.Set;

import graph.Graph;
import org.extendj.ast.*;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class FieldReader extends BodyDeclReader {
    private FieldDecl fieldDecl;
    private Node fieldNode;

	public FieldReader(FieldDecl fieldDecl, Graph graph) {
		super(fieldDecl, graph);
		this.fieldDecl = fieldDecl;
	}

	@Override
    protected String getFullName() {
	    return fieldNode.getFullName();
    }

    @Override
	public Graph read() {
		for (FieldDeclarator v : fieldDecl.getDeclaratorList()) {
		    String fullName = getHostTypeName() + "." + v.name();
		    fieldNode = addNode(fullName, Node.Type.Attribute, v);
		}

		addHostClassDependency();
		addFieldTypeDependency();

		return getGraph();
	}

	private void addHostClassDependency() {
	    addEdge(getHostTypeName(), fieldNode.getFullName(), Edge.Type.Contains);
    }

    private void addFieldTypeDependency() {
	    addTypeDependency(fieldDecl.getTypeAccess().type(), Edge.Type.Uses);
    }
}
