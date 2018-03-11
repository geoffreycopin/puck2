package graph.nodes;

import static org.junit.Assert.assertEquals;

import graph.Edge;
import graph.Node;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class TestJavaClass extends TestBase {


	@BeforeClass
	public static void init() {
		setTestFile("TestJava/IntEvent.java");
		load();
	}

	@Test
	public void extractClasses() {
		Set<String> expectedClasses = new HashSet<>(Arrays.asList(
				"dataflow.core.IntEvent","java.util.List","java.math.BigInteger","@primitive.void","java.lang.String"));

		nodesEquals(expectedClasses, Node.Type.Class);
	}

	@Test
	public void extractPackage() {
		Set<String> expectedPackages = new HashSet<>(Arrays.asList(
				"java.util","dataflow.core","java.math","@primitive","java.lang"
				));
		nodesEquals(expectedPackages, Node.Type.Package);
	}

	@Test
	public void extractInterfaces() {
		Set<String> expectedInterfaces = new HashSet<>(Arrays.asList(
				""
				));
		//nodesEquals(expectedInterfaces, Node.Type.Interface);
	}

	@Test
	public void containsDependency() {
		Set<TestEdge> contains = new HashSet<>(Arrays.asList(
				new TestEdge("dataflow.core", "dataflow.core.IntEvent"),
				new TestEdge("dataflow.core.IntEvent", "dataflow.core.IntEvent.r"),
				new TestEdge("dataflow.core.IntEvent", "dataflow.core.IntEvent.d"),
				new TestEdge("dataflow.core.IntEvent", "dataflow.core.IntEvent.val"),
				new TestEdge("dataflow.core.IntEvent", "dataflow.core.IntEvent.getValue()"),
				new TestEdge("dataflow.core.IntEvent", "dataflow.core.IntEvent.m(java.util.List)"),
				new TestEdge("dataflow.core.IntEvent", "dataflow.core.IntEvent.toString()"),
				new TestEdge("dataflow.core.IntEvent.getValue()", "dataflow.core.IntEvent.getValue().body"),
				new TestEdge("dataflow.core.IntEvent.m(java.util.List)", "dataflow.core.IntEvent.m(java.util.List).body"),
				new TestEdge("dataflow.core.IntEvent.toString()", "dataflow.core.IntEvent.toString().body"),
				new TestEdge("java.util", "java.util.List"),
				new TestEdge("java.math", "java.math.BigInteger"),
				new TestEdge("java.lang", "java.lang.String"),
				new TestEdge("@primitive", "@primitive.void")


				
				));
		edgesEquals(contains, Edge.Type.Contains);
	}

	@Test
	public void isADependency() {
		Set<TestEdge> isA = new HashSet<>(Arrays.asList(
				new TestEdge("dataflow.core.IntEvent.r", "java.util.List"),
				new TestEdge("dataflow.core.IntEvent.d","java.math.BigInteger"),
				new TestEdge("dataflow.core.IntEvent.val", "java.math.BigInteger")
				));
		edgesEquals(isA, Edge.Type.IsA);
	}

	@Test
	public void usesDependecy() {
		Set<TestEdge> uses = new HashSet<>(Arrays.asList(
				new TestEdge("dataflow.core.IntEvent.getValue()", "java.math.BigInteger"),
				new TestEdge("dataflow.core.IntEvent.m(java.util.List)", "java.util.List"),
				new TestEdge("dataflow.core.IntEvent.m(java.util.List)", "@primitive.void"),
				new TestEdge("dataflow.core.IntEvent.toString()", "java.lang.String")
				
				));
		edgesEquals(uses, Edge.Type.Uses);
	}
}


