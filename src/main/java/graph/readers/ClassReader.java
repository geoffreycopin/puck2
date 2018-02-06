package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

import org.extendj.ast.BodyDecl;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.MethodDecl;
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
        
        /*Add Fields*/
        if (classDeclaration.getNumBodyDecl() > 0) {
			for (BodyDecl decl : classDeclaration.getBodyDeclList()) {
				if (decl instanceof FieldDecl) {
					for (FieldDeclarator v : ((FieldDecl) decl).getDeclaratorList()) {
						FieldNode = new Node(idGenerator.generate(),  v.name(), Node.Type.Attribute, v.getTypeAccess());
				        nodes.put(v.name(), FieldNode );
				        System.out.println(v.type());
				        addFieldDependency(FieldNode,nodes, edges);
					}
				}
				if(decl instanceof MethodDecl){
					MethodNode = new Node(idGenerator.generate(),  ((MethodDecl)decl).name(), Node.Type.Method, ((MethodDecl)decl).getTypeAccess());
			        nodes.put(((MethodDecl)decl).name(), MethodNode );
			      
				}
			}
        }

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
    
    private void addFieldDependency(Node field,Map<String, Node> nodes, List<Edge> edges) {
		Edge e;
		/* Class uses field */
		e = new Edge(classNode.getId(), field.getId(), Edge.Type.Uses);
		edges.add(e);
		/* If field of class type */
		TypeDecl t = field.getExtendjNode().type();
		if (nodes.containsKey(t.fullName())) {
			e=new Edge(field.getId(), nodes.get(t.fullName()).getId(), Edge.Type.IsA);
			edges.add(e);
			/* or Class uses class ??*/
			/*e=new Edge(classdeclaration.getId(), nodes.get(t.fullName()).getId(), Edge.Type.IsA);
			edges.add(e);*/
		}
    	
    }
}
