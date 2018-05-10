package app;

import com.sun.javafx.application.LauncherImpl;
import graph.CodeGenerator;
import graph.Edge;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.extendj.ast.Program;
import org.xml.sax.SAXException;
import refactoring.RefactoringExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

public class Puck2Main {
    public static void main(String args[]) throws Exception {
        switch (args.length) {
            case 0: launchGui(); break;
            case 1: run(args[0]); break;
            case 2: {
                run(args[0]).outputToFile(args[1]);
                break;
            }
            case 3:
                refactor(args[0], args[1], args[2]); break;
            default: System.out.println("Usage: java -jar puck2 programDir ?outputFile");
        }
    }

    private static void refactor(String commandsPath, String projectPath, String outputPath) {
        RefactoringExecutor executor;
        Puck2Runner runner = new Puck2Runner(projectPath);
        try {
            runner.run();
            executor = new RefactoringExecutor(runner.getGraph(), commandsPath);
            executor.execute();
            System.out.println(executor.getGraph().getProgram().prettyPrint());
            new CodeGenerator(executor.getGraph().getProgram(), projectPath).generateCode(outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Puck2Runner run(String projectPath) {
        Puck2Runner runner = new Puck2Runner(projectPath);
        try {
            runner.run();
            runner.displayGraph();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return runner;
    }

    private static void launchGui() {
        LauncherImpl.launchApplication(ConfigurationUI.class, null);
    }
}
