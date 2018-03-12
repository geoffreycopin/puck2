package app;

import com.sun.javafx.application.LauncherImpl;
import graph.Edge;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import refactoring.MoveClassRefactoring;
import refactoring.RefactoringException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Puck2Main {
    public static void main(String args[]) throws IOException {
        switch (args.length) {
            case 0: launchGui(); break;
            case 1: run(args[0]); break;
            case 2: {
                run(args[0]).outputToFile(args[1]);
                break;
            }
            case 5: {
                moveClass(run(args[1]), args[2], args[3], args[4]);
                break;
            }
            default: System.out.println("Usage: java -jar puck2 programDir ?outputFile");
        }
    }

    private static Puck2Runner run(String projectPath) {
        Puck2Runner runner = new Puck2Runner(projectPath);
        runner.getProgram().addClassPath(new SourceFolderPath("testfiles"));
        try {
            runner.run();
            runner.displayGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return runner;
    }

    private static void moveClass(Puck2Runner runner, String oldPackage,
                                  String newPackage, String className)
    {
        MoveClassRefactoring r = new MoveClassRefactoring(runner.getNodes(),
                                                          runner.getEdges());

        try {
            r.doRefactor(oldPackage, newPackage, className);
        } catch (RefactoringException e) {
            System.err.println(e.getMessage());
        }

        for (CompilationUnit cu: runner.getProgram().getCompilationUnitList()) {
            System.out.println("COMPILATION UNIT");
            System.out.println(cu.prettyPrint());
        }
    }

    private static void launchGui() {
        LauncherImpl.launchApplication(ConfigurationUI.class, null);
    }
}
