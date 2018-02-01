package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.List;
import java.util.Map;

public class ProgramReader {
    private UniqueIdGenerator idGenerator;
    private Program program;

    public ProgramReader(Program p) {
        this(p, new UniqueIdGenerator());
    }

    public ProgramReader(Program p, UniqueIdGenerator idGenerator) {
        this.program = p;
        this.idGenerator = idGenerator;
    }

    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        readCompilationUnits(nodes, edges);
    }

    private void readCompilationUnits(Map<String, Node> nodes, List<Edge> edges) {
        for (CompilationUnit cu: program.getCompilationUnitList()) {
            CompilationUnitReader reader = new CompilationUnitReader(cu, idGenerator);
            reader.readInto(nodes, edges);
        }
    }
}
