package graph.nodes;

import static org.junit.Assert.assertEquals;

import graph.Edge;
import graph.Node;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class TestProgramReader extends TestBase{

    @BeforeClass
    public static void init() {
        setTestFile("testfiles/Test.java");
        load();
    }

    @Test
    public void extractClasses() {
        Set<String> expectedClasses = new HashSet<>(Arrays.asList(
                "test.SuperTest", "test.Test"));
        
        nodesEquals(expectedClasses, Node.Type.Class);
    }

    @Test
    public void extractPackage() {
        Set<String> expectedPackages = new HashSet<>(Arrays.asList(
                "test"
        ));
        nodesEquals(expectedPackages, Node.Type.Package);
    }

    @Test
    public void extractInterfaces() {
        Set<String> expectedInterfaces = new HashSet<>(Arrays.asList(
                "test.Foo"
        ));
        nodesEquals(expectedInterfaces, Node.Type.Interface);
    }

    @Test
    public void containsDependency() {
        Set<TestEdge> contains = new HashSet<>(Arrays.asList(
                new TestEdge("test", "test.SuperTest"),
                new TestEdge("test", "test.Test"),
                new TestEdge("test", "test.Foo"),
                new TestEdge("test.SuperTest", "test.SuperTest.r"),
                new TestEdge("test.SuperTest", "test.SuperTest.superMethod(test.Test, test.Foo)"),
                new TestEdge("test.SuperTest.superMethod(test.Test, test.Foo)", "test.SuperTest.superMethod(test.Test, test.Foo).body"),
                new TestEdge("test.Test", "test.Test.r"),
                new TestEdge("test.Test", "test.Test.f"),
                new TestEdge("test.Test", "test.Test.f()"),
                new TestEdge("test.Test.f()", "test.Test.f().body"),
                new TestEdge("test.Test", "test.Test.m(int)"),
                new TestEdge("test.Test.m(int)", "test.Test.m(int).body"),
                new TestEdge("test.Test", "test.Test.m(double)"),
                new TestEdge( "test.Test.m(double)", "test.Test.m(double).body"),
                new TestEdge("test.Test", "test.Test.m(test.Foo)"),
                new TestEdge("test.Test.m(test.Foo)", "test.Test.m(test.Foo).body"),
                new TestEdge("test.Foo", "test.Foo.t()"),
                new TestEdge("test.Foo", "test.Foo.t(test.Test)")
        ));
        edgesEquals(contains, Edge.Type.Contains);
    }

    @Test
    public void isADependency() {
        Set<TestEdge> isA = new HashSet<>(Arrays.asList(
                new TestEdge("test.SuperTest", "test.Test"),
                new TestEdge("test.Test", "test.Foo"),
                new TestEdge("test.Test.f", "test.SuperTest")
        ));
        edgesEquals(isA, Edge.Type.IsA);
    }

    @Test
    public void usesDependecy() {
        Set<TestEdge> uses = new HashSet<>(Arrays.asList(
                new TestEdge("test.SuperTest.superMethod(test.Test, test.Foo)", "test.Test"),
                new TestEdge("test.SuperTest.superMethod(test.Test, test.Foo)", "test.Foo"),
                new TestEdge("test.Test.m(test.Foo)", "test.Foo"),
                new TestEdge("test.Foo.t(test.Test)", "test.Test")
        ));
        edgesEquals(uses, Edge.Type.Uses);
    }
}
