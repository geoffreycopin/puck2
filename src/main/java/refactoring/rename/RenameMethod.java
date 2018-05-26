package refactoring.rename;

import app.Puck2Runner;
import graph.Graph;
import graph.Queries;
import org.extendj.ast.ASTNode;
import org.extendj.ast.Access;
import org.extendj.ast.MethodDecl;
import refactoring.RefactoringError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RenameMethod extends RenameBase {
    RenameMethod(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void check() {
        checkName(getNewName());
        Integer hostType = Queries.hostType(getId(), getGraph());
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
