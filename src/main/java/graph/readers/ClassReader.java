package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.Node;

import org.extendj.ast.*;

public class ClassReader extends TypeDeclReader {
    private ClassDecl classDeclaration;

    public ClassReader(ClassDecl classDeclaration, Graph graph) {
        super(classDeclaration, graph);
        this.classDeclaration = classDeclaration;
    }

    @Override
    String getFullName() {
        return classDeclaration.fullName();
    }

    @Override
    public Graph read() {
        String className = classDeclaration.fullName();

        addNode(className, Node.Type.Class, classDeclaration);

        readBodyDeclarations();
        addPackageDependency();
        addSuperClassdependency();
        addInterfacesDependency();

        return getGraph();
    }

    private void readBodyDeclarations() {
    	
        for (BodyDecl decl : classDeclaration.getBodyDeclList()) {
            if (decl instanceof FieldDecl) {
                FieldReader fieldreader = new FieldReader((FieldDecl) decl, graph);
                fieldreader.read();
            } else if (decl instanceof MethodDecl) {
                MethodReader methodreader = new MethodReader((MethodDecl) decl, graph);
                methodreader.read();
            } else if (decl instanceof ConstructorDecl) {
            	ConstructorReader construcreader = new ConstructorReader((ConstructorDecl) decl, graph);
            	construcreader.read();
            } else if (decl instanceof MemberClassDecl) {
                TypeDecl memberType = ((MemberClassDecl) decl).typeDecl();
                ClassReader reader = new ClassReader((ClassDecl) memberType, graph);
                reader.read();
                addTypeDependency(memberType, Edge.Type.Contains);
            } else if (decl instanceof MemberInterfaceDecl) {
                TypeDecl memberType = ((MemberInterfaceDecl) decl).typeDecl();
                InterfaceReader reader = new InterfaceReader((InterfaceDecl) memberType, graph);
                reader.read();
                addTypeDependency(memberType, Edge.Type.Contains);
            } else if (decl instanceof StaticInitializer) {
                System.out.println(((StaticInitializer) decl));
            }
        }
    }

    private void addSuperClassdependency() {
        Access superClass = classDeclaration.getSuperClass();
        if (superClass == null || Util.isPrimitive(superClass.type())) {
            return;
        }
        addTypeDependency(superClass.type(), Edge.Type.IsA);
    }

    private void addInterfacesDependency() {
        for (Access imp: classDeclaration.getImplementsList()) {
            if (! (imp.type() instanceof InterfaceDecl)) {
                continue;
            }
            addTypeDependency(imp.type(), Edge.Type.IsA);
        }
    }
}
