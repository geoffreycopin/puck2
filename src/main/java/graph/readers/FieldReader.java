package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.MethodDecl;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class FieldReader extends AbstractReader {


	public FieldReader(UniqueIdGenerator generator,FieldDecl fieldDecl,ClassDecl c) {
		super(generator);
		// TODO Auto-generated constructor stub
		this.fieldDecl=fieldDecl;
		this.c=c;
	}
	private FieldDecl fieldDecl;
	private Node fieldNode;
	private ClassDecl c;
	@Override
	
	
	public void readInto(Map<String, Node> nodes, List<Edge> edges) {
		// TODO Auto-generated method stub
		for (FieldDeclarator v : fieldDecl.getDeclaratorList()) {
			fieldNode = new Node(idGenerator.generate(), v.name(), Node.Type.Attribute, v.getTypeAccess());
			nodes.put(v.name(), fieldNode);
			addFieldClassDependency( nodes, edges,fieldNode,this.c);
			if( nodes.containsKey(v.type().fullName()))addClassFieldDependency( nodes, edges,fieldNode,v.type().fullName());
		}
		
	}
	 public void addFieldClassDependency(Map<String, Node> nodes, List<Edge> edges,Node field,ClassDecl c){
		 Edge e;
		 e = new Edge(nodes.get(c.fullName()).getId(), field.getId(), Edge.Type.Contains);
		 edges.add(e);

	 }
	 public void addClassFieldDependency(Map<String, Node> nodes, List<Edge> edges,Node field,String name){
		 Edge e;
		 e = new Edge(field.getId(),nodes.get(name).getId(), Edge.Type.IsA);
		 edges.add(e);

	 }
}
