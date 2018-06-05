package refactoring.rename;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Queries;
import org.extendj.ast.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RenameInterface extends RenameBase {

    RenameInterface(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void check() {
        checkName(getNewName());
        checkTypeNameAvailability();
    }

    @Override
    protected void refactorCode() {
        InterfaceDecl i = (InterfaceDecl) getGraph().getNode(getId()).getExtendjNode();
        i.setID(getNewName());
        renameTypeImports(i.createQualifiedAccess());
        updateSubInterfaces(i.createBoundAccess());
        updateImplementingClasses(i.createBoundAccess());
        updateFieldDeclarations(i.createBoundAccess());
        updateMethodParam(i.createBoundAccess());
        renameReferences(i.createBoundAccess());
    }

    private void updateSubInterfaces(Access newAccess) {
        for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.IsA)) {
            if (n.getExtendjNode() instanceof InterfaceDecl) {
                InterfaceDecl sub = (InterfaceDecl) n.getExtendjNode();
                int superInterfaceId = getInterfaceIndex(sub.getSuperInterfaceList(), newAccess.type().fullName());
                sub.setSuperInterface(newAccess, superInterfaceId);
            }
        }
    }

    private int getInterfaceIndex(List<Access> superInterfaces, String name) {
        int index = 0;
        for (Access a: superInterfaces) {
            if ((a.type().fullName().equals(name))) {
                return index;
            }
            index ++;
        }
        return -1;
    }

    private void updateImplementingClasses(Access newAccess) {
        for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.IsA)) {
            ClassDecl implementingClass = (ClassDecl) n.getExtendjNode();
            int idx = getInterfaceIndex(implementingClass.getImplementsList(), newAccess.type().fullName());
            if (idx != -1) {
                implementingClass.setImplements(newAccess, idx);
            }
        }
    }
}
