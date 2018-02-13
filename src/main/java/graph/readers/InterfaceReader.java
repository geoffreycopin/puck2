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
    	MethodReader methodreader;

    	interfaceNode = new Node(idGenerator.generate(), interfaceDecl.fullName(),
    			Node.Type.Interface, interfaceDecl.createQualifiedAccess());
    	nodes.put(interfaceNode.getFullName(), interfaceNode);


    	interfaceNode = nodes.get(interfaceDecl.fullName());

    	if (interfaceDecl.getNumBodyDecl() > 0) {      	
    		for (BodyDecl decl : interfaceDecl.getBodyDeclList()) {
    			/*Add Methods*/
    			if(decl instanceof MethodDecl){
    				MethodDecl m = (MethodDecl)decl;
    				methodreader= new MethodReader(idGenerator,m,interfaceDecl);
    				methodreader.readInto(nodes, edges);			      
    			}
    		}
    	}
    }

}
