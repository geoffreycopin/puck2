package graph.readers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.extendj.ast.Access;
import org.extendj.ast.ArrayCreationExpr;
import org.extendj.ast.Block;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.ClassInstanceExpr;
import org.extendj.ast.ConstructorDecl;
import org.extendj.ast.Dot;
import org.extendj.ast.Expr;
import org.extendj.ast.ExprMethodReference;
import org.extendj.ast.ExprStmt;
import org.extendj.ast.LabeledStmt;
import org.extendj.ast.MethodAccess;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Stmt;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.VarAccess;
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
			addMethodBodyTypeDependency(edges,nodes);
		}

		addMSignatureDependency(edges);

	}

	private void addMSignatureDependency(List<Edge> edges) {
		edges.add(new Edge(MethodNode.getFullName(), BodyNode.getFullName(), Edge.Type.Contains));
	}


	public void addMethodBodyTypeDependency(List<Edge> edges,Map<String, Node> nodes) {
		TypeDecl stmtType;
		MethodAccess ma = null;

		for(Stmt s: block.getStmtList()){
			if(s.value instanceof VarDeclStmt) {
				VarDeclStmt varStmt = (VarDeclStmt) s;
				stmtType = varStmt.type();
				addTypeDependency(edges, stmtType, Edge.Type.Uses,nodes);
			}else if (s.value instanceof ExprStmt) {
				Expr e = ((ExprStmt)s).getExpr();
				if(e.isMethodAccess() ) {	
					if(e instanceof  Dot) {
					ma = (MethodAccess) ((Dot)e).getRight(); 
					BodyMDependency(ma,edges,nodes) ;

					if (((Dot)e).getLeft() != null && ((Dot)e).getLeft() instanceof VarAccess ) {
						VarAccess va = (VarAccess ) ((Dot)e).getLeft();
						stmtType = va.type();
						addTypeDependency(edges, stmtType, Edge.Type.Uses,nodes);
					}
				}
				else{
					ma = (MethodAccess)e;
					BodyMDependency(ma,edges,nodes) ;
				}
				}
			}
		}
	}




	public void BodyMDependency(MethodAccess ma,List<Edge> edges,Map<String, Node> nodes) {
		TypeDecl stmtType;
		for ( Expr er :ma.getArgList() ) {
			if ( er instanceof ClassInstanceExpr) {
				ClassInstanceExpr ce = (ClassInstanceExpr) er;
				stmtType = ce.getAccess().type();
				addTypeDependency(edges, stmtType, Edge.Type.Uses,nodes);
			}
		}

	}

	@Override
	String getFullName() {
		return MethodNode.getFullName() + ".body";
	}
}