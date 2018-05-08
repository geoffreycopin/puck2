package graph.readers;

import java.util.Map;
import java.util.Set;

import graph.Graph;
import org.extendj.ast.*;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

public class MethodReader extends BodyDeclReader {
    private MethodDecl methodDecl;
    private Node methodNode;

    public MethodReader(MethodDecl methodDecl, Graph graph) {
        super(methodDecl, graph);
        this.methodDecl = methodDecl;
    }

    @Override
    protected String getFullName() {
        return methodNode != null ? methodNode.getFullName() : "";
    }

    @Override
    public Graph read() {
        String fullName = getHostClassName() + "." + methodDecl.fullSignature();

        methodNode = addNode(fullName, Node.Type.Method, methodDecl);

        if (methodDecl.hasBlock()) {
            Block b = methodDecl.getBlock();
            MethodBodyReader mbreader = new MethodBodyReader(b, methodNode, methodDecl, graph);
            mbreader.read();
        }

        addHostClassDependency();
        addReturnTypeDependency();
        addParametersTypeDependency();
        addExceptionsTypesDependencies();

        return getGraph();
    }
    

    private String getHostClassName() {
        return methodDecl.hostType().fullName();
    }

    private void addHostClassDependency() {
        addEdge(getHostTypeName(), methodNode.getFullName(), Edge.Type.Contains);
    }

    private void addReturnTypeDependency() {
        addTypeDependency(methodDecl.type(), Edge.Type.Uses);
    }

    private void addParametersTypeDependency() {
        for (ParameterDeclaration p: methodDecl.getParameterList()) {
            ParametersReader r = new ParametersReader(p, methodNode, getGraph());
            r.read();
        }
    }

    private void addExceptionsTypesDependencies() {
        for (Access a: methodDecl.getExceptionList()) {
            addTypeDependency(a.type(), Edge.Type.Uses);
        }
    }
}
