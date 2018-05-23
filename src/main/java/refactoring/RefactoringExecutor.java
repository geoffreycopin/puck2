package refactoring;

import graph.Graph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import refactoring.rename.Rename;
import refactoring.rename.RenameBase;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RefactoringExecutor {
    ArrayList<RefactoringBase> commands = new ArrayList<>();

    public RefactoringExecutor() { }

    public RefactoringExecutor(Graph graph, String commandsFile) throws
            IOException, ParserConfigurationException, SAXException
    {
        String xml = new String(Files.readAllBytes(Paths.get(commandsFile)));
        initCommands(xml, graph);
    }

    private void initCommands(String xml, Graph g) throws
            ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        Document doc = dBuilder.parse(source);
        doc.getDocumentElement().normalize();

        commands.addAll(readRenames(doc, g));
    }

    private ArrayList<RenameBase> readRenames(Document doc, Graph graph) {
        ArrayList<RenameBase> result = new ArrayList<>();
        NodeList renames = doc.getElementsByTagName("Rename");

        for (int i = 0; i < renames.getLength(); i++) {
            Element elem = (Element) renames.item(i);
            Integer id = Integer.valueOf(elem.getAttribute("id"));
            String newName = elem.getAttribute("newName");
            result.addAll(Rename.newRenameStrategy(id, newName, graph));
        }

        return result;
    }

    public void addRefactoring(RefactoringBase r) {
        commands.add(r);
    }

    public void addRefactorings(List<RefactoringBase> r) {
        commands.addAll(r);
    }

    public void execute() {
        for (RefactoringBase rb: commands) {
            rb.refactor();
        }
    }
}
