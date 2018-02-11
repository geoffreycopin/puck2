package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDeclarator;
import org.extendj.ast.TypeDecl;

import java.util.List;
import java.util.Map;

public class FieldDeclaratorReader extends AbstractReader {
    private FieldDeclarator fieldDeclarator;
    private Node fieldNode;

    public FieldDeclaratorReader(FieldDeclarator fieldDeclarator, UniqueIdGenerator generator) {
        super(generator);
        this.fieldDeclarator = fieldDeclarator;
    }

    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        if (nodes.containsKey(getUniqueName())) {
            return;
        }

        fieldNode = new Node(idGenerator.generate(), getUniqueName(),
                             Node.Type.Attribute, fieldDeclarator.createBoundAccess());
        nodes.put(fieldNode.getFullName(), fieldNode);

        addHostClassDependency(nodes, edges);
        addFieldTypeDependecy(nodes, edges);
    }

    private void addHostClassDependency(Map<String, Node> nodes, List<Edge> edges) {
        Node superClass = nodes.get(getHostClass().fullName());
        Edge dependencyEdge = new Edge(superClass.getId(), fieldNode.getId(), Edge.Type.Contains);
        edges.add(dependencyEdge);
    }

    private void addFieldTypeDependecy(Map<String, Node> nodes, List<Edge> edges) {
        TypeDecl fieldType =  fieldDeclarator.type();
        if (Util.isPrimitiveType(fieldType)) {
            return;
        }

        if (! nodes.containsKey(fieldType.fullName())) {
            new TypeDeclReader(fieldType, idGenerator).readInto(nodes, edges);
        }

        Node typeNode = nodes.get(fieldType.fullName());
        Edge dependencyEdge = new Edge(fieldNode.getId(), typeNode.getId(), Edge.Type.IsA);
        edges.add(dependencyEdge);
    }

    private String getUniqueName() {
        return String.format("%s.%s", getHostClass().fullName(), fieldDeclarator.name());
    }

    private ClassDecl getHostClass() {
        return (ClassDecl) fieldDeclarator.hostType();
    }
}
