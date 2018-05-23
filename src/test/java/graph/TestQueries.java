package graph;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestQueries {
    @Test
    public void subClasses() {
        Graph g = new Graph(null);
        g.addNode("BaseClass", Node.Type.Class, null);
        g.addNode("OtherClass", Node.Type.Class, null);
        g.addNode("SubClass1", Node.Type.Class, null);
        g.addNode("SubClass2", Node.Type.Class, null);
        g.addEdge("SubClass1", "BaseClass", Edge.Type.IsA);
        g.addEdge("SubClass2", "BaseClass", Edge.Type.IsA);

        Set<String> subclasses = idsToNodesNames(Queries.subClasses(g.getNode("BaseClass").getId(), g), g);

        assertEquals(2, subclasses.size());
        assertTrue(subclasses.contains("SubClass1"));
        assertTrue(subclasses.contains("SubClass2"));
    }

    @Test
    public void overidenMethods() {
        Graph g = new Graph(null);
        g.addNode("Class", Node.Type.Class, null);
        g.addNode("Class.method()", Node.Type.Method, null);
        g.addNode("Class.method(int)", Node.Type.Method, null);
        g.addNode("Class.method(double, double)", Node.Type.Method, null);
        g.addNode("Class.method2(int)", Node.Type.Method, null);
        g.addEdge("Class", "Class.method()", Edge.Type.Contains);
        g.addEdge("Class", "Class.method2(int)", Edge.Type.Contains);
        g.addEdge("Class", "Class.method(int)", Edge.Type.Contains);
        g.addEdge("Class", "Class.method(double, double)", Edge.Type.Contains);

        Set<String> overM = idsToNodesNames(Queries.overridenMethods(g.getNode("Class.method()").getId(), g), g);

        assertEquals(3, overM.size());
        assertTrue(overM.contains("Class.method()"));
        assertTrue(overM.contains("Class.method(int)"));
        assertTrue(overM.contains("Class.method(double, double)"));
    }

    @Test
    public void methodsInInterfaceImp() {
        Graph g = new Graph(null);
        g.addNode("I", Node.Type.Interface, null);
        g.addNode("I.m()", Node.Type.Method, null);
        g.addNode("I.n(int)", Node.Type.Method, null);
        g.addEdge("I", "I.m()", Edge.Type.Contains);
        g.addEdge("I", "I.n(int)", Edge.Type.Contains);

        g.addNode("C", Node.Type.Class, null);
        g.addNode("C.m()", Node.Type.Method, null);
        g.addNode("C.n(int)", Node.Type.Method, null);
        g.addNode("C.p()", Node.Type.Method, null);
        g.addEdge("C", "I", Edge.Type.IsA);
        g.addEdge("C", "C.m()", Edge.Type.Contains);
        g.addEdge("C", "C.n(int)", Edge.Type.Contains);
        g.addEdge("C", "C.p()", Edge.Type.Contains);

        Set<String> iImpls = idsToNodesNames(Queries.methodsInInterfaceImp(g.getNode("I").getId(), g), g);

        assertEquals(2, iImpls.size());
        assertTrue(iImpls.contains("C.m()"));
        assertTrue(iImpls.contains("C.n(int)"));
    }

    @Test
    public void methodsInType() {
        Graph g = new Graph(null);
        g.addNode("C", Node.Type.Class, null);
        g.addNode("C.m()", Node.Type.Method, null);
        g.addNode("C.n(int)", Node.Type.Method, null);
        g.addNode("C.p()", Node.Type.Method, null);
        g.addEdge("C", "C.m()", Edge.Type.Contains);
        g.addEdge("C", "C.n(int)", Edge.Type.Contains);
        g.addEdge("C", "C.p()", Edge.Type.Contains);

        g.addNode("Class", Node.Type.Class, null);
        g.addNode("Class.method()", Node.Type.Method, null);
        g.addNode("Class.method(int)", Node.Type.Method, null);
        g.addNode("Class.method(double, double)", Node.Type.Method, null);
        g.addNode("Class.method2(int)", Node.Type.Method, null);
        g.addEdge("Class", "Class.method()", Edge.Type.Contains);
        g.addEdge("Class", "Class.method2(int)", Edge.Type.Contains);
        g.addEdge("Class", "Class.method(int)", Edge.Type.Contains);
        g.addEdge("Class", "Class.method(double, double)", Edge.Type.Contains);

        Set<String> typeMethods = idsToNodesNames(Queries.methodsInType(g.getNode("C").getId(), g), g);

        assertEquals(3, typeMethods.size());
        assertTrue(typeMethods.contains("C.m()"));
        assertTrue(typeMethods.contains("C.n(int)"));
        assertTrue(typeMethods.contains("C.p()"));
    }

    @Test
    public void interfaceMethodsImplementations() {
        Graph g = new Graph(null);
        g.addNode("I", Node.Type.Interface, null);
        g.addNode("I.n(int)", Node.Type.Method, null);
        g.addNode("I.m()", Node.Type.Method, null);
        g.addEdge("I", "I.m()", Edge.Type.Contains);
        g.addEdge("I", "I.n(int)", Edge.Type.Contains);

        g.addNode("C1", Node.Type.Class, null);
        g.addNode("C1.n(int)", Node.Type.Method, null);
        g.addNode("C1.n(double)", Node.Type.Method, null);
        g.addEdge("C1", "I", Edge.Type.IsA);
        g.addEdge("C1", "C1.n(int)", Edge.Type.Contains);
        g.addEdge("C1", "C1.n(double)", Edge.Type.Contains);

        Set<String> implementations = idsToNodesNames(
                Queries.interfaceMethodImplementation(g.getNode("I.n(int)").getId(), g), g);

        assertEquals(1, implementations.size());
        assertTrue(implementations.contains("C1.n(int)"));
    }

    @Test
    public void classAttributes() {
        Graph g = new Graph(null);
        g.addNode("C", Node.Type.Class, null);
        g.addNode("C.a", Node.Type.Attribute, null);
        g.addNode("C.b", Node.Type.Attribute, null);
        g.addNode("C.m()", Node.Type.Method, null);
        g.addEdge("C", "C.a", Edge.Type.Contains);
        g.addEdge("C", "C.b", Edge.Type.Contains);
        g.addEdge("C", "C.m()", Edge.Type.Contains);

        Set<String> attributes = idsToNodesNames(Queries.classAttributes(g.getNode("C").getId(), g), g);

        assertEquals(2, attributes.size());
        assertTrue(attributes.contains("C.a"));
        assertTrue(attributes.contains("C.b"));
    }

    @Test
    public void hostType() {
        Graph g = new Graph(null);
        g.addNode("C", Node.Type.Class, null);
        g.addNode("C.m()", Node.Type.Method, null);
        g.addEdge("C", "C.m()", Edge.Type.Contains);

        Integer hostType = Queries.hostType(g.getNode("C.m()").getId(), g);

        assertEquals("C", g.getNode(hostType).getFullName());
    }

    @Test
    public void typePackage() {
        Graph g = new Graph(null);
        g.addNode("P", Node.Type.Package, null);
        g.addNode("P.C", Node.Type.Class, null);
        g.addNode("P.I", Node.Type.Interface, null);
        g.addNode("P.C.C1", Node.Type.Class, null);
        g.addEdge("P", "P.C", Edge.Type.Contains);
        g.addEdge("P", "P.I", Edge.Type.Contains);
        g.addEdge("P.C", "P.C.C1", Edge.Type.Contains);

        Integer C1Package = Queries.typePackage(g.getNode("P.C.C1").getId(), g);
        assertEquals("P", g.getNode(C1Package).getFullName());

        Integer CPackage = Queries.typePackage(g.getNode("P.C").getId(), g);
        assertEquals("P", g.getNode(CPackage).getFullName());

        Integer IPackage = Queries.typePackage(g.getNode("P.I").getId(), g);
        assertEquals("P", g.getNode(IPackage).getFullName());

        assertNull(Queries.typePackage(g.getNode("P").getId(), g));
    }

    @Test
    public void typesInPackage() {
        Graph g = new Graph(null);
        g.addNode("P", Node.Type.Package, null);
        g.addNode("P.C", Node.Type.Class, null);
        g.addNode("P.I", Node.Type.Interface, null);
        g.addNode("P.C.C1", Node.Type.Class, null);
        g.addEdge("P", "P.C", Edge.Type.Contains);
        g.addEdge("P", "P.I", Edge.Type.Contains);
        g.addEdge("P.C", "P.C.C1", Edge.Type.Contains);

        Set<String> tNames = idsToNodesNames(Queries.typesInPackage(g.getNode("P").getId(), g), g);

        assertEquals(2, tNames.size());
        assertTrue(tNames.contains("P.C"));
        assertTrue(tNames.contains("P.I"));
    }

    private Set<String> idsToNodesNames(List<Integer> ids, Graph graph) {
        return ids.stream()
                .map((n) -> graph.getNode(n).getFullName())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
