package refactoring.rename;

import graph.Graph;
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
}
