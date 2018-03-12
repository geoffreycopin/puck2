package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.List;
import java.util.Map;

public class ProgramReader extends AbstractReader {
    private Program program;

    public ProgramReader(Program p) {
        this(p, new UniqueIdGenerator());
    }

    public ProgramReader(Program p, UniqueIdGenerator idGenerator) {
        super(idGenerator);
        this.program = p;
    }

    @Override
    public void readInto(Map<String, Node> nodes, List<Edge> edges) {
        readCompilationUnits(nodes, edges);
    }

    @Override
    String getFullName() {
        return "PROGRAM";
    }

    private void readCompilationUnits(Map<String, Node> nodes, List<Edge> edges) {
        for (CompilationUnit cu: program.getCompilationUnitList()) {
            CompilationUnitReader reader = new CompilationUnitReader(cu, idGenerator);
            reader.readInto(nodes, edges);
        }
    }
}
