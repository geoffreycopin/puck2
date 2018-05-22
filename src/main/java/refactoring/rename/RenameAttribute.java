package refactoring.rename;

import app.Puck2Runner;
import graph.Graph;
import org.extendj.ast.FieldDeclarator;

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
        r.refactorCode();

        System.out.println(g.getProgram().prettyPrint());
    }

    RenameAttribute(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void refactorCode() {
        FieldDeclarator field = (FieldDeclarator) getGraph().getNode(getId()).getExtendjNode();
        field.setID(getNewName());
        renameReferences(field.createBoundAccess().lastAccess());
    }
}
