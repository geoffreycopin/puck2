package graph.readers;

import graph.Graph;
import org.extendj.ast.*;

import graph.Edge;
import graph.Node;

public class MethodBodyReader extends BodyDeclReader {
	private Block block;
	private Node methodNode;
	private MethodDecl method;
	private Node bodyNode;

	public MethodBodyReader(Block block, Node methodNode, MethodDecl method, Graph graph) {
		super(method, graph);
		this.block = block;
		this.methodNode = methodNode;
		this.method = method;
	}

	@Override
	public Graph read() {
		String name = methodNode.getFullName() + ".body";
		bodyNode = addNode(name, Node.Type.MethodBody, method);

		if (block.getNumStmt() > 0) {
			addMethodBodyTypeDependency();
		}

		addMSignatureDependency();

		return getGraph();
	}


	private void addMSignatureDependency() {
		addEdge(methodNode.getFullName(), bodyNode.getFullName(), Edge.Type.Contains);
	}


	public void addMethodBodyTypeDependency() {   
		DepStmt(block.getStmtList());     
	}

	public void DepStmt(List <Stmt> bloc) {
		for (Stmt s : bloc) {
			DepSingStmt(s);
		}
	}

	public void DepSingStmt(Stmt s) {
		TypeDecl stmtType;
		MethodAccess ma = null;

		/*************** Var  Decl*****************/
		if (s instanceof VarDeclStmt) {
			VarDeclStmt varStmt = (VarDeclStmt) s;

			/*Dep methodBody - Var type*/
			stmtType = varStmt.type();
			addTypeDependency(stmtType, Edge.Type.Uses);

			/*dep method body var value*/
			if (varStmt.hasDeclarator()) {
				for (VariableDeclarator d : varStmt.getDeclaratorList()) {
					if (d.hasInit()) {
						Expr init = d.getInit();
						DepExpr(init);
					}

				}
			}

		}
		/****EXPR *****/
		if (s instanceof ExprStmt) {
			Expr e = ((ExprStmt) s).getExpr();
			DepExpr(e);
		}


		/*******IF stmt*****/
		if (s instanceof IfStmt) {
			IfStmt ifstmt = (IfStmt)s;
			/*Dep condition */
			Expr condition = (Expr)ifstmt.getCondition();
			DepExpr(condition);

			/*Dep then */
			Stmt ifblock = ifstmt.getThen();
			if( ifblock instanceof Block) {
				Block bif = (Block)ifblock;
				DepStmt(bif.getStmtList());
			}

			/*Dep else */
			if (ifstmt.hasElse()) {
				Stmt elseE = ifstmt.getElse();
				if( elseE instanceof Block ) {
					Block belse = (Block)elseE;
					DepStmt(belse.getStmtList());
				}
			}

		}

		/**********Try-Catch ******************/
		if (s instanceof TryStmt) {

			TryStmt trystmt = (TryStmt) s;

			/*Dep try blocl */
			DepStmt(trystmt.getBlock().getStmtList());

			/*catch*/
			if(trystmt.hasCatchClause()) {
				for( CatchClause cc : trystmt.getCatchClauseList()) {
					/* Dep catch clause block */					
					DepStmt(cc.getBlock().getStmtList());

				}
			}
			/*finally */
			if(trystmt.hasFinally()) {
				/* Dep finally block */					
				DepStmt(trystmt.getFinally().getStmtList());

			}
		}



		/************While **************/
		if (s instanceof WhileStmt) {
			WhileStmt whilestmt = (WhileStmt)s;
			Expr condition = (Expr)whilestmt.getCondition();
			DepExpr(condition);
			Stmt swhile = (Stmt) whilestmt.getStmt();

			if( swhile instanceof Block) {
				Block bwhile = (Block) swhile;
				DepStmt(bwhile.getStmtList());
			}

		}


		/************For ****************/
		if (s instanceof ForStmt) {
			ForStmt forstmt = (ForStmt)s;

			/*Dep init */
			Stmt sinit = forstmt.getInitStmt(0);
			DepSingStmt(sinit);

			/*Dep condition */
			Expr condition = forstmt.getCondition();
			DepExpr(condition);

			/*Dep update stmt */
			Stmt upstmt = forstmt.getUpdateStmt(0);
			DepSingStmt(upstmt);

			Stmt sfor = (Stmt) forstmt.getStmt();
			if( sfor instanceof Block) {
				Block bfor = (Block) sfor;
				DepStmt(bfor.getStmtList());
			}


		}

		/****Local class ***/
		if (s instanceof LocalClassDeclStmt) {
			LocalClassDeclStmt lcds = (LocalClassDeclStmt)s;
			TypeDecl localType = lcds.getClassDecl();
			ClassReader reader = new ClassReader((ClassDecl) localType, graph);
			reader.read();
			addTypeDependency(localType, Edge.Type.Contains);
		}

		/************SWITCH *******/
		if (s instanceof SwitchStmt) {
			SwitchStmt ss = (SwitchStmt)s;
			DepStmt(ss.getBlock().getStmtList());
			DepExpr(ss.getExpr());
		}





	}

