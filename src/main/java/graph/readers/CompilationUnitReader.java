package graph.readers;

import graph.Edge;
import graph.Graph;
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

    public CompilationUnitReader(CompilationUnit unit, Graph graph) {
        super(graph);
        this.compilationUnit = unit;
    }

    public Graph read() {
        readCurrentPackage();
        readTypeDecalarations();
        return getGraph();
    }

    @Override
    String getFullName() {
        return compilationUnit.pathName();
    }

    private void readCurrentPackage() {
        String currentPackage = compilationUnit.getPackageDecl();
        if (currentPackage.isEmpty()) {
            return;
        }
        addNode(currentPackage, Node.Type.Package, null);
    }

    private void readTypeDecalarations() {
    	for (TypeDecl t: compilationUnit.getTypeDeclList()) {
            if (t instanceof ClassDecl) {
                readClassDeclaration((ClassDecl) t);
            } else if (t instanceof InterfaceDecl) {
                readInterfaceDeclaration((InterfaceDecl) t);
            }
        }
    }

    private void readClassDeclaration(ClassDecl decl) {
        ClassReader reader = new ClassReader(decl, graph);
        reader.read();
    }

    private void readInterfaceDeclaration(InterfaceDecl decl) {
        InterfaceReader reader = new InterfaceReader(decl, graph);
        reader.read();
    }
}
