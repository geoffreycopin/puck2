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
        TypeDecl stmtType;
        MethodAccess ma = null;

        for (Stmt s : block.getStmtList()) {
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
        }
    }

    public void DepExpr(Expr e) {
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
            if (d.isFieldAccess() && !d.getLeft().isThisAccess()) {
                String fullName = d.getLeft().type() + "." + d.getRight();
                addEdge(getFullName(), fullName, Edge.Type.Uses, e);
            }
        }

        if (e instanceof VarAccess) {
            if (e.isFieldAccess()) {
                String hostTypeName = ((VarAccess) e).decl().fieldDecl().hostType().fullName();
                String name = hostTypeName + "." + ((VarAccess) e).name();
                addEdge(bodyNode.getFullName(), name, Edge.Type.Uses, e);
            }
        }
    }

    public void BodyotherMethodDep(MethodAccess ma) {
        MethodDecl m =  ma.decl();
        String fullName = ma.methodHost() + "." + m.fullSignature();
        addEdge(bodyNode.getFullName(), fullName, Edge.Type.Uses, ma);
    }

    @Override
    String getFullName() {
        return methodNode.getFullName() + ".body";
    }
}