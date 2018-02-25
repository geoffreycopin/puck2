package app;

import com.sun.javafx.application.LauncherImpl;
import graph.Edge;
import graph.Node;
import graph.XMLExporter;
import graph.readers.ProgramReader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.extendj.ast.Program;

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
            default: System.out.println("Usage: java -jar puck2 programDir ?outputFile");
        }
    }

    private static Puck2Runner run(String projectPath) {
        Puck2Runner runner = new Puck2Runner(projectPath);
        try {
            runner.run();
            runner.displayGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return runner;
    }

    private static void launchGui() {
        LauncherImpl.launchApplication(ConfigurationUI.class, null);
    }
}
