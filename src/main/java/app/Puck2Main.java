package app;

import graph.Edge;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Puck2Main {
    public static void main(String args[]) {
        if (args.length == 0 || args.length > 2) {
            System.out.println("Usage: java -jar puck2 programDir ?outputFile");
        }
    	Program p = new Program();
        loadProgram(p, args[0], "");

        HashMap<String, Node> nodes = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ProgramReader reader = new ProgramReader(p);
        reader.readInto(nodes, edges);

        if (args.length == 2) {
            outputXML(nodes, edges, args[1]);
        } else {
            displayGraph(nodes, edges);
        }
    }

    private static void displayGraph(HashMap<String, Node> nodes, ArrayList<Edge> edges) {
        for (Node node: nodes.values()) {
            System.out.println(node);
        }

        for (Edge edge: edges) {
            System.out.println(edge);
        }
    }

    private static void outputXML(HashMap<String, Node> nodes, ArrayList<Edge> edges, String path) {
        XMLExporter exporter = new XMLExporter();
        exporter.add(nodes, edges);
        try {
            exporter.writeTo(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addSource(Program p, String path) {
        try {
            p.addSourceFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadProgram(Program program, String p, String previousPath) {
        File currentFile = new File(p);
        String path = previousPath + p;

        if (currentFile.isDirectory()) {
            for (String innerFile: currentFile.list()) {
                loadProgram(program, innerFile, previousPath + path);
            }
        } else {
            try {
                addSource(program, Paths.get(previousPath, currentFile.getPath()).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
