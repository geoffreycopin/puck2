package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.Map;
import java.util.Set;

public class InterfaceReader extends TypeDeclReader {
    private InterfaceDecl interfaceDecl;

    public InterfaceReader(InterfaceDecl interfaceDecl, Graph graph) {
        super(interfaceDecl, graph);
        this.interfaceDecl = interfaceDecl;
    }


    @Override
    public Graph read() {
        addNode(getFullName(), Node.Type.Interface, interfaceDecl);

        readBodyDeclarations();

        addSuperInterfacesDependency();

        return getGraph();
    }

    @Override
    String getFullName() {
        return interfaceDecl.fullName();
    }

    private void readBodyDeclarations() {
        for (BodyDecl decl : interfaceDecl.getBodyDeclList()) {
            if (decl instanceof MethodDecl) {
                MethodDecl m = (MethodDecl) decl;
                MethodReader methodreader = new MethodReader(m, graph);
                methodreader.read();
            } else if (decl instanceof MemberInterfaceDecl) {
                MemberInterfaceDecl mi = (MemberInterfaceDecl) decl;
                InterfaceReader reader = new InterfaceReader((InterfaceDecl) mi.typeDecl(), graph);
                reader.read();
            } else if (decl instanceof MemberClassDecl) {
                MemberClassDecl mc = (MemberClassDecl) decl;
                ClassReader reader = new ClassReader((ClassDecl)  mc.typeDecl(), graph);
                reader.read();
            }
        }
    }
    
    private void addSuperInterfacesDependency() {
        for (Access sup: interfaceDecl.getSuperInterfaceList()) {
        	addTypeDependency(sup.type(), Edge.Type.IsA);
        }
    }
}
