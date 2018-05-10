package refactoring.rename;

import graph.Graph;
import org.extendj.ast.*;

public class Rename {
    public static RenameBase newRenameStrategy(Integer id, String newName, Graph graph) {
        ASTNode<ASTNode> node = graph.getNode(id).getExtendjNode();
        if (node instanceof ClassDecl) {
            return new RenameClass(id, newName, graph);
        } else if (node instanceof MethodDecl) {
            return new RenameMethod(id, newName, graph);
        } else if (node instanceof FieldDeclarator) {
            return new RenameAttribute(id, newName, graph);
        }
        return null;
    }
}
