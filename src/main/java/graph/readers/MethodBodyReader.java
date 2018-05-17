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
						readExpr(init);
					}

				}
			}

		}
		/****EXPR *****/
		if (s instanceof ExprStmt) {
			Expr e = ((ExprStmt) s).getExpr();
			readExpr(e);
		}


		/*******IF stmt*****/
		if (s instanceof IfStmt) {
			IfStmt ifstmt = (IfStmt)s;
			/*Dep condition */
			Expr condition = (Expr)ifstmt.getCondition();
			readExpr(condition);

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
			readExpr(condition);
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
			readExpr(condition);

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
			readExpr(ss.getExpr());
		}

		if (s instanceof ReturnStmt) {
		    ReturnStmt rs = (ReturnStmt) s;
		    if (rs.getResult() != null) {
		        readExpr(rs.getResult());
            }
        }

	}

//	public void DepExpr (Expr e) {
//		MethodAccess ma;
//
//		if (e.isMethodAccess()) {
//		    if (e instanceof Dot) {
//				if (!((Dot) e).getLeft().isThisAccess()) {
//					ma = (MethodAccess) ((Dot) e).getRight();
//					BodyotherMethodDep(ma);
//					if (((Dot) e).getLeft() instanceof VarAccess) {
//					    readVarAccess((VarAccess) ((Dot) e).getLeft());
//                    }
//				}
//			}
//		}
//
//		if (e instanceof AssignSimpleExpr) {
//			AssignSimpleExpr ase = (AssignSimpleExpr) e;
//			DepExpr(ase.getSource());
//			DepExpr(ase.getDest());
//		}
//
//		if (e instanceof Dot) {
//			Dot d = (Dot) e;
//
//			/*Dep field access */
//			if (d.isFieldAccess() && !d.getLeft().isThisAccess()) {
//				String fullName = d.getLeft().type().fullName()+ "." + d.getRight();
//				addEdge(getFullName(), fullName, Edge.Type.Uses);
//				addReference(fullName, d);
//			}
//
//			/*dep static func call */
//			if(d.getLeft() instanceof TypeAccess) {
//				String fullName = d.getLeft().type().fullName() + "." + d.getRight();
//				addEdge(getFullName(), fullName, Edge.Type.Uses);
//				addReference(fullName, d);
//			}
//		}
//
//		if (e instanceof VarAccess) {
//			readVarAccess((VarAccess) e);
//		}
//
//		if(e instanceof RelationalExpr ) {
//			Expr e1 = ((RelationalExpr) e).getLeftOperand();
//			Expr e2 = ((RelationalExpr) e).getRightOperand();
//			DepExpr(e1);
//			DepExpr(e2);
//
//		}
//		if ( e instanceof LogNotExpr) {
//			LogNotExpr lne = (LogNotExpr)e;
//			DepExpr(lne.getOperand());
//		}
//		if ( e instanceof AndLogicalExpr) {
//			AndLogicalExpr ale = (AndLogicalExpr)e;
//			DepExpr(ale.getLeftOperand());
//			DepExpr(ale.getRightOperand());
//		}
//		if ( e instanceof  OrLogicalExpr) {
//			OrLogicalExpr ole = (OrLogicalExpr)e;
//			DepExpr(ole.getLeftOperand());
//			DepExpr(ole.getRightOperand());
//		}
//		if (e instanceof MulExpr) {
//		    MulExpr m = (MulExpr) e;
//		    DepExpr(m.getLeftOperand());
//		    DepExpr(m.getRightOperand());
//        }
//
//	}

	private void readExpr(Expr e) {
	    if (e instanceof Access) {
	        readAccess((Access) e);
        } else if (e instanceof ArrayInit) {
	        readArrayInit((ArrayInit) e);
        } else if (e instanceof AssignExpr) {
	        readAssignExpr((AssignExpr) e);
        } else if (e instanceof Binary) {
	        readBinary((Binary) e);
        } else if (e instanceof CastExpr) {
	        readCastExpr((CastExpr) e);
        } else if (e instanceof ConditionalExpr) {
	        readConditionalExpr((ConditionalExpr) e);
        } else if (e instanceof ConstructorReference) {
	        readConstructorReference((ConstructorReference) e);
        } else if (e instanceof InstanceOfExpr) {
	        readInstanceOfExpr((InstanceOfExpr) e);
        } else if (e instanceof IntersectionCastExpr) {
	        readIntersectionCastExpr((IntersectionCastExpr) e);
        } else if (e instanceof LambdaExpr) {
	        readLambdaExpr((LambdaExpr) e);
        } else if (e instanceof MethodReference) {
	        readMethodReference((MethodReference) e);
        } else if (e instanceof PrimaryExpr) {
	        readPrimaryExpr((PrimaryExpr) e);
        } else if (e instanceof Unary) {
	        readUnary((Unary) e);
        }
    }

    private void readUnary(Unary u) {
        readExpr(u.getOperand());
    }

    private void readPrimaryExpr(PrimaryExpr p) {
        if (p instanceof ArrayCreationExpr) {
            readAccess(((ArrayCreationExpr) p).getTypeAccess());
        } else if (p instanceof ParExpr) {
            readExpr(((ParExpr) p).getExpr());
        }
    }

    private void readMethodReference(MethodReference e) {
        if (e instanceof AmbiguousMethodReference) {
            readAccess(((AmbiguousMethodReference) e).getAmbiguousName());
        } else if (e instanceof ExprMethodReference) {
            readExpr(((ExprMethodReference) e).getExpr());
        } else if (e instanceof TypeMethodReference) {
            readAccess(((TypeMethodReference) e).getTypeAccess());
        }
    }

    private void readLambdaExpr(LambdaExpr l) {
        readLambdaBody(l.getLambdaBody());
    }

    private void readLambdaBody(LambdaBody lambdaBody) {
        if (lambdaBody instanceof BlockLambdaBody) {
            DepStmt(((BlockLambdaBody) lambdaBody).getBlock().getStmtList());
        } else if (lambdaBody instanceof ExprLambdaBody) {
            readExpr(((ExprLambdaBody) lambdaBody).getExpr());
        }
    }

    private void readIntersectionCastExpr(IntersectionCastExpr i) {
        readExpr(i.getExpr());
        readAccess(i.getTypeAccess());
        for (Access a: i.getTypeLists()) {
            readAccess(a);
        }
    }

    private void readInstanceOfExpr(InstanceOfExpr i) {
        readExpr(i.getExpr());
        readAccess(i.getTypeAccess());
    }

    private void readConstructorReference(ConstructorReference c) {
        readAccess(c.getTypeAccess());
	}

    private void readConditionalExpr(ConditionalExpr e) {
        readExpr(e.getCondition());
        readExpr(e.getTrueExpr());
        readExpr(e.getFalseExpr());
    }

    private void readCastExpr(CastExpr c) {
        readAccess(c.getTypeAccess());
        readExpr(c.getExpr());
    }

    private void readBinary(Binary b) {
        readExpr(b.getRightOperand());
        readExpr(b.getLeftOperand());
    }

    private void readAssignExpr(AssignExpr a) {
        readExpr(a.getDest());
        readExpr(a.getSource());
    }

    private void readArrayInit(ArrayInit a) {
        addTypeDependency(a.type(), Edge.Type.Uses);
    }



    private void readAccess(Access a) {
	    if (a instanceof AbstractWildcard) {
	        readAbstractWidlCard((AbstractWildcard) a);
        } else if (a instanceof ArrayAccess) {
	        readArrayAccess((ArrayAccess) a);
        } else if (a instanceof ClassAccess) {
	        readClassAccess((ClassAccess) a);
        } else if (a instanceof ClassInstanceExpr) {
	        readClassInstanceExpr((ClassInstanceExpr) a);
        } else if (a instanceof ConstructorAccess) {
	        readConstructorAccess((ConstructorAccess) a);
        } else if (a instanceof DiamondAccess) {
	        readDiamondAccess((DiamondAccess) a);
        } else if (a instanceof Dot) {
	        readDotAccess((Dot) a);
        } else if (a instanceof MethodAccess) {
	        readMethodAccess((MethodAccess) a);
        } else if (a instanceof ParTypeAccess) {
	        readparTypeAccess((ParTypeAccess) a);
        } else if (a instanceof ParseName) {
	        readParseName((ParseName) a);
        } else if (a instanceof SuperAccess) {
	        readSuperAccess((SuperAccess) a);
        } else if (a instanceof SyntheticTypeAccess) {
	        readSyntheticTypeAccess((SyntheticTypeAccess) a);
        } else if (a instanceof TypeAccess) {
	        readTypeAccess((TypeAccess) a);
        } else if (a instanceof VarAccess) {
	        readVarAccess((VarAccess) a);
        }
    }

	private void readVarAccess(VarAccess v) {
        if (v.isFieldAccess()) {
            String hostTypeName = v.decl().fieldDecl().hostType().fullName();
            String name = hostTypeName + "." + v.name();
            addEdge(bodyNode.getFullName(), name, Edge.Type.Uses);
            addReference(name, v);
        } else {
            String fullName = methodNode.getFullName() + "." + v.getID();
            if (getGraph().getNode(fullName) != null) {
                addReference(fullName, v);
            }
        }
    }

	public void BodyotherMethodDep(MethodAccess ma) {
		MethodDecl m =  ma.decl();
		String fullName = ma.methodHost() + "." + m.fullSignature();
		addEdge(bodyNode.getFullName(), fullName, Edge.Type.Uses);
		addReference(fullName, ma);
	}

	@Override
	String getFullName() {
		return methodNode.getFullName() + ".body";
	}
}