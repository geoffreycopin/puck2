package refactoring.rename;

import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.*;
import refactoring.RefactoringBase;

public abstract class RenameBase extends RefactoringBase {
    private Integer id;
    private String newName;
    private String oldName;

    RenameBase(Integer id, String newName, Graph graph) {
        super(graph);
        this.id = id;
        this.newName = newName;
        this.oldName = graph.getNode(id).getFullName();
    }

    public Integer getId() {
        return id;
    }

    public String getNewName() {
        return newName;
    }

    public String getOldName() {
        return oldName;
    }

    @Override
    protected void refactorGraph() {
        getGraph().renameNode(id, getNewFullName());
    }

    protected void renameReferences(Access a) {
        for (ASTNode<ASTNode> ref: getGraph().getReferences(getId())) {
            if (ref instanceof MethodAccess) {
                ((MethodAccess) ref).setID(getNewName());
            } else if (ref instanceof VarAccess) {
                ((VarAccess) ref).setID(getNewName());
            } else if (ref instanceof Dot) {
                ((Dot) ref).setRight(a);
            }
        }
    }

    protected void updateMethodParam(Access newAccess) {
        for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.Uses)) {
            if (n.getExtendjNode() instanceof ParameterDeclaration) {
                ParameterDeclaration p = (ParameterDeclaration) n.getExtendjNode();
                if(p.getTypeAccess().type().fullName().equals(getOldName())) {
                    p.setTypeAccess(newAccess);
                }
            }
        }
    }

    protected void updateFieldDeclarations(Access newAccess) {
        for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.Uses)) {
            if (n.getExtendjNode() instanceof FieldDeclarator) {
                FieldDeclarator f = (FieldDeclarator) n.getExtendjNode();
                f.fieldDecl().setTypeAccess(newAccess);
            }
        }
    }

    protected String getNewFullName() {
        String[] components = oldName.split("\\.");
        if (components.length == 0) {
            return newName;
        }
        String lastComponent = components[components.length - 1].split("\\(")[0];
        components[components.length - 1] = components[components.length - 1].replace(lastComponent, newName);
        return String.join(".", components);
    }
}
