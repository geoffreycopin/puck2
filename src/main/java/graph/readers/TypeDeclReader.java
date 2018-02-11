package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.List;
import java.util.Map;

public class TypeDeclReader extends AbstractReader {
    private TypeDecl typeDecl;

    public TypeDeclReader(TypeDecl typeDecl, UniqueIdGenerator generator) {
        super(generator);
        this.typeDecl = typeDecl;
    }

    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        if (typeDecl instanceof ClassDecl) {
            new ClassReader((ClassDecl) typeDecl, idGenerator).readInto(nodes, edges);
        } else if (typeDecl instanceof InterfaceDecl) {
            new InterfaceReader((InterfaceDecl) typeDecl, idGenerator).readInto(nodes, edges);
        }
    }

    protected void readBodyDecls(Map<String, Node> nodes, List<Edge> edges) {
        for (BodyDecl b: typeDecl.getBodyDeclList()) {
            if (b instanceof FieldDecl) {
                new FieldReader((FieldDecl) b, idGenerator).readInto(nodes, edges);
            } else if (b instanceof MethodDecl) {
                new MethodReader((MethodDecl) b, idGenerator).readInto(nodes, edges);
            }
        }
    }
}
