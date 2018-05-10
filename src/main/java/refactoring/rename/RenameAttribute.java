package refactoring.rename;

import app.Puck2Runner;
import graph.Edge;
import graph.Graph;
import org.extendj.ast.*;

import java.io.IOException;

public class RenameAttribute extends RenameBase {
    public static void main(String[] args) {
        Puck2Runner runner = new Puck2Runner("testfiles/TestAttribute.java");
        try {
            runner.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graph g = runner.getGraph();
        RenameAttribute r = new RenameAttribute(g.getNode("Foo.X").getId(), "Renamed", g);
        r.refactor();

        System.out.println(g.getProgram().prettyPrint());
    }

    RenameAttribute(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void refactor() {
        FieldDeclarator field = (FieldDeclarator) getGraph().getNode(getId()).getExtendjNode();
        field.setID(getNewName());
        renameReferences(field.createBoundAccess().lastAccess());
    }
}
