package refactoring;

import graph.Edge;
import graph.Node;
import org.extendj.ast.ClassDecl;
import org.extendj.ast.CompilationUnit;

import java.util.HashMap;
import java.util.List;

public class MoveClassRefactoring {
    private List<Edge> edges;
    private HashMap<String, Node> nodes;

    public MoveClassRefactoring(HashMap<String, Node> nodes, List<Edge> edges) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public void doRefactor(String oldPackage, String newPackage, String className)
            throws RefactoringException
    {
        String oldName = oldPackage + "." + className;
        String newName = newPackage + "." + className;
        Node node = nodes.remove(oldName);

        if (node == null) {
            throw new RefactoringException("Class" + oldName + " does not exist");
        }

        editClassPackage(node, newPackage);
        correctEdges(oldName, newName);
    }

    private void editClassPackage(Node classNode, String newPackage) {
        ClassDecl classDecl = (ClassDecl) classNode.getExtendjNode();
        classDecl.setID("Toast");
        CompilationUnit compilationUnit = classDecl.compilationUnit();
        compilationUnit.setPackageDecl(newPackage);
    }

    private void correctEdges(String oldName, String newName) {
        for (Edge e: edges) {
            if (e.getTargetName().equals(oldName)) {
                e.setTargetName(newName);
            } else if (e.getSourceName().equals(oldName)) {
                e.setSourceName(newName);
            }
        }
    }
}
