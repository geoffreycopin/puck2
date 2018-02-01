package graph.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import graph.Edge;
import graph.Node;
import graph.readers.ClassReader;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestProgramReader {
    private static ProgramLoader loader;

    @BeforeClass
    public static void init() {
        loader = new ProgramLoader();
        loader.addFile("testfiles/Test.java");
    }

    @Test
    public void TestExtractClasses() {
        Map<String, Node> nodes = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        ProgramReader r = new ProgramReader(loader.getProgram());
        r.readInto(nodes, edges);

        Set<String> extractedClasses = nodes.values().stream()
                .filter(n -> n.getType() == Node.Type.Class)
                .map(Node::getFullName)
                .collect(Collectors.toSet());

        Set<String> expectedClasses = new HashSet<>(Arrays.asList("test.X", "test.Test", "test.SuperTest"));

        assertEquals(extractedClasses, expectedClasses);
    }
}
