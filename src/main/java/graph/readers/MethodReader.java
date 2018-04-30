package graph.readers;

import java.util.Map;
import java.util.Set;

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
    public void readInto(Map<Integer, Node> nodes, Set<Edge> edges) {
        String fullName = getHostClassName() + "." + methodDecl.fullSignature();
       
       
        methodNode = new Node(idGenerator.idFor(fullName), fullName, Node.Type.Method,
                methodDecl);

        nodes.put(idGenerator.idFor(fullName), methodNode);
  
        
        if (methodDecl.hasBlock()) {
            Block b = methodDecl.getBlock();
            MethodBodyReader mbreader = new MethodBodyReader(idGenerator, b, methodNode, methodDecl);
            mbreader.readInto(nodes, edges);
        }

        addHostClassDependency(edges);
        addReturnTypeDependency(edges,nodes);
        addParametersTypeDependency(edges,nodes);
        addExceptionsTypesDependencies(edges, nodes);
    }
    

    private String getHostClassName() {
        return methodDecl.hostType().fullName();
    }

    private void addHostClassDependency(Set<Edge> edges) {
        edges.add(new Edge(idGenerator.idFor(getHostTypeName()), idGenerator.idFor(methodNode.getFullName()),
                Edge.Type.Contains));
    }

    private void addReturnTypeDependency(Set<Edge> edges,Map<Integer, Node> nodes) {
        addTypeDependency(edges, methodDecl.type(), Edge.Type.Uses, nodes);
    }

    private void addParametersTypeDependency(Set<Edge> edges,Map<Integer, Node> nodes) {
        for (ParameterDeclaration p: methodDecl.getParameterList()) {
            TypeDecl parameterType = p.getTypeAccess().type();
            addTypeDependency(edges, parameterType, Edge.Type.Uses,nodes);
        }
    }

    private void addExceptionsTypesDependencies(Set<Edge> edges, Map<Integer, Node> nodes) {
        for (Access a: methodDecl.getExceptionList()) {
            addTypeDependency(edges, a.type(), Edge.Type.Uses, nodes);
        }
    }
}
