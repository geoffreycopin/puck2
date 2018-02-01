package graph.nodes;

import graph.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNode {
    /*
     * Test idiot uniquement destiné à fournir un example.
     */
    @Test
    public void createNode() {
        Node n = new Node(0, "nodeName", Node.Type.Class, null);
        assertEquals(n.getFullName(), "nodeName");
        assertEquals(n.getId(), (Integer) 0);
        assertEquals(n.getExtendjNode(), null);
        assertEquals(n.getType(), Node.Type.Class);
    }
}
