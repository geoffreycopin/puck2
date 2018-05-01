package refactoring.rename;

import graph.Graph;
import org.extendj.ast.ASTNode;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.MethodDecl;

public class Rename {
    public RenameBase newRenameStrategy(Integer id, String newName, Graph graph) {
        ASTNode<ASTNode> node = graph.getNode(id).getExtendjNode();
        if (node instanceof ClassDecl) {
            return new RenameClass(id, newName, graph);
        } else if (node instanceof MethodDecl) {
            return new RenameMethod(id, newName, graph);
        } else if (node instanceof FieldDecl) {
            return new RenameAttribute(id, newName, graph);
        }
        return null;
    }
}
