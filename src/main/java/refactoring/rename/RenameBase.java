package refactoring.rename;

import graph.Graph;
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
}