	public void DepExpr (Expr e) {
		MethodAccess ma;

		if (e.isMethodAccess()) {

			if (e instanceof Dot) {
				if (!((Dot) e).getLeft().isThisAccess()) {
					ma = (MethodAccess) ((Dot) e).getRight();
					BodyotherMethodDep(ma);
				}
			}
		}

		if (e instanceof AssignSimpleExpr) {
			AssignSimpleExpr ase = (AssignSimpleExpr) e;
			DepExpr(ase.getSource());
			DepExpr(ase.getDest());
		}

		if (e instanceof Dot) {
			Dot d = (Dot) e;

			/*Dep field access */
			if (d.isFieldAccess() && !d.getLeft().isThisAccess()) {
				String fullName = d.getLeft().type().fullName()+ "." + d.getRight();
				addEdge(getFullName(), fullName, Edge.Type.Uses);
			}

			/*dep static func call */
			if(d.getLeft() instanceof TypeAccess) {
				String fullName = d.getLeft().type().fullName() + "." + d.getRight();
				addEdge(getFullName(), fullName, Edge.Type.Uses);
			}
		}

		if (e instanceof VarAccess) {
			if (e.isFieldAccess()) {
				String hostTypeName = ((VarAccess) e).decl().fieldDecl().hostType().fullName();
				String name = hostTypeName + "." + ((VarAccess) e).name();
				addEdge(bodyNode.getFullName(), name, Edge.Type.Uses);
			}
		}

		if(e instanceof RelationalExpr ) {
			Expr e1 = ((RelationalExpr) e).getLeftOperand();
			Expr e2 = ((RelationalExpr) e).getRightOperand();
			DepExpr(e1);
			DepExpr(e2);

		}
		if ( e instanceof LogNotExpr) {
			LogNotExpr lne = (LogNotExpr)e;
			DepExpr(lne.getOperand());
		}
		if ( e instanceof AndLogicalExpr) {
			AndLogicalExpr ale = (AndLogicalExpr)e;
			DepExpr(ale.getLeftOperand());
			DepExpr(ale.getRightOperand());
		}
		if ( e instanceof  OrLogicalExpr) {
			OrLogicalExpr ole = (OrLogicalExpr)e;
			DepExpr(ole.getLeftOperand());
			DepExpr(ole.getRightOperand());
		}

	}

	public void BodyotherMethodDep(MethodAccess ma) {
		MethodDecl m =  ma.decl();
		String fullName = ma.methodHost() + "." + m.fullSignature();
		addEdge(bodyNode.getFullName(), fullName, Edge.Type.Uses);
	}

	@Override
	String getFullName() {
		return methodNode.getFullName() + ".body";
	}
}