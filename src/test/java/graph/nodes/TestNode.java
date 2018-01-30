package graph.nodes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNode {
    /*
     * Test idiot uniquement destiné à fournir un example.
     */
    @Test
    public void createNode() {
        Node n = new Node(0, "nodeName", null);
        assertEquals(n.getFullName(), "nodeName");
        assertEquals(n.getId(), (Integer) 0);
        assertEquals(n.getExtendjNode(), null);
    }
}
