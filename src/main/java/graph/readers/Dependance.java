package graph.readers;

import java.util.List;
import java.util.Map;

import org.extendj.ast.Access;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.InterfaceDecl;
import org.extendj.ast.TypeDecl;

import graph.Edge;
import graph.Node;

public class Dependance {
	private Map<String, Node> nodes;
	private List<Edge> edges;
	private List<TypeDecl> typedec;


	public Dependance(Map<String, Node> nodes, List<Edge> edges, List<TypeDecl> typedec) {
		super();
		this.nodes = nodes;
		this.edges = edges;
		this.typedec = typedec;
	}

	public void addPackageDependancy(){
		String PackageName;
		for ( TypeDecl t : typedec){
			PackageName=t.packageName();
			if(nodes.get(PackageName) != null){
			Integer packageId = nodes.get(PackageName).getId();
			Edge packageDependency = new Edge(packageId, nodes.get(t.fullName()).getId(), Edge.Type.Contains);
			edges.add(packageDependency);
			}
		}
	}

	public void addClassDependancy(){
		for ( TypeDecl t : typedec){
			if(t instanceof ClassDecl){
				ClassDecl c = (ClassDecl)t;
				if (c.getSuperClass() != null){
					TypeDecl superClass = c.superclass();
					Edge e = new Edge(nodes.get(c.fullName()).getId(), nodes.get(superClass.fullName()).getId(), Edge.Type.IsA);
					edges.add(e);
				}
				if(c.getNumImplements() >0){
					for (Access imp : c.getImplementsList()) {
						InterfaceDecl implement = (InterfaceDecl) imp.type();
						Integer interfaceId = nodes.get(implement.fullName()).getId();
						Edge dependency = new Edge(nodes.get(c.fullName()).getId(), interfaceId, Edge.Type.Uses);
						edges.add(dependency);
					}
				}

			}
		}

	}
//AJOUTER NOEUD CORPS  corps contenu dans method
	public void addSuperInterfaceDependancy(){
		for ( TypeDecl t : typedec){
			if(t instanceof InterfaceDecl){
				InterfaceDecl i = (InterfaceDecl)t;
				if (i.getNumSuperInterface() >0){
					for (Access sup: i.getSuperInterfaceList()) {
						InterfaceDecl superInterface = (InterfaceDecl) sup.type();
						Edge e = new Edge(nodes.get(i.fullName()).getId(), nodes.get(superInterface.fullName()).getId(), Edge.Type.IsA);
						edges.add(e);
					}
				}
			}

		}
	}


}
