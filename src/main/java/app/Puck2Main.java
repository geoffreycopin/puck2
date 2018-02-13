package app;

import graph.Edge;
import graph.Node;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puck2Main {
    public static void main(String args[]) {
    	String[] files = new File("testfiles").list();
    	for ( String s : files ){
    		Program p = loadProgram("testfiles/"+s);
    		System.out.println(s);
        ProgramReader r = new ProgramReader(p);
        Map<String, Node> nodes = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        r.readInto(nodes, edges);

        for (Node n: nodes.values()) {
            System.out.println(n);
        }

        for (Edge e: edges) {
            System.out.println(e);
        }
    	}
    }

    private static Program loadProgram(String path) {
        Program p = new Program();

        try {
            p.addSourceFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
}
