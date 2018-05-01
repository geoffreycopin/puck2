package refactoring.rename;

import app.Puck2Runner;
import graph.Edge;
import graph.Graph;
import org.extendj.ast.ASTNode;
import org.extendj.ast.MethodAccess;
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
        r.refactor();

        System.out.println(g.getProgram().prettyPrint());
    }

    RenameMethod(Integer id, String newName, Graph graph) {
        super(id, newName, graph);
    }

    @Override
    public void refactor() {
        MethodDecl method = (MethodDecl) getGraph().getNode(getId()).getExtendjNode();
        method.setID(getNewName());

        for (Edge e: getGraph().queryEdgesTo(getId(), Edge.Type.Uses)) {
            ASTNode<ASTNode> dp = e.getDependencyPointAccess();
            if (dp instanceof MethodAccess) {
                ((MethodAccess) dp).setID(getNewName());
            }
        }
    }
}
