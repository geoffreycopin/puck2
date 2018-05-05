package graph.nodes;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestXMLExporter {
    private static XMLExporter exporter;

    @BeforeClass
    public static void init() {
        ProgramLoader loader = new ProgramLoader();
        loader.addFile("testfiles/ExporterTest.java");
        ProgramReader r = new ProgramReader(loader.getProgram());
        Graph graph = r.read();

        exporter = new XMLExporter();
        exporter.add(graph.getNodes(), graph.getEdges());
    }

    @Test
    public void generateXML() {
        String expected = "<?xml version=\"1.0\"?>\n" +
                "<DG>\n" +
                "\t<node type=\"package\" id=\"0\" name=\"exporter\"/>\n" +
                "\t<node type=\"class\" id=\"1\" name=\"TestClass\"/>\n" +
                "\t<edge type=\"contains\" src=\"0\" dest=\"1\" id=\"0\"/>\n" +
                "</DG>\n";
        assertEquals(expected, exporter.generateXml());
    }
}
