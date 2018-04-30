package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.Access;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;

import java.util.Map;
import java.util.Set;

public class InterfaceReader extends TypeDeclReader {
    private InterfaceDecl interfaceDecl;

    public InterfaceReader(InterfaceDecl interfaceDecl, UniqueIdGenerator idGenerator) {
        super(interfaceDecl, idGenerator);
        this.interfaceDecl = interfaceDecl;
    }


    @Override
    public void readInto(Map<Integer, Node> nodes, Set<Edge> edges) {
        Node interfaceNode = new Node(idGenerator.idFor(getFullName()), getFullName(),
                Node.Type.Interface, interfaceDecl);
        nodes.put(idGenerator.idFor(interfaceNode.getFullName()), interfaceNode);

        readBodyDeclarations(nodes, edges);

        addPackageDependency(edges);
        addSuperInterfacesDependency(nodes, edges);
    }

    @Override
    String getFullName() {
        return interfaceDecl.fullName();
    }

    private void readBodyDeclarations(Map<Integer, Node> nodes, Set<Edge> edges) {
        for (BodyDecl decl : interfaceDecl.getBodyDeclList()) {
            if (decl instanceof MethodDecl) {
                MethodDecl m = (MethodDecl) decl;
                MethodReader methodreader = new MethodReader(idGenerator, m);
                methodreader.readInto(nodes, edges);
            }
        }
    }
    
    private void addSuperInterfacesDependency(Map<Integer, Node> nodes, Set<Edge> edges) {
        for (Access sup: interfaceDecl.getSuperInterfaceList()) {
        	addTypeDependency(edges, sup.type(), Edge.Type.IsA,nodes);
        }
    }
}
