package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.UniqueIdGenerator;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.TypeDecl;

import java.util.List;

public abstract class BodyDeclReader extends AbstractReader {
    private BodyDecl bodyDecl;

    public BodyDeclReader(BodyDecl decl, Graph graph) {
        super(graph);
        this.bodyDecl = decl;
    }

    protected String getHostTypeName() {
        return bodyDecl.hostType().fullName();
    }
}
