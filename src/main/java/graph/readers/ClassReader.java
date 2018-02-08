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

public class ClassReader extends AbstractReader {
	private ClassDecl classDeclaration;
	private Node classNode;

	public ClassReader(ClassDecl classDeclaration, UniqueIdGenerator idGenerator) {
		super(idGenerator);
		this.classDeclaration = classDeclaration;
		this.idGenerator = idGenerator;
	}

	public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        String className = classDeclaration.fullName();
        Node FieldNode;
        Node MethodNode;
        if (nodes.containsKey(className)) {
            return;
        }

        classNode = new Node(idGenerator.generate(), className, Node.Type.Class, null);
        nodes.put(className, classNode);
        
       
        if (classDeclaration.getNumBodyDecl() > 0) {
			for (BodyDecl decl : classDeclaration.getBodyDeclList()) {
				 /*Add Fields*/
				if (decl instanceof FieldDecl) {
					for (FieldDeclarator v : ((FieldDecl) decl).getDeclaratorList()) {
						FieldNode = new Node(idGenerator.generate(),  v.name(), Node.Type.Attribute, v.getTypeAccess());
				        nodes.put(v.name(), FieldNode );
				        addFieldDependency(FieldNode,nodes, edges);
					}
				}
				 /*Add Methods*/
				if(decl instanceof MethodDecl){
					MethodDecl m = (MethodDecl)decl;
					MethodNode = new Node(idGenerator.generate(),  m.fullSignature(), Node.Type.Method, m.getTypeAccess());
			        nodes.put(m.fullSignature(), MethodNode );
			        addMethodDependency(MethodNode,nodes, edges,m);
			      
				}
			}
        }

        addPackageDependency(classDeclaration.packageName(), nodes, edges);
        addSupperClassDependency(nodes, edges);
        addInterfaceDependency(nodes,edges);
       
        
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

	private void addFieldDependency(Node field, Map<String, Node> nodes, List<Edge> edges) {
		Edge e;
		/* Class uses field */
		e = new Edge(classNode.getId(), field.getId(), Edge.Type.Uses);
		edges.add(e);
		/* If field of class type */
		// TypeDecl t = field.getExtendjNode().type();
		// if (nodes.containsKey(t.fullName())) {
		// e=new Edge(field.getId(), nodes.get(t.fullName()).getId(),
		// Edge.Type.IsA);
		// edges.add(e);
		// /* or Class uses class ??*/
		// /*e=new Edge(classdeclaration.getId(),
		// nodes.get(t.fullName()).getId(), Edge.Type.IsA);
		// edges.add(e);*/
		// }

	}

	private void addMethodDependency(Node method, Map<String, Node> nodes, List<Edge> edges, MethodDecl m) {
		Edge e;
		Node ParamNode;
		e = new Edge(classNode.getId(), method.getId(), Edge.Type.Uses);
		edges.add(e);

		if (m.getNumParameter() > 0) {
			for (ParameterDeclaration p : m.getParameterList()) {
				ParamNode = new Node(idGenerator.generate(), p.name(), Node.Type.Attribute, p.getTypeAccess());
				nodes.put(p.name(), ParamNode);
			}
		}
	}

	public void addInterfaceDependency(Map<String, Node> nodes, List<Edge> edges) {
		for (Access imp : classDeclaration.getImplementsList()) {
			InterfaceDecl implement = (InterfaceDecl) imp.type();
			if (!nodes.containsKey(implement.fullName())) {
				Node InterfaceNode = new Node(idGenerator.generate(), implement.fullName(), Node.Type.Interface,
						implement.createQualifiedAccess());
				nodes.put(implement.fullName(), InterfaceNode);
			}

			Integer interfaceId = nodes.get(implement.fullName()).getId();
			Edge dependency = new Edge(classNode.getId(), interfaceId, Edge.Type.Uses);
			edges.add(dependency);
		}
	}
}
