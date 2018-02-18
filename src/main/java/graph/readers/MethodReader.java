package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.*;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class MethodReader extends BodyDeclReader {
    private MethodDecl methodDecl;
    private Node methodNode;

    public MethodReader(UniqueIdGenerator idGenerator, MethodDecl methodDecl) {
        super(methodDecl, idGenerator);
        this.methodDecl = methodDecl;
    }


    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        String fullName = getHostClassName() + "." + methodDecl.fullSignature();

        methodNode = new Node(idGenerator.generate(), fullName, Node.Type.Method,
                methodDecl.getTypeAccess());
        nodes.put(fullName, methodNode);

        if (methodDecl.hasBlock()) {
            Block b = methodDecl.getBlock();
            MethodBodyReader mbreader = new MethodBodyReader(idGenerator, b, methodNode, methodDecl);
            mbreader.readInto(nodes, edges);
        }

        addHostClassDependency(edges);
        addParametersTypeDependency(edges);
    }

    private String getHostClassName() {
        return methodDecl.hostType().fullName();
    }

    private void addHostClassDependency(List<Edge> edges) {
        edges.add(new Edge(getHostTypeName(), methodNode.getFullName(), Edge.Type.Contains));
    }

    private void addParametersTypeDependency(List<Edge> edges) {
        for (ParameterDeclaration p: methodDecl.getParameterList()) {
            TypeDecl parameterType = p.getTypeAccess().type();

            if (parameterType.isParameterizedType()) {
                addGenericTypeDependency(edges, parameterType);
            } else if (! Util.isPrimitive(parameterType)) {
                edges.add(new Edge(methodNode.getFullName(), parameterType.fullName(), Edge.Type.Uses));
            }
        }
    }

    private void addGenericTypeDependency(List<Edge> edges, TypeDecl type) {
        String genericTypeName = TypeDeclReader.getGenericTypeName(type);
        edges.add(new Edge(methodNode.getFullName(), genericTypeName, Edge.Type.Uses));

        for (String typeParameterName: TypeDeclReader.getTypeParametersName(type)) {
            edges.add(new Edge(methodNode.getFullName(), typeParameterName, Edge.Type.Uses));
        }
    }
}
