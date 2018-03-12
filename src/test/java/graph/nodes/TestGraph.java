package graph.nodes;

import com.sun.tools.javac.util.Pair;
import graph.Edge;
import graph.Node;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestGraph {
    private static final File testDir = new File("testdir");
    private static final Pattern resultFilePattern = Pattern.compile(".*_(all|contains)\\.result");
    private static final Pattern graphStringPAttern =
            Pattern.compile(".*(\\p{Alnum}*\\p{Blank}*=\\p{Blank}*\\p{Alnum}*)*.*");
    private int failed = 0;
    private int passed = 0;
    private int nbTests = 0;

    @Test
    public void run() {
        ArrayList<TestCase> testCases = extractTestCases();

        System.out.println("FOUND " + testCases.size() + " TESTS");

        for (TestCase t: testCases) {
            runTest(t);
        }
    }

    private ArrayList<TestCase> extractTestCases() {
        ArrayList<TestCase> testCases = new ArrayList<>();

        for (File f: testDir.listFiles()) {
            if (f.isDirectory()) {
                TestCase t = new TestCase(f.getName());
                addDirectory(t, f);
                testCases.add(t);
            }
        }

        return testCases;
    }

    private void addDirectory(TestCase testCase, File currentTestDir) {
        for (File f: currentTestDir.listFiles()) {
            Matcher m = resultFilePattern.matcher(f.getName());
            if (f.isDirectory()) {
                addDirectory(testCase, f);
            } else if (m.matches()) {
                boolean exhaustive = m.group(1).equals("all");
                nbTests++;
                testCase.addResultFile(f.getAbsolutePath(), exhaustive);
            } else if (f.getName().endsWith(".java")) {
                testCase.addTestFile(f.getAbsolutePath());
            }
        }
    }

    private void runTest(TestCase currentTest) {
        System.out.println("Running test: " + currentTest.getTitle());
        HashSet<String> graphStrings = generateGraphsStrings(currentTest.program());
    }

    private void runSubTest(String resultFile, boolean exhaustive, HashSet<String> graphLines) throws IOException {
        HashSet<String> resultLines = generateResultStrings(resultFile);
    }

    private Pair<HashSet<String>, HashSet<String>> match(HashSet<String> expetcted, HashSet<String> actual) {
        HashSet<String> remaining;
        HashSet<String> missing;
        // TODO: implement
    }

    private HashSet<String> generateGraphsStrings(Program p) {
        HashMap<String, Node> nodes = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ProgramReader reader = new ProgramReader(p);
        reader.readInto(nodes, edges);

        HashSet<String> result = new HashSet<>();

        nodes.values().forEach((n) -> result.add(n.toString()));
        edges.forEach((e) -> result.add(e.toString()));

        return result;
    }

    private HashSet<String> generateResultStrings(String resultFile) throws IOException {
        return Files.lines(Paths.get(resultFile))
                    .collect(Collectors.toCollection(HashSet::new));
    }

    private ArrayList<String> normalizeLines(ArrayList<String> lines) {
        return lines.stream()
                .map(this::normalizeLine)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String normalizeLine(String line) {
        Matcher m = graphStringPAttern.matcher(line);
        String result = "";
        HashMap<String, String> values = new HashMap<>();

        for (int i = 0; i < m.groupCount(); i++) {
            String[] splitedLine = m.group(i).split("=");
            values.put(splitedLine[0], splitedLine[1]);
        }

        ArrayList<String> keys = values.keySet().stream().sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        for (String key: keys) {
            result += key + "=" + values.get(key);
        }

        return result;
    }

    private class TestCase {
        String title;
        HashMap<String, Boolean> resultFiles;
        ArrayList<String> testFiles;

        TestCase(String title) {
            this.title = title;
            resultFiles = new HashMap<>();
            testFiles = new ArrayList<>();
        }

        void addResultFile(String fileName, boolean exhaustive) {
            resultFiles.put(fileName, exhaustive);
        }

        void addTestFile(String fileName) {
            testFiles.add(fileName);
        }

        public String getTitle() {
            return title;
        }

        public HashMap<String, Boolean> getResultFiles() {
            return resultFiles;
        }

        public ArrayList<String> getTestFiles() {
            return testFiles;
        }

        public Program program() {
            ProgramLoader loader = new ProgramLoader();
            loader.addFiles(getTestFiles());
            return loader.getProgram();
        }
    }
}
