package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.Block;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;
import org.extendj.ast.TypeDecl;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class MethodReader extends AbstractReader{
	 private MethodDecl methodDecl;
	 private Node methodNode;
	 private TypeDecl c;
	
	 
	 public MethodReader(UniqueIdGenerator idGenerator, MethodDecl methodDecl,TypeDecl c) {
		super(idGenerator);
		this.methodDecl = methodDecl;
		this.c=c;
		
	}


	 @Override
	 public void readInto(Map<String, Node> nodes, List<Edge> edges) {
		 // TODO Auto-generated method stud
		 ParameterReader preader;
		 MethodBodyReader mbreader;
		 String name = c.fullName()+"."+methodDecl.fullSignature();
		 methodNode = new Node(idGenerator.generate(), name, Node.Type.Method,
				 methodDecl.getTypeAccess());
		 nodes.put(name, methodNode);
		 if(methodDecl.getNumParameter() >0){
			 for (ParameterDeclaration p : methodDecl.getParameterList()) {
				 preader= new ParameterReader(idGenerator,p,methodDecl,methodNode);
				 preader.readInto(nodes, edges);

			 }
		 }
		 if(methodDecl.hasBlock()){
			 Block b = methodDecl.getBlock();
			 mbreader = new MethodBodyReader(idGenerator,b,methodNode,methodDecl);
			 mbreader.readInto(nodes, edges);
		 }
		addMethodClassDependency(nodes,edges,methodNode);
	}
	 
	 public void addMethodClassDependency(Map<String, Node> nodes, List<Edge> edges,Node method){
		 Edge e;
		 e = new Edge(nodes.get(c.fullName()).getId(), method.getId(), Edge.Type.Contains);
		 edges.add(e);

	 }
}
