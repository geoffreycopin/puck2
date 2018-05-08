package app;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Puck2Runner {
    String projectPath;
    Graph graph;
    Program program;

    public Puck2Runner(String path) {
        projectPath = path;
        program = new Program();
        graph = new Graph(program);
    }

    public Map<Integer, Node> getNodes() {
        return graph.getNodes();
    }

    public Set<Edge> getEdges() {
        return graph.getEdges();
    }

    public Program getProgram() {
        return program;
    }

    public Graph getGraph() {
        return graph;
    }

    public void run() throws IOException {
        loadProgram(projectPath);
        ProgramReader reader = new ProgramReader(program);
        graph = reader.read();
    }

    public void outputToFile(String outputFile) throws Exception {
    	XMLExporter exporter = new XMLExporter();
        exporter.add(graph.getNodes(), graph.getEdges());
        exporter.writeTo(outputFile);
    }
    
    public void XMLValidation()throws Exception{
    	 XMLExporter exporter = new XMLExporter();
    	 exporter.add(graph.getNodes(), graph.getEdges());
    	  File temp = File.createTempFile("file", ".tmp");
          BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
          writer.write(exporter.generateXml());
          writer.close();
          this.XMLValidator(temp.getPath());
    }
    
    public void XMLValidator(String outputfile) throws Exception {
    	 // parse an XML document into a DOM tree
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parser.parse(new File(outputfile));

        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File("XMLValidator/dg.xsd"));
        Schema schema = factory.newSchema(schemaFile);

        // create a Validator instance, which can be used to validate an instance document
        Validator validator = schema.newValidator();

        // validate the DOM tree
        try {
            validator.validate(new DOMSource(document));
        } catch (SAXException e) {
            // instance document is invalid!
        	System.out.println(e.getMessage());
        	throw new Exception();
        }
    }

    private void loadProgram(String path) throws IOException {
        File f = new File(path);
        if (f.isDirectory()) {
            for (File innerFile: f.listFiles()) {
                loadProgram(innerFile.getAbsolutePath());
            }
        } else if (getFileExtension(path).equals("java")) {
            program.addSourceFile(path);
        }
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");

        if (index == -1 || index == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(index + 1);
    }

    public void displayGraph() {
        for (Node node: graph.getNodes().values()) {
            System.out.println(node);
        }

        for (Edge edge: graph.getEdges()) {
            System.out.println(edge);
        }
    }
}
