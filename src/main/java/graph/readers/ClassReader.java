package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

import org.extendj.ast.*;

import java.util.List;
import java.util.Map;

public class ClassReader extends TypeDeclReader {
    private ClassDecl classDeclaration;

    public ClassReader(ClassDecl classDeclaration, UniqueIdGenerator idGenerator) {
        super(classDeclaration, idGenerator);
        this.classDeclaration = classDeclaration;
    }

    @Override
    String getFullName() {
        return classDeclaration.fullName();
    }

    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        String className = classDeclaration.fullName();

        Node classNode = new Node(idGenerator.generate(), className,
                            Node.Type.Class, classDeclaration.createQualifiedAccess());
        nodes.put(className, classNode);

        readBodyDeclarations(nodes, edges);

        addPackageDependency(edges);
        addSuperClassdependency(edges);
        addInterfacesDependency(edges);
    }

    private void readBodyDeclarations(Map<String, Node> nodes, List<Edge> edges) {
        for (BodyDecl decl : classDeclaration.getBodyDeclList()) {
            if (decl instanceof FieldDecl) {
                FieldReader fieldreader = new FieldReader(idGenerator, (FieldDecl) decl);
                fieldreader.readInto(nodes, edges);
            } else if (decl instanceof MethodDecl) {
                MethodReader methodreader = new MethodReader(idGenerator, (MethodDecl) decl);
                methodreader.readInto(nodes, edges);
            } else if (decl instanceof MemberClassDecl) {
                TypeDecl memberType = ((MemberClassDecl) decl).typeDecl();
                ClassReader reader = new ClassReader((ClassDecl) memberType, idGenerator);
                reader.readInto(nodes, edges);
            } else if (decl instanceof MemberInterfaceDecl) {
                TypeDecl memberType = ((MemberInterfaceDecl) decl).typeDecl();
                InterfaceReader reader = new InterfaceReader((InterfaceDecl) memberType, idGenerator);
                reader.readInto(nodes, edges);
            }
        }
    }

    private void addSuperClassdependency(List<Edge> edges) {
        Access superClass = classDeclaration.getSuperClass();
        if (superClass == null || Util.isPrimitive(superClass.type()) ||
                Util.isBuiltin(superClass.type())) {
            return;
        }
        addTypeDependency(edges, superClass.type(), Edge.Type.IsA);
    }

    private void addInterfacesDependency(List<Edge> edges) {
        for (Access imp: classDeclaration.getImplementsList()) {
            if (! (imp.type() instanceof InterfaceDecl)) {
                continue;
            }
            addTypeDependency(edges, imp.type(), Edge.Type.IsA);
        }
    }
}
