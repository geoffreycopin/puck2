package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.TypeDecl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompilationUnitReader extends AbstractReader{
    private CompilationUnit compilationUnit;

    public CompilationUnitReader(CompilationUnit unit, UniqueIdGenerator generator) {
        super(generator);
        this.compilationUnit = unit;
    }

    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        readCurrentPackage(nodes);
        readTypeDecalarations(nodes, edges);
    }

    @Override
    String getFullName() {
        return compilationUnit.pathName();
    }

    private void readCurrentPackage(Map<String, Node> nodes) {
        String currentPackage = compilationUnit.getPackageDecl();

        if (currentPackage.isEmpty()) {
            return;
        }

        Node packageNode = new Node(idGenerator.generate(), currentPackage,
                Node.Type.Package, null);
        nodes.put(currentPackage, packageNode);
    }

    private void readTypeDecalarations(Map<String, Node> nodes, List<Edge> edges) {
    	for (TypeDecl t: compilationUnit.getTypeDeclList()) {
            if (t instanceof ClassDecl) {
                readClassDeclaration((ClassDecl) t, nodes, edges);              
            } else if (t instanceof InterfaceDecl) {
                readInterfaceDeclaration((InterfaceDecl) t, nodes, edges);                
            }
        }
    }

    private void readClassDeclaration(ClassDecl decl, Map<String, Node> nodes, List<Edge> edges) {
        ClassReader reader = new ClassReader(decl, idGenerator);
        reader.readInto(nodes, edges);
    }

    private void readInterfaceDeclaration(InterfaceDecl decl, Map<String, Node> nodes, List<Edge> edges) {
        InterfaceReader reader = new InterfaceReader(decl, idGenerator);
        reader.readInto(nodes, edges);
    }
}
