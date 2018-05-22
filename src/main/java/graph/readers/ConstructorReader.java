package graph.readers;

import org.extendj.ast.Access;
import org.extendj.ast.Block;
import org.extendj.ast.ConstructorDecl;
import org.extendj.ast.ParameterDeclaration;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class ConstructorReader extends BodyDeclReader {
	private ConstructorDecl constructorDecl;
	private Node constructorNode;

	public ConstructorReader(ConstructorDecl constructorDecl, Graph graph) {
		super(constructorDecl, graph);
		this.constructorDecl = constructorDecl;
	}

	@Override
	public Graph read() {
		String fullName = getHostClassName() + "." + constructorDecl.signature();

		constructorNode = addNode(fullName, Node.Type.Method, constructorDecl);
		addHostClassDependency();
		if(constructorDecl.hasParameter()) addParametersTypeDependency();
		if (constructorDecl.getBlock() != null) {
			Block b = constructorDecl.getBlock();
			MethodBodyReader mbreader = new MethodBodyReader(b, constructorNode, constructorDecl, graph);
			mbreader.read();
		}

		if( constructorDecl.hasExceptions()) {
			addExceptionsTypesDependencies();
		}

		return getGraph();
	}
	private void addHostClassDependency() {
		addEdge(getHostTypeName(), constructorNode.getFullName(), Edge.Type.Contains);
	}
	@Override
	String getFullName() {
		// TODO Auto-generated method stub
		return constructorNode != null ? constructorNode.getFullName() : "";
	}
	private String getHostClassName() {
		return constructorDecl.hostType().fullName();
	}
	private void addParametersTypeDependency() {
		for (ParameterDeclaration p: constructorDecl.getParameterList()) {
			ParametersReader r = new ParametersReader(p, constructorNode, getGraph());
			r.read();
		}
	}
	private void addExceptionsTypesDependencies() {
		for (Access a: constructorDecl.getExceptionList()) {
			addTypeDependency(a.type(), Edge.Type.Uses);
		}
	}

}
