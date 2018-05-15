package refactoring.rename;

import graph.Graph;
import org.extendj.ast.*;

public class Rename {
    public static RenameBase newRenameStrategy(Integer id, String newName, Graph graph) {
        if (graph.getNode(id) == null) {
            return null;
        }

        ASTNode<ASTNode> node = graph.getNode(id).getExtendjNode();
        if (node instanceof ClassDecl) {
            return new RenameClass(id, newName, graph);
        } else if (node instanceof MethodDecl) {
            return new RenameMethod(id, newName, graph);
        } else if (node instanceof FieldDeclarator) {
            return new RenameAttribute(id, newName, graph);
        } else if (node instanceof ParameterDeclaration) {
            return new RenameParameter(id, newName, graph);
        } else if (node instanceof InterfaceDecl) {
            return new RenameInterface(id, newName, graph);
        }

        return null;
    }

    public static RenameBase newRenameStartegy(String target, String newName, Graph graph) {
        if (graph.getNode(target) == null) {
            return null;
        }
        Integer id = graph.getNode(target).getId();
        return newRenameStrategy(id, newName, graph);
    }
}
