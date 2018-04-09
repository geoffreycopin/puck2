package graph;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Program;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeGenerator {
    private Program program;

    public CodeGenerator(Program program) {
        this.program = program;
    }

    public void generateCode(String outputDir) throws IOException {
        for (CompilationUnit cu: program.getCompilationUnits()) {
            Path filePath = Paths.get(outputDir, cu.relativeName());
            new File(filePath.toString()).getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()));
            writer.write(cu.prettyPrint());
            writer.close();
        }
    }
}
