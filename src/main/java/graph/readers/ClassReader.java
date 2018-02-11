package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

import org.extendj.ast.Access;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;
import org.extendj.ast.TypeDecl;

import java.util.List;
import java.util.Map;

public class ClassReader extends TypeDeclReader {
	private ClassDecl classDeclaration;
	private Node classNode;

	public ClassReader(ClassDecl classDeclaration, UniqueIdGenerator idGenerator) {
		super(classDeclaration, idGenerator);
		this.classDeclaration = classDeclaration;
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
        addInterfaceDependency(nodes,edges);
        readBodyDecls(nodes, edges);
    }

	private void addPackageDependency(String packageName, Map<String, Node> nodes, List<Edge> edges) {
		if (!nodes.containsKey(packageName)) {
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
        if (!nodes.containsKey(superClass.fullName())) {
			new ClassReader(superClass, idGenerator).readInto(nodes, edges);
		}

		Edge e = new Edge(classNode.getId(), nodes.get(superClass.fullName()).getId(), Edge.Type.IsA);
		edges.add(e);
	}

	private void addInterfaceDependency(Map<String, Node> nodes, List<Edge> edges) {
		for (Access imp : classDeclaration.getImplementsList()) {
			InterfaceDecl implement = (InterfaceDecl) imp.type();
			if (!nodes.containsKey(implement.fullName())) {
				new InterfaceReader(implement, idGenerator).readInto(nodes, edges);
			}

			Integer interfaceId = nodes.get(implement.fullName()).getId();
			Edge dependency = new Edge(classNode.getId(), interfaceId, Edge.Type.IsA);
			edges.add(dependency);
		}
	}
}
