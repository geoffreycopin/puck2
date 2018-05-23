package refactoring.rename;

import graph.Graph;
import graph.Node;
import graph.Queries;
import org.extendj.ast.*;
import refactoring.RefactoringBase;
import refactoring.RefactoringError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rename {
    public static List<RenameBase> newRenameStrategy(Integer id, String newName, Graph graph) {
        if (graph.getNode(id) == null) {
            throw new RefactoringError("Node " + id + " doesn't exists !");
        }

        if (! isValidJavaIdentifier(newName)) {
            throw new RefactoringError(newName + " is not a valid Java identifier !");
        }

        ArrayList<RenameBase> renames = new ArrayList<>();

        Node node = graph.getNode(id);
        if (node.getType() == Node.Type.Class) {
            renames.add(new RenameClass(id, newName, graph));
        } else if (node.getType() == Node.Type.Method) {
            renames.addAll(newRenameMethod(id, newName, graph));
        } else if (node.getType() == Node.Type.Attribute) {
            renames.add(new RenameAttribute(id, newName, graph));
        } else if (node.getType() == Node.Type.Parameter) {
            renames.add(new RenameParameter(id, newName, graph));
        } else if (node.getType() == Node.Type.Interface) {
            renames.add(new RenameInterface(id, newName, graph));
        } else {
            throw new RefactoringError("Invalid Id for rename !");
        }

        return renames;
    }

    public static List<RenameBase> newRenameStartegy(String target, String newName, Graph graph) {
        if (graph.getNode(target) == null) {
            return null;
        }
        Integer id = graph.getNode(target).getId();
        return newRenameStrategy(id, newName, graph);
    }

    public static List<RenameBase> newRenameMethod(Integer id, String newName, Graph graph) {
        List<Integer> methodsToRename = Queries.overridenMethods(id, graph);
        Integer hostType = Queries.hostType(id, graph);

        if (graph.getNode(hostType).getType() == Node.Type.Interface) {
            List<Integer> implementations = methodsToRename.stream()
                    .flatMap((n) -> Queries.interfaceMethodImplementation(n, graph).stream())
                    .collect(Collectors.toCollection(ArrayList::new));
            methodsToRename.addAll(implementations);
        }

        return methodsToRename.stream()
                .map((n) -> new RenameMethod(n, newName, graph))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean isValidJavaIdentifier(String id) {
        if (id.length() == 0 || ! Character.isJavaIdentifierStart(id.charAt(0))) {
            return false;
        }

        for (Character c: id.substring(1).toCharArray()) {
            if (! Character.isJavaIdentifierPart(c)) {
                return false;
            }
        }

        return true;
    }
}
