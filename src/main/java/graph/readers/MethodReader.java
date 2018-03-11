package graph.readers;

import java.util.ArrayList;
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
    protected String getFullName() {
        return methodNode.getFullName();
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
        addReturnTypeDependency(edges,nodes);
        addParametersTypeDependency(edges,nodes);
    }

    private String getHostClassName() {
        return methodDecl.hostType().fullName();
    }

    private void addHostClassDependency(List<Edge> edges) {
        edges.add(new Edge(getHostTypeName(), methodNode.getFullName(), Edge.Type.Contains));
    }

    private void addReturnTypeDependency(List<Edge> edges,Map<String, Node> nodes) {
        addTypeDependency(edges, methodDecl.type(), Edge.Type.Uses,nodes);
    }

    private void addParametersTypeDependency(List<Edge> edges,Map<String, Node> nodes) {
        for (ParameterDeclaration p: methodDecl.getParameterList()) {
            TypeDecl parameterType = p.getTypeAccess().type();
            addTypeDependency(edges, parameterType, Edge.Type.Uses,nodes);
        }
    }
}
