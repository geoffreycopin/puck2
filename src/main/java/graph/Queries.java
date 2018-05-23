package graph;

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
        String methodName = methodName(graph.getNode(methodId).getFullName());
        Integer hostType = graph.queryNodesTo(methodId, Edge.Type.Contains).get(0).getId();

        return graph.queryNodesFrom(hostType, Edge.Type.Contains).stream()
                .filter((n) -> n.getType() == Node.Type.Method && methodName(n.getFullName()).equals(methodName))
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> interfaceMethodImplementation(Integer interfaceMethod, Graph graph) {
        Integer hostInterface = hostType(interfaceMethod, graph);
        String singature = lastComponent(graph.getNode(interfaceMethod).getFullName());

        return methodsInInterfaceImp(hostInterface, graph).stream()
                .filter((n) -> lastComponent(graph.getNode(n).getFullName()).equals(singature))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> methodsInInterfaceImp(Integer interfaceId, Graph graph) {
        Set<String> methodsName = methodsInType(interfaceId, graph).stream()
                .map((n) -> methodName(graph.getNode(n).getFullName()))
                .collect(Collectors.toCollection(HashSet::new));

        return subClasses(interfaceId, graph).stream()
                .flatMap((n) -> methodsInType(n, graph).stream())
                .filter((n) -> {
                    String name = methodName(graph.getNode(n).getFullName());
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

    public static List<Integer> classAttributes(Integer classId, Graph graph) {
        return graph.queryNodesFrom(classId, Edge.Type.Contains).stream()
                .filter((n) -> n.getType() == Node.Type.Attribute)
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Integer hostType(Integer bodyDecl, Graph graph) {
        return graph.queryNodesTo(bodyDecl, Edge.Type.Contains).get(0).getId();
    }

    public static String lastComponent(String name) {
        String[] components = name.split("\\.");
        if (components.length == 0) {
            return "";
        }
        return components[components.length - 1];
    }

    public static String methodName(String fullName) {
        return lastComponent(fullName.split("\\(")[0]);
    }
}
