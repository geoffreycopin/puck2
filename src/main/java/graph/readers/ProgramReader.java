package graph.readers;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.UniqueIdGenerator;
import org.extendj.ast.*;

import java.util.Map;
import java.util.Set;

public class ProgramReader extends AbstractReader {
    private Program program;

    public ProgramReader(Program p) {
        super(new Graph(p));
        this.program = p;
    }

    @Override
    public Graph read() {
        readCompilationUnits();
        return getGraph();
    }

    @Override
    String getFullName() {
        return "PROGRAM";
    }

    private void readCompilationUnits() {
        for (CompilationUnit cu: program.getCompilationUnitList()) {
            CompilationUnitReader reader = new CompilationUnitReader(cu, graph);
            reader.read();
        }
    }
}
