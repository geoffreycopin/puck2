package graph.nodes;

import graph.Edge;
import graph.Node;
import graph.readers.ProgramReader;
import org.extendj.ast.Option;
import org.extendj.ast.Program;
import org.junit.BeforeClass;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestBase {
    protected static ProgramLoader loader;
    protected static String testFile;

    public static void load() {
        loader = new ProgramLoader();
        loader.addFile(testFile);
    }

    protected static void setTestFile(String path) {
        testFile = path;
    }

    protected void nodesEquals(Set<String> expected, Node.Type type) {
        Set<String> actual = computeNodes(type);
        assertEquals(actual, expected);
    }

    private Set<String> computeNodes(Node.Type type) {
        Map<String, Node> nodes = new HashMap<>();
        Set<Edge> edges = new HashSet<>();
        ProgramReader r = new ProgramReader(loader.getProgram());
        r.readInto(nodes, edges);

        return nodes.values().stream()
                .filter((n) -> type == null || n.getType() == type)
                .map(Node::getFullName)
                .collect(Collectors.toSet());
    }

    protected void edgesEquals(Set<TestEdge> expected, Edge.Type type) {
        Set<TestEdge> actual = computeEdges(type);
        assertEquals(expected, actual);
    }

    protected void edgesContains(Set<TestEdge> expected, Edge.Type type) {
        Set<TestEdge> actual = computeEdges(type);
        assertTrue(actual.containsAll(expected));
    }

    private Set<TestEdge> computeEdges(Edge.Type type) {
        Map<String, Node> nodes_map = new HashMap<>();
        Set<Edge> edges = new HashSet<>();
        ProgramReader r = new ProgramReader(loader.getProgram());
        r.readInto(nodes_map, edges);

        return edges.stream()
                .filter(n -> type == null || n.getType() == type)
                .map(n -> new TestBase.TestEdge(n.getSourceName(), n.getTargetName()))
                .collect(Collectors.toSet());
    }

    protected class TestEdge{
        public String source;
        public String target;

        public TestEdge(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public String toString() {
            return String.format("<source=%s target=%s>", source, target);
        }

        public boolean equals(Object o) {
            if (! (o instanceof TestEdge)) {
                return false;
            }
            TestEdge other = (TestEdge) o;

            return other.source.equals(source) && other.target.equals(target);
        }

        public int hashCode() {
            return source.hashCode() + target.hashCode();
        }
    }
}
