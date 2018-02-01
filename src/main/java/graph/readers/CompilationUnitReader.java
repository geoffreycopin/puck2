package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.TypeDecl;

import java.util.List;
import java.util.Map;

public class CompilationUnitReader {
    private CompilationUnit compilationUnit;
    private UniqueIdGenerator idGenerator;

    public CompilationUnitReader(CompilationUnit unit) {
        this(unit, new UniqueIdGenerator());
    }

    public CompilationUnitReader(CompilationUnit unit, UniqueIdGenerator generator) {
        this.compilationUnit = unit;
        this.idGenerator = generator;
    }

    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        readCurrentPackage(nodes);
        readTypeDecalarations(nodes, edges);
    }

    private void readCurrentPackage(Map<String, Node> nodes) {
        String currentPackage = compilationUnit.getPackageDecl();

        if (nodes.containsKey(currentPackage) || currentPackage.isEmpty()) {
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
            }
        }
    }

    private void readClassDeclaration(ClassDecl decl, Map<String, Node> nodes, List<Edge> edges) {
        ClassReader reader = new ClassReader(decl, idGenerator);
        reader.readInto(nodes, edges);
    }
}
