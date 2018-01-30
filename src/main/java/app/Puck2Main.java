package app;

import graph.nodes.Node;
import graph.nodes.ProgramReader;
import org.extendj.ast.Program;

public class Puck2Main {
    public static void main(String args[]) {
        Program p = loadProgram("testfiles/Test.java");
        ProgramReader r = new ProgramReader();
        for (Node n: r.read(p)) {
            System.out.println(n);
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
