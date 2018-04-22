package graph.readers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.extendj.ast.Access;
import org.extendj.ast.ArrayCreationExpr;
import org.extendj.ast.AssignSimpleExpr;
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
import org.extendj.ast.VariableDeclarator;

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
	public void readInto(Map<String, Node> nodes, Set<Edge> edges) {
		String name = this.MethodNode.getFullName()+".body";
		BodyNode = new Node(idGenerator.generate(), name, Node.Type.MethodBody,
				method);

		nodes.put(name, BodyNode);

		if(block.getNumStmt()>0){
			addMethodBodyTypeDependency(edges,nodes);
		}

		addMSignatureDependency(edges);

	}

	private void addMSignatureDependency(Set<Edge> edges) {
		edges.add(new Edge(MethodNode.getFullName(), BodyNode.getFullName(), Edge.Type.Contains));
	}


	public void addMethodBodyTypeDependency(Set<Edge> edges,Map<String, Node> nodes) {
		TypeDecl stmtType;
		MethodAccess ma = null;

		for(Stmt s: block.getStmtList()){
			/*************** Var  Decl*****************/
			if(s.value instanceof VarDeclStmt) {
				VarDeclStmt varStmt = (VarDeclStmt) s;

				/*Dep methodBody - Var type*/
				stmtType = varStmt.type();
				addTypeDependency(edges, stmtType, Edge.Type.Uses,nodes);

				/*dep method body var value*/
				if(varStmt.hasDeclarator()) {
					for(VariableDeclarator d : varStmt.getDeclaratorList() ) {
						if(d.hasInit()) {
							Expr init = d.getInit();
							DepExpr(init, edges,nodes);
						}

					}
				}				

			}
			/****EXPR *****/
			if (s.value instanceof ExprStmt) {
				Expr e = ((ExprStmt)s).getExpr();
				DepExpr(e, edges,nodes);
			}
		}
	}


	public void DepExpr(Expr e,Set<Edge> edges,Map<String, Node> nodes ) {
		TypeDecl stmtType;
		MethodAccess ma = null;

		if(e.isMethodAccess() ) {	

			if(e instanceof  Dot) {
				if ( ! ((Dot)e).getLeft().isThisAccess()) {
				ma = (MethodAccess) ((Dot)e).getRight(); 
				//				if ( ((Dot)e).getLeft() instanceof VarAccess ) {
				//					VarAccess va = (VarAccess ) ((Dot)e).getLeft();
				//					stmtType = va.type();
				//					addTypeDependency(edges, stmtType, Edge.Type.Uses,nodes);
				//				}
				BodyotherMethodDep(ma,edges,nodes) ;
				
			}
			}
			
			
		}
		if( e instanceof AssignSimpleExpr) {
			AssignSimpleExpr ase = (AssignSimpleExpr) e;
			DepExpr(ase.getSource(),edges,nodes);

		}

		if (e instanceof Dot) {
			Dot d = (Dot)e;
			if(d.isFieldAccess() && ! d.getLeft().isThisAccess()) {
				String fullName = d.getLeft().type() + "."+d.getRight() ;
				edges.add(new Edge(BodyNode.getFullName(), fullName, Edge.Type.Uses));


			}


		}




	}

	public void BodyotherMethodDep(MethodAccess ma,Set<Edge> edges,Map<String, Node> nodes) {
		TypeDecl stmtType;

		MethodDecl m = (MethodDecl)ma.decl();

		String fullName = ma.methodHost() + "." + m.fullSignature();
		edges.add(new Edge(BodyNode.getFullName(), fullName, Edge.Type.Uses));
		/*	addTypeDependency(edges, fullName, Edge.Type.Uses,nodes);

				for ( Expr er :ma.getArgList() ) {
					if ( er instanceof ClassInstanceExpr) {
						ClassInstanceExpr ce = (ClassInstanceExpr) er;
						stmtType = ce.getAccess().type();
						addTypeDependency(edges, stmtType, Edge.Type.Uses,nodes);
					}
			}*/

	}

	@Override
	String getFullName() {
		return MethodNode.getFullName() + ".body";
	}
}