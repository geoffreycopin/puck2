package graph;

import org.extendj.ast.MethodDecl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Queries {
    public static List<Integer> subClasses(Integer classId, Graph graph) {
        return graph.queryNodesTo(classId, Edge.Type.IsA).stream()
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> overridenMethods(Integer methodId, Graph graph) {
        String methodName = lastComponent(graph.getNode(methodId).getFullName());
        Integer hostType = graph.queryNodesTo(methodId, Edge.Type.Contains).get(0).getId();

        return graph.queryNodesFrom(hostType, Edge.Type.Contains).stream()
                .filter((n) -> n.getType() == Node.Type.Method && lastComponent(n.getFullName()).equals(methodName))
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> methodsInInterfaceImp(Integer interfaceId, Graph graph) {
        Set<String> methodsName = methodsInType(interfaceId, graph).stream()
                .map((n) -> ((MethodDecl) graph.getNode(n).getExtendjNode()).name())
                .collect(Collectors.toCollection(HashSet::new));

        return subClasses(interfaceId, graph).stream()
                .flatMap((n) -> methodsInType(n, graph).stream())
                .filter((n) -> {
                    String name = ((MethodDecl) graph.getNode(n).getExtendjNode()).name();
                    return methodsName.contains(name);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> methodsInType(Integer typeId, Graph graph) {
        return graph.queryNodesFrom(typeId, Edge.Type.Contains).stream()
                .filter((n) -> n.getType() == Node.Type.Method)
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String lastComponent(String name) {
        String[] components = name.split("\\.");
        if (components.length == 0) {
            return "";
        }
        return components[components.length - 1];
    }
}
