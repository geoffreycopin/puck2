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
    private ArrayList<TypeDecl> list = new ArrayList<>();
    public CompilationUnitReader(CompilationUnit unit, UniqueIdGenerator generator) {
        super(generator);
        this.compilationUnit = unit;
        this.idGenerator = generator;
    }

    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        readCurrentPackage(nodes);
        readTypeDecalarations(nodes, edges);
        
    }

    public void Dependance(Map<String, Node> nodes, List<Edge> edges){
    	Dependance d ;
    	 d= new Dependance(nodes,edges,list);
         d.addPackageDependancy();
         d.addClassDependancy();
         d.addSuperInterfaceDependancy();
         d.addmethodSignatureDependancy();
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
      
        	list.add(t);
            if (t instanceof ClassDecl) {
                readClassDeclaration((ClassDecl) t, nodes, edges);              
            } else if (t instanceof InterfaceDecl) {
                readInterfaceDeclaration((InterfaceDecl) t, nodes, edges);                
            }
        }
       this.Dependance(nodes, edges);
    }

    
    
    private void readClassDeclaration(ClassDecl decl, Map<String, Node> nodes, List<Edge> edges) {
        ClassReader reader = new ClassReader(decl, idGenerator);
        reader.readInto(nodes, edges);
       // reader.addPackageDependency(nodes, edges);
    }

    private void readInterfaceDeclaration(InterfaceDecl decl, Map<String, Node> nodes, List<Edge> edges) {
        InterfaceReader reader = new InterfaceReader(decl, idGenerator);
        reader.readInto(nodes, edges);
    }
}
