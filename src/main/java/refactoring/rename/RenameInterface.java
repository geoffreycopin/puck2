package refactoring.rename;

import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.*;

public class RenameInterface extends RenameBase {

    RenameInterface(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    protected void refactorCode() {
        InterfaceDecl i = (InterfaceDecl) getGraph().getNode(getId()).getExtendjNode();
        i.setID(getNewName());
        i.flushAttrAndCollectionCache();
        i.createQualifiedAccess().treeCopy();
        updateSubInterfaces(i.createBoundAccess());
        updateImplementingClasses(i.createBoundAccess());
    }

    private void updateSubInterfaces(Access newAccess) {
        for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.IsA)) {
            if (n.getExtendjNode() instanceof InterfaceDecl) {
                InterfaceDecl sub = (InterfaceDecl) n.getExtendjNode();
                int superInterfaceId = getInterfaceIndex(sub.getSuperInterfaceList());
                sub.setSuperInterface(newAccess, superInterfaceId);
            }
        }
    }

    private int getInterfaceIndex(List<Access> superInterfaces) {
        int index = 0;
        for (Access a: superInterfaces) {
            if ((a.type().fullName().equals(getOldName()))) {
                return index;
            }
            index ++;
        }
        return -1;
    }

    private void updateImplementingClasses(Access newAccess) {
        for (Node n: getGraph().queryNodesTo(getId(), Edge.Type.IsA)) {
            ClassDecl implementingClass = (ClassDecl) n.getExtendjNode();
            int idx = getInterfaceIndex(implementingClass.getImplementsList());
            if (idx != -1) {
                implementingClass.setImplements(newAccess, idx);
            }
        }
    }
}
