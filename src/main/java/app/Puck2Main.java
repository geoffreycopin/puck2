package app;

import com.sun.javafx.application.LauncherImpl;
import graph.CodeGenerator;
import graph.Graph;
import org.extendj.ast.CompilationUnit;
import org.xml.sax.SAXException;
import refactoring.RefactoringExecutor;
import refactoring.rename.Rename;
import refactoring.rename.RenameBase;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puck2Main {
    static Pattern saveGraph = Pattern.compile("saveGraph (.+)");
    static Pattern rename = Pattern.compile("rename (.+) (.+)");
    static Pattern execPlan = Pattern.compile("execPlan (.+)");
    static Pattern saveCode = Pattern.compile("saveCode (.+)");

    public static void main(String args[]) throws Exception {
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
        Puck2Runner runner = initRunner(projectPath);
        Scanner input = new Scanner(System.in);

        while (input.hasNext()) {
            String command = input.nextLine();
            if (command.equals("display")) {
                runner.displayGraph();
            } else if (command.equals("prettyPrint")) {
                execPrettyPrint(runner);
            } else if (command.startsWith("saveGraph")) {
                execSaveGraph(runner, saveGraph.matcher(command));
            } else if (command.startsWith("rename")) {
                execRenameId(runner, rename.matcher(command));
            } else if (command.startsWith("execPlan")) {
                execPlan(runner, execPlan.matcher(command));
            } else if (command.startsWith("saveCode")) {
                execSaveCode(runner, saveCode.matcher(command));
            }
        }

        return runner;
    }

    private static Puck2Runner initRunner(String projectPath) {
        Puck2Runner runner = new Puck2Runner(projectPath);
        try {
            runner.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return runner;
    }

    private static void execSaveGraph(Puck2Runner runner, Matcher m) {
        if (! m.find()) {
            System.err.println("ERROR: invalid command");
            return;
        }

        String path = m.group(1);
        try {
            runner.outputToFile(path);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void execRenameId(Puck2Runner runner, Matcher m) {
        if (! m.find()) {
            System.err.println("ERROR: invalid command");
            return;
        }

        RenameBase rb = getRenameStrategy(m.group(1), m.group(2), runner.getGraph());
        if (rb == null) {
            System.err.println("ERROR: node <" + m.group(1) + "> doesn't exist.");
            return;
        }

        rb.refactor();
    }

    private static RenameBase getRenameStrategy(String target, String newName, Graph graph) {
        RenameBase rb;
        try {
            Integer id = Integer.valueOf(target);
            rb = Rename.newRenameStrategy(id, newName, graph);
        } catch (NumberFormatException n) {
            rb = Rename.newRenameStartegy(target, newName, graph);
        }
        return rb;
    }

    private static void execPlan(Puck2Runner runner, Matcher m) {
        if (! m.find()) {
            System.err.println("ERROR: invalid command");
            return;
        }
        try {
            RefactoringExecutor executor = new RefactoringExecutor(runner.getGraph(), m.group(1));
            executor.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private static void execSaveCode(Puck2Runner runner, Matcher m) {
        if (! m.find()) {
            System.err.println("Error: invalid command");
            return;
        }
        CodeGenerator generator = new CodeGenerator(runner.getProgram(), runner.getProjectPath());
        try {
            generator.generateCode(m.group(1));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("DONE");
        }
    }

    private static void execPrettyPrint(Puck2Runner runner) {
        for (CompilationUnit cu: runner.getProgram().getCompilationUnits()) {
            System.out.println("####" + cu.pathName() + "####");
            System.out.println(cu.prettyPrint() + "\n\n");
        }
    }

    private static void launchGui() {
        LauncherImpl.launchApplication(ConfigurationUI.class, null);
    }
}
