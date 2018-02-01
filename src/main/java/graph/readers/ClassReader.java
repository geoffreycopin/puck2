package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.TypeDecl;

import java.util.List;
import java.util.Map;

public class ClassReader {
    private ClassDecl classDeclaration;
    private UniqueIdGenerator idGenerator;
    private Node classNode;

    public ClassReader(ClassDecl classDeclaration) {
        this(classDeclaration, new UniqueIdGenerator());
    }

    public ClassReader(ClassDecl classDeclaration, UniqueIdGenerator idGenerator) {
        this.classDeclaration = classDeclaration;
        this.idGenerator = idGenerator;
    }

    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        String className = classDeclaration.fullName();

        if (nodes.containsKey(className)) {
            return;
        }

        classNode = new Node(idGenerator.generate(), className, Node.Type.Class, null);
        nodes.put(className, classNode);

        addPackageDependency(classDeclaration.packageName(), nodes, edges);
        addSupperClassDependency(nodes, edges);
    }

    private void addPackageDependency(String packageName, Map<String, Node> nodes, List<Edge> edges)
    {
        if (! nodes.containsKey(packageName)) {
            Node packageNode = new Node(idGenerator.generate(), packageName, Node.Type.Package, null);
            nodes.put(packageName, packageNode);
        }
        Integer packageId = nodes.get(packageName).getId();
        Edge packageDependency = new Edge(packageId, classNode.getId(), Edge.Type.Contains);
        edges.add(packageDependency);
    }

    private void addSupperClassDependency(Map<String, Node> nodes, List<Edge> edges) {
        if (classDeclaration.getSuperClass() == null) {
            return;
        }

        ClassDecl superClass = (ClassDecl) classDeclaration.superclass();

        if (! nodes.containsKey(superClass.fullName())) {
            new ClassReader(superClass, idGenerator).readInto(nodes, edges);
        }

        Edge e = new Edge(classNode.getId(), nodes.get(superClass.fullName()).getId(), Edge.Type.IsA);
        edges.add(e);
    }
}
