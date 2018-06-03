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

    public static List<Integer> overloadedMethods(Integer methodId, Graph graph) {
        String methodName = methodName(graph.getNode(methodId).getFullName());
        Integer hostType = graph.queryNodesTo(methodId, Edge.Type.Contains).get(0).getId();

        return graph.queryNodesFrom(hostType, Edge.Type.Contains).stream()
                .filter((n) -> n.getType() == Node.Type.Method && methodName(n.getFullName()).equals(methodName))
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> overriddenMethods(Integer methodId, Graph graph) {
        String signature = Queries.lastComponent(graph.getNode(methodId).getFullName());
        Integer hostType = Queries.parent(methodId, graph);
        return Queries.hierarchicalChilds(hostType, graph).stream()
                .flatMap((n) -> graph.queryNodesFrom(n, Edge.Type.Contains).stream())
                .filter((n) -> n.getType() == Node.Type.Method
                        && Queries.lastComponent(n.getFullName()).equals(signature))
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> interfaceMethodImplementation(Integer interfaceMethod, Graph graph) {
        Integer hostInterface = parent(interfaceMethod, graph);
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

    public static List<Integer> typesInPackage(Integer packageId, Graph graph) {
        return  graph.queryNodesFrom(packageId, Edge.Type.Contains).stream()
                    .filter((n) -> n.getType() == Node.Type.Class || n.getType() == Node.Type.Interface)
                    .map(Node::getId)
                    .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Integer parent(Integer node, Graph graph) {
        List<Node> p = graph.queryNodesTo(node, Edge.Type.Contains);
        if (p.size() > 0) {
            return p.get(0).getId();
        } else return null;
    }

    public static List<Integer> directHierarchicalParents(Integer nodeId, Graph graph) {
        return graph.queryNodesFrom(nodeId, Edge.Type.IsA).stream()
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> hierarchicalParents(Integer nodeId, Graph graph) {
        List<Integer> result = new ArrayList<>();
        List<Integer> p = directHierarchicalParents(nodeId, graph);
        result.addAll(p);
        for (Integer n: p) {
            result.addAll(hierarchicalParents(n, graph));
        }

        return result;
    }

    public static List<Integer> directHierarchicalChild(Integer typeId, Graph graph) {
        return graph.queryNodesTo(typeId, Edge.Type.IsA).stream()
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Integer> hierarchicalChilds(Integer typeId, Graph graph) {
        List<Integer> c = directHierarchicalChild(typeId, graph);
        List<Integer> result = new ArrayList<>(c);
        for (Integer child: c) {
            result.addAll(hierarchicalChilds(child, graph));
        }
        return result;
    }

    public static List<Integer> allParents(Integer nodeId, Graph graph) {
        List<Integer> result = new ArrayList<>();
        Integer p = Queries.parent(nodeId, graph);

        while (p != null) {
            result.add(p);
            p = Queries.parent(p, graph);
        }

        return result;
    }

    public static Integer typePackage(Integer typeId, Graph graph) {
        for (Integer i: Queries.allParents(typeId, graph)) {
            if (graph.getNode(i).getType() == Node.Type.Package) {
                return i;
            }
        }
        return null;
    }

    public static String lastComponent(String name) {
        String[] components = name.split("\\.");
        if (components.length == 0) {
            return "";
        }
        return components[components.length - 1];
    }

    public static List<Integer> superTypes(Integer typeId, Graph graph) {
        List<Integer> parents = graph.queryNodesTo(typeId, Edge.Type.Contains).stream()
                .map(Node::getId)
                .collect(Collectors.toCollection(ArrayList::new));

        List<Integer> parentsParents = parents.stream()
                .flatMap((n) -> superTypes(n, graph).stream())
                .collect(Collectors.toCollection(ArrayList::new));

        parents.addAll(parentsParents);

        return parents;
    }

    public static String methodName(String fullName) {
        return lastComponent(fullName.split("\\(")[0]);
    }
}
