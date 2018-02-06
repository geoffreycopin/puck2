package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.Access;
import org.extendj.ast.InterfaceDecl;

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
        if (nodes.containsKey(interfaceDecl.fullName())) {
            return;
        }

        interfaceNode = new Node(idGenerator.generate(), interfaceDecl.fullName(),
                Node.Type.Interface, interfaceDecl.createQualifiedAccess());
        nodes.put(interfaceNode.getFullName(), interfaceNode);

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
            InterfaceReader r = new InterfaceReader(superInterface, idGenerator);
            r.readInto(nodes, edges);
        }
    }
}
