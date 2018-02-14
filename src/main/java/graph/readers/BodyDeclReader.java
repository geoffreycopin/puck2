package graph.readers;

import graph.UniqueIdGenerator;
import org.extendj.ast.BodyDecl;

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
