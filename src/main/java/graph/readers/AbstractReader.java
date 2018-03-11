package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ArrayDecl;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.WildcardExtendsType;

import java.util.List;
import java.util.Map;

public abstract class AbstractReader {
	protected UniqueIdGenerator idGenerator;

	public AbstractReader(UniqueIdGenerator generator) {
		this.idGenerator = generator;
	}

	public abstract void readInto(Map<String, Node> nodes, List<Edge> edges);

	abstract String getFullName();

	protected void addTypeDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType,Map<String, Node> nodes) {

		
		if (type.isParameterizedType()) {     	
			addGenericTypeDependency(edges, type, edgeType,nodes);
		} else if (type.isWildcard()) {
			addTypeDependency(edges, ((WildcardExtendsType) type).extendsType(), Edge.Type.Uses,nodes);
		} else if (type.isArrayDecl()) {
			
			addTypeDependency(edges, type.elementType(), edgeType,nodes);
		}else if ((Util.isPrimitive(type) || Util.isBuiltin(type))) {
			addGenericNodes(nodes, type.elementType(), edgeType,edges);
			addDependency(edges,type,edgeType);
		} else if (! type.isTypeVariable()) {
			edges.add(new Edge(getFullName(), type.fullName(), edgeType));
		}
		
	}

	protected void addGenericTypeDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType,Map<String, Node> nodes) {
		if (!Util.isBuiltin(type)) {

			String genericTypeName = TypeDeclReader.getGenericTypeName(type);
			edges.add(new Edge(getFullName(), genericTypeName, edgeType));
		}

		for (TypeDecl typeParameter : TypeDeclReader.getTypeParameters(type)) {
			addTypeDependency(edges, typeParameter, Edge.Type.Uses,nodes);
		}
	}

	protected void addGenericNodes(Map<String, Node> nodes, TypeDecl type, Edge.Type edgeType,List<Edge> edges) {
		
		if(!nodes.containsKey(type.fullName())) {
			Node n =new Node(idGenerator.generate(),type.fullName(),Node.Type.Class,type.createQualifiedAccess());
			nodes.put(type.fullName(),n);
			if(!nodes.containsKey(type.packageName())) {
				n = new Node(idGenerator.generate(),type.packageName(),Node.Type.Package,type.createQualifiedAccess());
				nodes.put(type.packageName(), n);
				
			}		
		}
		
		Edge e = new Edge(type.packageName(),type.fullName(),Edge.Type.Contains);
		if(!e.Contains(edges))edges.add(e);
		
	}
	protected void addDependency(List<Edge> edges, TypeDecl type, Edge.Type edgeType) {
		edges.add(new Edge(getFullName(), type.fullName(), edgeType));
	
	}
	
}