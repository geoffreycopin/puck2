package graph.readers;

import graph.Edge;
import graph.UniqueIdGenerator;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.TypeDecl;

import java.util.List;

public abstract class BodyDeclReader extends AbstractReader {
    private BodyDecl bodyDecl;

    public BodyDeclReader(BodyDecl decl, UniqueIdGenerator generator) {
        super(generator);
        this.bodyDecl = decl;
    }

    protected String getHostTypeName() {
        return bodyDecl.hostType().fullName();
    }
}
