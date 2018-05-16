package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ASTNode;
import org.extendj.ast.AssertStmt;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.WildcardExtendsType;

import java.util.Map;
import java.util.Set;

public abstract class AbstractReader {
    protected Graph graph;

    public AbstractReader(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

	public abstract Graph read();

	abstract String getFullName();

	protected Node addNode(String name, Node.Type type, ASTNode<ASTNode> extendJNode) {
	    return graph.addNode(name, type, extendJNode);
    }

    protected void addEdge(String source, String target, Edge.Type type, ASTNode<ASTNode> dependencyPoint) {
	    graph.addEdge(source, target, type, dependencyPoint);
    }

    protected void addReference(String source, ASTNode<ASTNode> ref) {
	    graph.addReference(source, ref);
    }

    protected void addEdge(String source, String target, Edge.Type type) {
	    addEdge(source, target, type, null);
    }

	protected void addTypeDependency(TypeDecl type, Edge.Type edgeType) {
	    String typeName = "";
	    if (type.isParameterizedType()) {
			addGenericTypeDependency(type, edgeType);
			typeName = getGenericTypeName(type);
		} else if (type.isWildcard()) {
			addTypeDependency(((WildcardExtendsType) type).extendsType(), edgeType);
		} else if (type.isArrayDecl()) {
			addTypeDependency(type.elementType(), edgeType);
			typeName = type.elementType().fullName();
		} else if (! Util.isPrimitive(type) && ! type.isTypeVariable() && ! Util.isBuiltin(type)) {
	        addEdge(getFullName(), type.fullName(), edgeType);
			typeName = type.fullName();
		}

		if (!typeName.isEmpty() && graph.getNode(type.packageName()) == null) {
	        addContainingPackage(typeName, type.packageName());
        }
	}

	protected void addGenericTypeDependency(TypeDecl type, Edge.Type edgeType) {
	    String genericTypeName = getGenericTypeName(type);
	    addEdge(getFullName(), genericTypeName, edgeType);

		for (TypeDecl typeParameter : TypeDeclReader.getTypeParameters(type)) {
			addTypeDependency(typeParameter, Edge.Type.Uses);
		}
	}

	protected static String getGenericTypeName(TypeDecl t) {
	    return t.packageName() + "." + t.topLevelType().name();
    }

    public void addContainingPackage(String typeName, String packageName) {
	    if (packageName.isEmpty()) {
	        return;
        }
        addNode(packageName, Node.Type.Package, null);
        addEdge(packageName, typeName, Edge.Type.Contains);
    }
}