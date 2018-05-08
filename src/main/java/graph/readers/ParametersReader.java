package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.Node;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;

public class ParametersReader extends AbstractReader {
    private ParameterDeclaration parameterDeclaration;
    private Node methodNode;

    public ParametersReader(ParameterDeclaration param, Node methodNode, Graph graph) {
        super(graph);
        this.methodNode = methodNode;
        this.parameterDeclaration = param;
    }

    @Override
    public Graph read() {
        addNode(getFullName(), Node.Type.Parameter, parameterDeclaration);
        addEdge(methodNode.getFullName(), getFullName(), Edge.Type.Contains);
        addTypeDependency(parameterDeclaration.type(), Edge.Type.Uses);
        return graph;
    }

    @Override
    String getFullName() {
        if (methodNode == null || parameterDeclaration == null) {
            return "";
        }
        return methodNode.getFullName() + '.' + parameterDeclaration.getID();
    }
}
