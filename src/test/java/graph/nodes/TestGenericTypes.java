package graph.nodes;

import graph.Edge;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestGenericTypes extends TestBase {

    @BeforeClass
    public static void init() {
        setTestFile("testfiles/Statement.java");
        load();
    }

    
    @Test
    public void typeParamsAreRead() {
        Set<TestEdge> edges = new HashSet<>(Arrays.asList(
                new TestEdge("statement.Statement.f(java.util.List<statement.Statement.X>)", "statement.Statement.X")
        ));
        edgesContains(edges, Edge.Type.Uses);
    }
}
