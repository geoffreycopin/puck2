package refactoring.rename;

import graph.Graph;
import graph.Queries;
import org.extendj.ast.ASTNode;
import org.extendj.ast.Access;
import org.extendj.ast.FieldDeclarator;
import refactoring.RefactoringError;

public class RenameAttribute extends RenameBase {
    RenameAttribute(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void refactorCode() {
        FieldDeclarator field = (FieldDeclarator) getGraph().getNode(getId()).getExtendjNode();
        field.setID(getNewName());
        renameReferences(field.createBoundAccess().lastAccess());
    }

    @Override
    protected void check() {
        Integer hostType = Queries.hostType(getId(), getGraph());
        for (Integer id: Queries.classAttributes(hostType, getGraph())) {
            String attrName = Queries.lastComponent(getGraph().getNode(id).getFullName());
            if (attrName.equals(getNewName())) {
                throw new RefactoringError("Field " + getNewName() + " already exists !");
            }
        }
    }
}
