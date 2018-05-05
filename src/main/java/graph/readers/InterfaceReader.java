package graph.readers;

import graph.Edge;
import graph.Graph;
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

    public InterfaceReader(InterfaceDecl interfaceDecl, Graph graph) {
        super(interfaceDecl, graph);
        this.interfaceDecl = interfaceDecl;
    }


    @Override
    public Graph read() {
        addNode(getFullName(), Node.Type.Interface, interfaceDecl);

        readBodyDeclarations();

        addPackageDependency();
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
            }
        }
    }
    
    private void addSuperInterfacesDependency() {
        for (Access sup: interfaceDecl.getSuperInterfaceList()) {
        	addTypeDependency(sup.type(), Edge.Type.IsA);
        }
    }
}
