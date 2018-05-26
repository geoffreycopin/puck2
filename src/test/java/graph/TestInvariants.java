package graph;

import app.Puck2Runner;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestInvariants {
    private static Graph graph;

    @BeforeClass
    public static void init() throws IOException {
        Puck2Runner runner = new Puck2Runner("src/main/java");
        runner.run();
        graph = runner.getGraph();
    }

    private List<Node> nodesOfType(Node.Type t) {
        return graph.getNodes().values().stream()
                .filter((n) -> n.getType() == t)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void match0From(Node source, Edge.Type t) {
        assertEquals(0, graph.queryNodesFrom(source.getId()).size());
    }

    private void matchOptionnalFrom(Node source, Edge.Type t, Node.Type targetType) {
        
    }
}
