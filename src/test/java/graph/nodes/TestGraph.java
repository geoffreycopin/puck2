package graph.nodes;

import graph.Edge;
import graph.Node;
import graph.readers.ProgramReader;
import org.extendj.ast.Program;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestGraph {
    private static final File testDir = new File("testdir");
    private static final Pattern resultFilePattern = Pattern.compile(".*_(all|contains)\\.result");
    private static final Pattern graphStringPAttern =
            Pattern.compile("<(Node|Edge)[\\h]*([\\w]*[\\h]*=[\\h]*[\\w]*[\\h]*)+.*>");
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

        System.out.println("Total: " + nbTests);
        System.out.println("Failed: " + failed);
        System.out.println("Passed: " + passed);

        assertEquals(failed, 0);
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
        System.out.println ("Running test: " + currentTest.getTitle());
        HashSet<String> actual;

        try {
            actual = new HashSet<>(readXml(getProgramOutput(currentTest.program())));
        } catch (Exception e) {
            System.out.println("\tfailed: " + e.getMessage());
            return;
        }

        for (String resultFile: currentTest.getResultFiles().keySet()) {
            try {
                boolean exhaustive = currentTest.getResultFiles().get(resultFile);
                runSubTest(resultFile, actual, exhaustive);
            } catch (Exception e) {
                System.out.println("\t" + resultFile + " " + e.getMessage());
            }
        }
    }

    private void runSubTest(String resultFile, HashSet<String> actual, boolean exhaustive) throws Exception {
        HashSet<String> expected = new HashSet<>(readXml(getExpectedValues(resultFile)));
        TestResult result = match(expected, actual, exhaustive);
        System.out.print("\t" + resultFile + ": ");
        result.display();
        if (result.isSuccess()) {
            passed++;
        } else {
            failed++;
        }
    }

    private TestResult match(HashSet<String> expetcted, HashSet<String> actual, boolean exhaustive) {
        HashSet<String> savedActual = new HashSet<>(actual);
        actual.removeAll(expetcted);
        expetcted.removeAll(savedActual);

        if (expetcted.size() == 0 && (!exhaustive || actual.size() == 0)) {
            return new TestResult(true);
        }

        TestResult result = new TestResult(false);
        result.addAdditionnalElements(expetcted);

        if (exhaustive) {
            result.addMissingLines(actual);
        }

        return result;
    }

    private String getProgramOutput(Program p) {
        HashMap<String, Node> nodes = new HashMap<>();
        Set<Edge> edges = new HashSet<>();
        ProgramReader reader = new ProgramReader(p);
        reader.readInto(nodes, edges);

        StringBuilder result = new StringBuilder();
        result.append("<DG>\n");

        nodes.values().forEach((n) -> result.append(n.toString()));
        edges.forEach((e) -> result.append(e.toString()));

        result.append("\n</DG>");
        return result.toString();
    }

    private String getExpectedValues(String resultFile) throws IOException {
        return Files.lines(Paths.get(resultFile))
                    .collect(Collectors.joining());
    }

    private ArrayList<String> readXml(String text) throws Exception {
        ArrayList<String> result = new ArrayList();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(text));
        Document doc = dBuilder.parse(source);
        doc.getDocumentElement().normalize();

        NodeList nodes = doc.getElementsByTagName("Node");
        for(int i = 0; i < nodes.getLength(); i++) {
            result.add(nodeToString(nodes.item(i)));
        }

        NodeList edges = doc.getElementsByTagName("Edge");
        for (int i = 0; i < edges.getLength(); i++) {
            result.add(nodeToString(edges.item(i)));
        }

        return result;
    }

    private String nodeToString(org.w3c.dom.Node n) throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(n), new StreamResult(sw));
        return sw.toString();
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

    private class TestResult {
        boolean success;
        ArrayList<String> additionnalLines = new ArrayList<>();
        ArrayList<String> missingLines = new ArrayList<>();

        public TestResult(boolean success) {
            this.success = success;
        }

        public void addAdditionnalElements(Collection<String> lines) {
            additionnalLines.addAll(lines);
        }

        public void addMissingLines(Collection<String> lines) {
            missingLines.addAll(lines);
        }

        public boolean isSuccess() {
            return success;
        }

        public ArrayList<String> getAdditionnalLines() {
            return additionnalLines;
        }

        public ArrayList<String> getMissingLines() {
            return missingLines;
        }

        public void display() {
            if (isSuccess()) {
                System.out.println("ok.");
            } else {
                System.out.println("failed");
            }

            for (String a: getAdditionnalLines()) {
                System.out.println("\t\tAdditionnal: " + a);
            }

            for (String m: getMissingLines()) {
                System.out.println("\t\tMissing: " + m);
            }
        }
    }
}
