package graph;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Program;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeGenerator {
    private Program program;
    private Path projectPath;

    public CodeGenerator(Program program, String projectPath) {
        this.program = program;
        this.projectPath = Paths.get(projectPath);
    }

    public void generateCode(String outputDir) throws IOException {
        Path basePath = getProjectFolder();
        for (CompilationUnit cu: program.getCompilationUnits()) {
            Path relativePath = basePath.relativize(Paths.get(cu.pathName()));
            Path filePath = Paths.get(outputDir, relativePath.toString());
            new File(filePath.toString()).getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()));
            writer.write(cu.prettyPrint());
            writer.close();
        }
    }

    private Path getProjectFolder() {
        return Files.isDirectory(projectPath) ? projectPath : projectPath.getParent();
    }
}
