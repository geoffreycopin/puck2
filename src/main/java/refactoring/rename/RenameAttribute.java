package refactoring.rename;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Queries;
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
        Integer hostType = Queries.parent(getId(), getGraph());
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
        Integer currentPackage = Queries.typePackage(Queries.parent(getId(), getGraph()), getGraph());
        Integer hostType = Queries.parent(getId(), getGraph());
        return Queries.hierarchicalParents(hostType, getGraph()).stream()
                .flatMap((n) -> getGraph().queryNodesFrom(n, Edge.Type.Contains).stream())
                .filter((n) -> {
                    if (n.getType() != Node.Type.Attribute) {
                        return false;
                    }
                    FieldDeclarator f = (FieldDeclarator) n.getExtendjNode();
                    return f.isPublic() || f.isProtected() || (! f.hasModifiers()
                            && Queries.typePackage(n.getId(), getGraph()).equals(currentPackage));
                })
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
