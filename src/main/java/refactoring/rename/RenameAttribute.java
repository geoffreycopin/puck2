package refactoring.rename;

import graph.Graph;
import graph.Queries;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;
import refactoring.RefactoringError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void check() {
        checkName(getNewName());
        Integer hostType = Queries.hostType(getId(), getGraph());
        checkExistingFieldsNames(Queries.classAttributes(hostType, getGraph()));
        checkExistingFieldsNames(visibleSuperClassAttributes());
    }

    protected void checkExistingFieldsNames(List<Integer> fields) {
        for (Integer id: fields) {
            String attrName = Queries.lastComponent(getGraph().getNode(id).getFullName());
            if (attrName.equals(getNewName())) {
                throw new RefactoringError("Field " + getNewName() + " already exists !");
            }
        }
    }

    private List<Integer> visibleSuperClassAttributes() {
        return Queries.superTypes(getId(), getGraph()).stream()
                .flatMap((n) -> Queries.classAttributes(n, getGraph()).stream())
                .filter((n) -> {
                    FieldDeclarator f = (FieldDeclarator) getGraph().getNode(n).getExtendjNode();
                    return f.isProtected() || f.isPublic();
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
