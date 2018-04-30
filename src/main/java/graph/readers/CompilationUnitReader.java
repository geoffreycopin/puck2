package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.TypeDecl;

import java.util.Map;
import java.util.Set;

public class CompilationUnitReader extends AbstractReader{
    private CompilationUnit compilationUnit;

    public CompilationUnitReader(CompilationUnit unit, UniqueIdGenerator generator) {
        super(generator);
        this.compilationUnit = unit;
    }

    public void readInto(Map<Integer, Node> nodes, Set<Edge> edges) {
        readCurrentPackage(nodes);
        readTypeDecalarations(nodes, edges);
    }

    
    @Override
    String getFullName() {
        return compilationUnit.pathName();
    }

    private void readCurrentPackage(Map<Integer, Node> nodes) {
        String currentPackage = compilationUnit.getPackageDecl();

        if (currentPackage.isEmpty()) {
            return;
        }

        Node packageNode = new Node(idGenerator.idFor(currentPackage), currentPackage,
                Node.Type.Package, null);
        nodes.put(idGenerator.idFor(currentPackage), packageNode);
    }

    private void readTypeDecalarations(Map<Integer, Node> nodes, Set<Edge> edges) {
    	for (TypeDecl t: compilationUnit.getTypeDeclList()) {
            if (t instanceof ClassDecl) {
                readClassDeclaration((ClassDecl) t, nodes, edges);              
            } else if (t instanceof InterfaceDecl) {
                readInterfaceDeclaration((InterfaceDecl) t, nodes, edges);                
            }
        }
    }

    private void readClassDeclaration(ClassDecl decl, Map<Integer, Node> nodes, Set<Edge> edges) {
        ClassReader reader = new ClassReader(decl, idGenerator);
        reader.readInto(nodes, edges);
    }

    private void readInterfaceDeclaration(InterfaceDecl decl, Map<Integer, Node> nodes, Set<Edge> edges) {
        InterfaceReader reader = new InterfaceReader(decl, idGenerator);
        reader.readInto(nodes, edges);
    }
}
