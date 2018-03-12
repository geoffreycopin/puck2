package graph.nodes;

import org.extendj.ast.ClassDecl;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Program;
import org.extendj.ast.TypeDecl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramLoader {
    private Program program = new Program();

    public void addFile(String path) {
        try {
            program.addSourceFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFiles(ArrayList<String> paths) {
        for (String p: paths) {
            addFile(p);
        }
    }

    public Program getProgram() {
        return program;
    }
}
