package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FieldDeclarator;

import java.util.List;
import java.util.Map;

public class FieldReader extends AbstractReader {
    private FieldDecl fieldDecl;

    public FieldReader(FieldDecl fieldDecl, UniqueIdGenerator generator) {
        super(generator);
        this.fieldDecl = fieldDecl;
    }

    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        for (FieldDeclarator f: fieldDecl.getDeclaratorList()) {
            new FieldDeclaratorReader(f, idGenerator).readInto(nodes, edges);
        }
    }
}
