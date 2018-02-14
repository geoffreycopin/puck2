package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.Access;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;

import java.util.List;
import java.util.Map;

public class InterfaceReader extends TypeDeclReader {
    private InterfaceDecl interfaceDecl;

    public InterfaceReader(InterfaceDecl interfaceDecl, UniqueIdGenerator idGenerator) {
        super(interfaceDecl, idGenerator);
        this.interfaceDecl = interfaceDecl;
    }


    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        Node interfaceNode = new Node(idGenerator.generate(), interfaceDecl.fullName(),
                Node.Type.Interface, interfaceDecl.createQualifiedAccess());
        nodes.put(interfaceNode.getFullName(), interfaceNode);

        readBodyDeclarations(nodes, edges);

        addPackageDependency(edges);
    }

    private void readBodyDeclarations(Map<String, Node> nodes, List<Edge> edges) {
        for (BodyDecl decl : interfaceDecl.getBodyDeclList()) {
            if (decl instanceof MethodDecl) {
                MethodDecl m = (MethodDecl) decl;
                MethodReader methodreader = new MethodReader(idGenerator, m);
                methodreader.readInto(nodes, edges);
            }
        }
    }
}
