package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.Block;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Stmt;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.VarDeclStmt;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class MethodBodyReader extends BodyDeclReader {


	private Block block ;
	private Node MethodNode;
	private MethodDecl method;
	private Node BodyNode;



	public MethodBodyReader(UniqueIdGenerator generator, Block block, Node MethodNode,MethodDecl method) {
		super(method, generator);
		this.block=block;
		this.MethodNode=MethodNode;
		this.method=method;
	}

	@Override
	public void readInto(Map<String, Node> nodes, List<Edge> edges) {
		String name = this.MethodNode.getFullName()+".body";
		BodyNode = new Node(idGenerator.generate(), name, Node.Type.MethodBody,
				null);

		nodes.put(name, BodyNode);

		if(block.getNumStmt()>0){
			addMethodBodyTypeDependency(edges);
		}

		addMSignatureDependency(edges);

	}

	private void addMSignatureDependency(List<Edge> edges) {
		edges.add(new Edge(MethodNode.getFullName(), BodyNode.getFullName(), Edge.Type.Contains));
	}


	public void addMethodBodyTypeDependency(List<Edge> edges) {
		TypeDecl stmtType;
		for(Stmt s: block.getStmtList()){
			if(s.value instanceof VarDeclStmt) {
				VarDeclStmt varStmt = (VarDeclStmt) s;
				stmtType = varStmt.type();
                addTypeDependency(edges, stmtType, Edge.Type.Uses);
			}
		}
	}

    @Override
    String getFullName() {
        return MethodNode.getFullName() + ".body";
    }
}