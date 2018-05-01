package graph.readers;

import java.util.Map;
import java.util.Set;

import org.extendj.ast.*;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class MethodBodyReader extends BodyDeclReader {
    private Block block;
    private Node methodNode;
    private MethodDecl method;
    private Node bodyNode;

    public MethodBodyReader(UniqueIdGenerator generator, Block block, Node methodNode, MethodDecl method) {
        super(method, generator);
        this.block = block;
        this.methodNode = methodNode;
        this.method = method;
    }

    @Override
    public void readInto(Map<Integer, Node> nodes, Set<Edge> edges) {
        String name = methodNode.getFullName() + ".body";
        bodyNode = new Node(idGenerator.idFor(name), name, Node.Type.MethodBody,
                method);

        nodes.put(idGenerator.idFor(name), bodyNode);

        if (block.getNumStmt() > 0) {
            addMethodBodyTypeDependency(edges, nodes);
        }

        addMSignatureDependency(edges);

    }

    private void addMSignatureDependency(Set<Edge> edges) {
        Edge e = new Edge(idGenerator.idFor(methodNode.getFullName()), idGenerator.idFor(bodyNode.getFullName()),
                Edge.Type.Contains);
        edges.add(e);
    }

    public void addMethodBodyTypeDependency(Set<Edge> edges, Map<Integer, Node> nodes) {
        TypeDecl stmtType;
        MethodAccess ma = null;

        for (Stmt s : block.getStmtList()) {
            /*************** Var  Decl*****************/
            if (s instanceof VarDeclStmt) {
                VarDeclStmt varStmt = (VarDeclStmt) s;

                /*Dep methodBody - Var type*/
                stmtType = varStmt.type();
                addTypeDependency(edges, stmtType, Edge.Type.Uses, nodes);

                /*dep method body var value*/
                if (varStmt.hasDeclarator()) {
                    for (VariableDeclarator d : varStmt.getDeclaratorList()) {
                        if (d.hasInit()) {
                            Expr init = d.getInit();
                            DepExpr(init, edges, nodes);
                        }

                    }
                }

            }
            /****EXPR *****/
            if (s instanceof ExprStmt) {
                Expr e = ((ExprStmt) s).getExpr();
                DepExpr(e, edges, nodes);
            }
        }
    }

    public void DepExpr(Expr e, Set<Edge> edges, Map<Integer, Node> nodes) {
        MethodAccess ma;

        if (e.isMethodAccess()) {

            if (e instanceof Dot) {
                if (!((Dot) e).getLeft().isThisAccess()) {
                    ma = (MethodAccess) ((Dot) e).getRight();
                    BodyotherMethodDep(ma, edges);
                }
            }
        }

        if (e instanceof AssignSimpleExpr) {
            AssignSimpleExpr ase = (AssignSimpleExpr) e;
            DepExpr(ase.getSource(), edges, nodes);
            DepExpr(ase.getDest(), edges, nodes);
        }

        if (e instanceof Dot) {
            Dot d = (Dot) e;
            if (d.isFieldAccess() && !d.getLeft().isThisAccess()) {
                String fullName = d.getLeft().type() + "." + d.getRight();
                edges.add(createEdge(bodyNode.getFullName(), fullName, Edge.Type.Uses, e));
            }
        }

        if (e instanceof VarAccess) {
            if (e.isFieldAccess()) {
                String hostTypeName = ((VarAccess) e).decl().fieldDecl().hostType().fullName();
                String name = hostTypeName + "." + ((VarAccess) e).name();
                edges.add(createEdge(bodyNode.getFullName(), name, Edge.Type.Uses, e))  ;
            }
        }
    }

    public void BodyotherMethodDep(MethodAccess ma, Set<Edge> edges) {
        MethodDecl m =  ma.decl();
        String fullName = ma.methodHost() + "." + m.fullSignature();
        edges.add(createEdge(bodyNode.getFullName(), fullName, Edge.Type.Uses, ma));
    }

    @Override
    String getFullName() {
        return methodNode.getFullName() + ".body";
    }
}