package app;

import graph.Edge;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Puck2Runner {
    String projectPath;
    HashMap<String, Node> nodes;
    List<Edge> edges;
    Program program;

    public Puck2Runner(String path) {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
        projectPath = path;
        program = new Program();
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Program getProgram() {
        return program;
    }

    public void run() throws IOException {
        loadProgram(projectPath);
        ProgramReader reader = new ProgramReader(program);
        reader.readInto(nodes, edges);
    }

    public void outputToFile(String outputFile) throws IOException {
        XMLExporter exporter = new XMLExporter();
        exporter.add(nodes, new ArrayList<>(edges));
        exporter.writeTo(outputFile);
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
        for (Node node: nodes.values()) {
            System.out.println(node);
        }

        for (Edge edge: edges) {
            System.out.println(edge);
        }
    }
}
