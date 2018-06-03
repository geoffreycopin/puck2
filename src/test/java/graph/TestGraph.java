package graph;

import graph.nodes.ProgramLoader;
import org.extendj.ast.Program;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGraph {
    Program program;

    @Before
    public void init() {
        ProgramLoader loader = new ProgramLoader();
        loader.addFile("testfiles/Test.java");
        program = loader.getProgram();
    }

    @Test
    public void addNode() {
        Graph graph = new Graph(program);
        graph.addNode("testPackage", Node.Type.Package, null);
        Node expected = new Node(0, "testPackage", Node.Type.Package, null);
        assertEquals(expected, graph.getNode(0));
        assertEquals(expected, graph.getNode("testPackage"));
    }
}
