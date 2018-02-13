package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.Block;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Stmt;
import org.extendj.ast.TypeDecl;

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
			for(Stmt s: block.getStmtList()){
			//	System.out.println(s.toString());
			}
		}





	}


}
