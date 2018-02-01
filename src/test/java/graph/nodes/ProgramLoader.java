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

    public Program getProgram() {
        return program;
    }

    public Iterable<CompilationUnit> getCompilationUnits() {
        return program.getCompilationUnitList();
    }

    public Iterable<TypeDecl> getTypeDecls() {
        List<TypeDecl> declarations = new ArrayList<>();

        for (CompilationUnit cu: getCompilationUnits()) {
            for (TypeDecl t: cu.getTypeDeclList()) {
                declarations.add(t);
            }
        }

        return declarations;
    }

    public Iterable<ClassDecl> getClassDeclarations() {
        List<ClassDecl> declarations = new ArrayList<>();

        for (TypeDecl t: getTypeDecls()) {
            if (t instanceof ClassDecl) {
                declarations.add((ClassDecl) t);
            }
        }

        return declarations;
    }
}
