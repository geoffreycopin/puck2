package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.Access;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ParameterDeclaration;

import java.util.List;
import java.util.Map;

public class InterfaceReader extends AbstractReader {
    private InterfaceDecl interfaceDecl;
    private Node interfaceNode;

    public InterfaceReader(InterfaceDecl interfaceDecl, UniqueIdGenerator idGenerator) {
        super(idGenerator);
        this.interfaceDecl = interfaceDecl;
    }


    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
    	Node MethodNode;
        if (!nodes.containsKey(interfaceDecl.fullName())) {
        	  interfaceNode = new Node(idGenerator.generate(), interfaceDecl.fullName(),
                      Node.Type.Interface, interfaceDecl.createQualifiedAccess());
              nodes.put(interfaceNode.getFullName(), interfaceNode);
        }
        
        interfaceNode = nodes.get(interfaceDecl.fullName());
        if (interfaceDecl.getNumBodyDecl() > 0) {
        	
			for (BodyDecl decl : interfaceDecl.getBodyDeclList()) {
				 /*Add Methods*/
				if(decl instanceof MethodDecl){
					System.out.println("in");
					MethodDecl m = (MethodDecl)decl;
					MethodNode = new Node(idGenerator.generate(),  m.fullSignature(), Node.Type.Method, m.getTypeAccess());
			        nodes.put(m.fullSignature(), MethodNode );
			        addMethodDependency(MethodNode,nodes, edges,m);
			      
				}
			}
        }
        addPackageDependency(nodes, edges);
        addSuperInterfacesDependency(nodes, edges);
    }

    private void addPackageDependency(Map<String, Node> nodes, List<Edge> edges) {
        String packageName = interfaceDecl.packageName();
        if (! nodes.containsKey(packageName)) {
            Node packageNode = new Node(idGenerator.generate(), packageName, Node.Type.Package, null);
            nodes.put(packageName, packageNode);
        }

        Integer packageId = nodes.get(packageName).getId();
        Edge dependency = new Edge(packageId, interfaceNode.getId(), Edge.Type.Contains);
        edges.add(dependency);
    }

    private void addSuperInterfacesDependency(Map<String, Node> nodes, List<Edge> edges) {
        for (Access sup: interfaceDecl.getSuperInterfaceList()) {
            InterfaceDecl superInterface = (InterfaceDecl) sup.type();
            
            if (! nodes.containsKey(superInterface.fullName())) {
                Node InterfaceNode = new Node(idGenerator.generate(), superInterface.fullName(), Node.Type.Interface, null);
                nodes.put(superInterface.fullName(), InterfaceNode);
            }
            Integer interfaceId = nodes.get(superInterface.fullName()).getId();
            Edge dependency = new Edge(interfaceNode.getId(), interfaceId, Edge.Type.IsA);
            edges.add(dependency);
            
			InterfaceReader r = new InterfaceReader(superInterface, idGenerator);
			r.readInto(nodes, edges);
            
            
        }
    }
    private void addMethodDependency(Node method,Map<String, Node> nodes, List<Edge> edges,MethodDecl m) {
		Edge e;
		Node ParamNode;
		e = new Edge(interfaceNode.getId(), method.getId(), Edge.Type.Uses);
		edges.add(e);
		
		if(m.getNumParameter() >0){
			for (ParameterDeclaration p : m.getParameterList()) {
				ParamNode = new Node(idGenerator.generate(),p.name(), Node.Type.Attribute, p.getTypeAccess());
		        nodes.put(p.name(), ParamNode );
			}
		}
    }
}
