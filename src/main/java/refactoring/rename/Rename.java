package refactoring.rename;

import graph.Graph;
import graph.Node;
import graph.Queries;
import refactoring.RefactoringError;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Rename {

    private static HashSet<String> reservedKeywords = new HashSet<>(Arrays.asList(
            "abstract", "assert", "boolean", "break", "byte", "case",
            "catch", "char", "class", "const", "continue",
            "default", "do", "double", "else", "extends",
            "false", "final", "finally", "float", "for",
            "goto", "if", "implements", "import", "instanceof",
            "int", "interface", "long", "native", "new",
            "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "true", "try", "void", "volatile",
            "while")
    );

    public static List<RenameBase> newRenameStrategy(Integer id, String newName, Graph graph) {
        if (graph.getNode(id) == null) {
            throw new RefactoringError("Node " + id + " doesn't exists !");
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
            throw new RefactoringError("Node" + target + " doesn't exists !");
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
        if (id.equals("_") || reservedKeywords.contains(id) || id.length() == 0 || ! Character.isJavaIdentifierStart(id
                .charAt(0))) {
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
