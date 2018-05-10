package refactoring.rename;

import app.Puck2Runner;
import graph.Graph;
import org.extendj.ast.ParameterDeclaration;

import java.io.IOException;

public class RenameParameter extends RenameBase {
    public static void main(String[] args) {
        Puck2Runner runner = new Puck2Runner("testfiles/TestAttribute.java");
        try {
            runner.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graph g = runner.getGraph();
        RenameParameter r = new RenameParameter(g.getNode("Foo.setX(int).val").getId(), "Renamed", g);
        r.refactor();

        System.out.println(g.getProgram().prettyPrint());
    }

    RenameParameter(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void refactor() {
        ParameterDeclaration p = (ParameterDeclaration) getGraph().getNode(getId())
                .getExtendjNode();
        p.setID(getNewName());
        renameReferences(p.getTypeAccess());
    }
}
