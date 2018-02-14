package graph.readers;

import graph.Edge;
import graph.UniqueIdGenerator;
import org.extendj.ast.TypeDecl;

import java.util.List;

public abstract class TypeDeclReader extends AbstractReader{
    private TypeDecl typeDecl;

    public TypeDeclReader(TypeDecl typeDecl, UniqueIdGenerator generator) {
        super(generator);
        this.typeDecl = typeDecl;
    }

    protected void addPackageDependency(List<Edge> edges) {
        String packageName = typeDecl.packageName();

        if (packageName.isEmpty()) {
            return;
        }

        edges.add(new Edge(packageName, typeDecl.fullName(), Edge.Type.Contains));
    }
}
