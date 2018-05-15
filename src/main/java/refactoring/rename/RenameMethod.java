package refactoring.rename;

import app.Puck2Runner;
import graph.Graph;
import org.extendj.ast.MethodDecl;

import java.io.IOException;

public class RenameMethod extends RenameBase {
    public static void main(String[] args) {
        Puck2Runner runner = new Puck2Runner("testfiles/TestAttribute.java");
        try {
            runner.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graph g = runner.getGraph();
        RenameMethod r = new RenameMethod(g.getNode("Foo.setX(int)").getId(), "Renamed", g);
        r.refactorCode();

        System.out.println(g.getProgram().prettyPrint());
    }

    RenameMethod(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    protected void refactorCode() {
        MethodDecl method = (MethodDecl) getGraph().getNode(getId()).getExtendjNode();
        method.setID(getNewName());
        renameReferences(method.getTypeAccess());
    }
}
