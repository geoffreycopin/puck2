package refactoring.rename;

import graph.Graph;
import graph.Queries;
import org.extendj.ast.MethodDecl;
import refactoring.RefactoringError;

public class RenameMethod extends RenameBase {
    RenameMethod(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void check() {
        checkName(getNewName());
        Integer hostType = Queries.parent(getId(), getGraph());
        for (Integer id: Queries.methodsInType(hostType, getGraph())) {
            String methodName = getGraph().getNode(id).getFullName();
            if (methodName.equals(getNewFullName())) {
                throw new RefactoringError("Method " + methodName + " already exists !");
            }
        }
    }

    @Override
    protected void refactorCode() {
        MethodDecl method = (MethodDecl) getGraph().getNode(getId()).getExtendjNode();
        method.setID(getNewName());
        renameReferences(method.getTypeAccess());
    }

    @Override
    protected String getNewFullName() {
        String[] components = getOldName().split("\\(");
        String left = components[0];
        String right = components[1];
        String[] leftComps = left.split("\\.");
        leftComps[leftComps.length - 1] = getNewName();
        return String.join(".", leftComps) + "(" + right;
    }
}
