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
        FieldReader fieldreader;
        MethodReader methodreader;

        if (nodes.containsKey(className)) {
            return;
        }

        classNode = new Node(idGenerator.generate(), className, Node.Type.Class, classDeclaration.createQualifiedAccess());
        nodes.put(className, classNode);
        
        if (classDeclaration.getNumBodyDecl() > 0) {
        	for (BodyDecl decl : classDeclaration.getBodyDeclList()) {
        		
        		/* Add Fields */
        		if (decl instanceof FieldDecl) {
        			FieldDecl f = (FieldDecl) decl;
        			fieldreader= new FieldReader(idGenerator,f,classDeclaration);
        			fieldreader.readInto(nodes, edges);
        		}
        		/* Add Methods */
        		if (decl instanceof MethodDecl) {
        			MethodDecl m = (MethodDecl)decl;
        			methodreader= new MethodReader(idGenerator,m,classDeclaration);
        			methodreader.readInto(nodes, edges);
        		}

        	}
        }
             
}


}
