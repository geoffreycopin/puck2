package refactoring.rename;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Queries;
import org.extendj.ast.*;
import refactoring.RefactoringBase;
import refactoring.RefactoringError;

import java.util.ArrayList;
import java.util.List;

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
            } else if (ref instanceof TypeAccess) {
                ((TypeAccess) ref).setID(getNewName());
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

    protected List<Integer> otherTypesInPackage() {
        Integer p = Queries.typePackage(getId(), getGraph());
        if (p == null) {
            return new ArrayList<>();
        } else {
            return Queries.typesInPackage(p, getGraph());
        }
    }

    protected void checkTypeNameAvailability() {
        for (Integer id: otherTypesInPackage()) {
            String typeName = getGraph().getNode(id).getFullName();
            if (Queries.lastComponent(typeName).equals(getNewName())) {
                throw new RefactoringError("Type " + getNewFullName() + " already exists !");
            }
        }
    }
}
