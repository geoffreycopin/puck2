package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.Block;
import org.extendj.ast.ForStmt;
import org.extendj.ast.LocalClassDeclStmt;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ReturnStmt;
import org.extendj.ast.Stmt;
import org.extendj.ast.ThrowStmt;
import org.extendj.ast.TryStmt;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.VarDeclStmt;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class MethodBodyReader extends AbstractReader {


	private Block block ;
	private Node MethodNode;
	private MethodDecl method;
	private Node BodyNode;



	public MethodBodyReader(UniqueIdGenerator generator, Block block, Node MethodNode,MethodDecl method) {
		super(generator);
		// TODO Auto-generated constructor stub
		this.block=block;
		this.MethodNode=MethodNode;
		this.method=method;
	}

	@Override
	public void readInto(Map<String, Node> nodes, List<Edge> edges) {
		// TODO Auto-generated method stub
		String name = this.MethodNode.getFullName()+".body";
		BodyNode = new Node(idGenerator.generate(), name, Node.Type.MethodBody,
				null);

		nodes.put(name, BodyNode);

		if(block.getNumStmt()>0){
			addMethodBodyTypeDependancy(edges);
		}

		addMSignatureDependency(edges);

	}

	private void addMSignatureDependency(List<Edge> edges) {
		edges.add(new Edge(MethodNode.getFullName(), BodyNode.getFullName(), Edge.Type.Contains));
	}


	public void addMethodBodyTypeDependancy(List<Edge> edges) {
		TypeDecl stmtType;
		for(Stmt s: block.getStmtList()){
			if(s.value instanceof VarDeclStmt) {
				VarDeclStmt varStmt = (VarDeclStmt) s;
				stmtType = varStmt.type();
				if (Util.isPrimitive(stmtType)) {
					continue;
				}

				Edge e =new Edge(BodyNode.getFullName(), stmtType.fullName(), Edge.Type.Uses);		
				if(e.Contains(edges)) continue;
				edges.add(e);

			}
			
		
			
			/* Return Stmt  
			if(s.value instanceof ReturnStmt) {
				ReturnStmt returnStmt= (ReturnStmt )s.value;
				stmtType = returnStmt.getResult().type();
				if (Util.isPrimitive(stmtType)) {
					continue;
				}
				Edge e =new Edge(BodyNode.getFullName(), stmtType.fullName(), Edge.Type.Uses);		
				if(e.Contains(edges)) continue;
				edges.add(e);

			}*/
			

		}

	}

}