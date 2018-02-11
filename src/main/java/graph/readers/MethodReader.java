package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.List;
import java.util.Map;

public class MethodReader extends AbstractReader {
    private MethodDecl methodDecl;
    private Node methodNode;

    public MethodReader(MethodDecl methodDecl, UniqueIdGenerator generator) {
        super(generator);
        this.methodDecl = methodDecl;
    }

    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        if (nodes.containsKey(getUniqueName())) {
            return;
        }

        methodNode = new Node(idGenerator.generate(), getUniqueName(),
                              Node.Type.Method, methodDecl.getTypeAccess());
        nodes.put(methodNode.getFullName(), methodNode);

        addParametersDependency(nodes, edges);
        addHostClassDependency(nodes, edges);
    }

    private void addHostClassDependency(Map<String, Node> nodes, List<Edge> edges) {
        if (! nodes.containsKey(getHostType().fullName())) {
            new TypeDeclReader(getHostType(), idGenerator).readInto(nodes, edges);
        }

        Node hostTypeNode = nodes.get(getHostType().fullName());
        Edge dependencyEdge = new Edge(hostTypeNode.getId(), methodNode.getId(), Edge.Type.Contains);
        edges.add(dependencyEdge);
    }

    private void addParametersDependency(Map<String, Node> nodes, List<Edge> edges) {
        for (ParameterDeclaration p: methodDecl.getParameterList()) {
            TypeDecl parameterType = p.getTypeAccess().type();
            if (Util.isPrimitiveType(parameterType)) { return; }

            if (! nodes.containsKey(parameterType.fullName())) {
                new TypeDeclReader(parameterType, idGenerator).readInto(nodes, edges);
            }
            Node parametreNode = nodes.get(parameterType.fullName());
            Edge dependencyEdge = new Edge(methodNode.getId(), parametreNode.getId(), Edge.Type.Uses);
            edges.add(dependencyEdge);
        }
    }

    private TypeDecl getHostType() {
        return methodDecl.hostType();
    }

    private String getUniqueName() {
        return String.format("%s.%s", getHostType().fullName(), methodDecl.fullSignature());
    }
}
