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

public class RefactoringExecutor {
    Graph graph;
    ArrayList<RefactoringBase> commands = new ArrayList<>();

    public RefactoringExecutor(Graph graph, String commandsFile) throws
            IOException, ParserConfigurationException, SAXException
    {
        this.graph = graph;
        String xml = new String(Files.readAllBytes(Paths.get(commandsFile)));
        initCommands(xml);
    }

    private void initCommands(String xml) throws
            ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        Document doc = dBuilder.parse(source);
        doc.getDocumentElement().normalize();

        commands.addAll(readRenames(doc));
    }

    private ArrayList<RenameBase> readRenames(Document doc) {
        ArrayList<RenameBase> result = new ArrayList<>();
        NodeList renames = doc.getElementsByTagName("Rename");

        for (int i = 0; i < renames.getLength(); i++) {
            Element elem = (Element) renames.item(i);
            Integer id = Integer.valueOf(elem.getAttribute("id"));
            String newName = elem.getAttribute("newName");
            result.add(Rename.newRenameStrategy(id, newName, graph));
        }

        return result;
    }

    public void execute() {
        for (RefactoringBase rb: commands) {
            rb.refactorCode();
        }
    }

    public Graph getGraph() {
        return graph;
    }
}
