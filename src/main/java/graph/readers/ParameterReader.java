package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.ClassDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class ParameterReader extends AbstractReader{

	public ParameterReader(UniqueIdGenerator generator,ParameterDeclaration param,MethodDecl m,Node methodNode) {
		super(generator);
		// TODO Auto-generated constructor stub
		this.param=param;
		this.method=m;
		this.methodNode=methodNode;
	}
	private ParameterDeclaration param;
	private MethodDecl method;
	private Node methodNode;
	private Node paramNode;
	@Override
	public void readInto(Map<String, Node> nodes, List<Edge> edges) {
		// TODO Auto-generated method stub
		String name = method.signature()+"."+param.name();
		paramNode = new Node(idGenerator.generate(),name, Node.Type.Attribute, param.getTypeAccess());
		nodes.put(name, paramNode );
		addMethodParamDependency( nodes, edges,paramNode,methodNode);
		if( nodes.containsKey(param.type().fullName()))addClassParamDependency( nodes, edges,paramNode,param.type().fullName());
	}

	
	 public void addMethodParamDependency(Map<String, Node> nodes, List<Edge> edges,Node paramf,Node method){
		 Edge e;
		 e = new Edge(nodes.get(methodNode.getFullName()).getId(), paramf.getId(), Edge.Type.Contains);
		 edges.add(e);

	 }
	public void addClassParamDependency(Map<String, Node> nodes, List<Edge> edges,Node field,String name){
		Edge e;
		e = new Edge(field.getId(),nodes.get(name).getId(), Edge.Type.IsA);
		edges.add(e);

	}


}
