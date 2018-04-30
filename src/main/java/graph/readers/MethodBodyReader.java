package graph.readers;

import java.util.Map;
import java.util.Set;

import org.extendj.ast.AssignSimpleExpr;
import org.extendj.ast.Block;
import org.extendj.ast.Dot;
import org.extendj.ast.Expr;
import org.extendj.ast.ExprStmt;
import org.extendj.ast.MethodAccess;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Stmt;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.VarDeclStmt;
import org.extendj.ast.VariableDeclarator;

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
            if (s.value instanceof VarDeclStmt) {
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
            if (s.value instanceof ExprStmt) {
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
        }

        if (e instanceof Dot) {
            Dot d = (Dot) e;
            if (d.isFieldAccess() && !d.getLeft().isThisAccess()) {
                String fullName = d.getLeft().type() + "." + d.getRight();
                edges.add(new Edge(idGenerator.idFor(bodyNode.getFullName()), idGenerator.idFor(fullName), Edge.Type.Uses));
            }
        }
    }

    public void BodyotherMethodDep(MethodAccess ma, Set<Edge> edges) {
        MethodDecl m =  ma.decl();
        String fullName = ma.methodHost() + "." + m.fullSignature();
        edges.add(new Edge(idGenerator.idFor(bodyNode.getFullName()), idGenerator.idFor(fullName), Edge.Type.Uses));
    }

    @Override
    String getFullName() {
        return methodNode.getFullName() + ".body";
    }
}