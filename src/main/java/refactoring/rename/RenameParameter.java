package refactoring.rename;

import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RenameParameter extends RenameBase {
    RenameParameter(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void check() {
        checkName(getNewName());
    }

    @Override
    protected void refactorCode() {
        ParameterDeclaration p = (ParameterDeclaration) getGraph().getNode(getId())
                .getExtendjNode();
        p.setID(getNewName());
        renameReferences(p.getTypeAccess());

        Integer enclosingMethod = findEnclosingMethod(getId());
        Integer enclosingInterface = findEnclosingInterface(enclosingMethod);
        if (enclosingInterface != null) {
            String methodName = ((MethodDecl)getGraph().getNode(enclosingMethod).getExtendjNode()).fullSignature();
            int paramId = getParamIndex(enclosingMethod);
            renameParamInAllImpls(enclosingInterface, methodName, paramId);
        }
    }

    private Integer findEnclosingMethod(Integer paramId) {
        for (Node n: getGraph().queryNodesTo(paramId, Edge.Type.Contains)) {
            if (n.getType() == Node.Type.Method) {
                return n.getId();
            }
        }
        return null;
    }

    private Integer findEnclosingInterface(Integer methodId) {
        for (Node n: getGraph().queryNodesTo(methodId, Edge.Type.Contains)) {
            if (n.getType() == Node.Type.Interface) {
                return n.getId();
            }
        }
        return null;
    }

    private int getParamIndex(Integer enclosingMethod) {
        MethodDecl m = (MethodDecl) getGraph().getNode(enclosingMethod).getExtendjNode();
        int i = 0;
        for (ParameterDeclaration p: m.getParameters()) {
            if (p.getID().equals(getNewName())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void renameParamInAllImpls(Integer interfaceId, String methodSignature, int paramId) {
        getImplementingClasses(interfaceId).stream()
                .flatMap((c) -> getMethods(c).stream())
                .filter((m) -> ((MethodDecl) getGraph().getNode(m).getExtendjNode()).signature().equals(methodSignature))
                .forEach((m) -> renameNthParameter(m, paramId));
    }

    private List<Integer> getImplementingClasses(Integer interfaceId) {
        return getGraph().queryNodesTo(interfaceId, Edge.Type.IsA).stream()
                .filter((n) -> n.getType() == Node.Type.Class)
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Integer> getMethods(Integer typeId) {
        return getGraph().queryNodesFrom(typeId, Edge.Type.Contains).stream()
                .filter((m) -> m.getType() == Node.Type.Method)
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void renameNthParameter(Integer method, int paramIndex) {
        MethodDecl m = (MethodDecl) getGraph().getNode(method).getExtendjNode();
        ParameterDeclaration p = m.getParameter(paramIndex);
        String fullName = getGraph().getNode(method).getFullName() + "." + p.getID();
        Integer targetId = getGraph().getNode(fullName).getId();
        RenameParameter r = new RenameParameter(targetId, getNewName(), getGraph());
        r.refactor();
    }
}
